package com.tequilarusa.mysku.viewer.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tequilarusa.mysku.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Maks on 04.01.2017.
 */

public class ImageLoader implements Html.ImageGetter {

    // the simplest in-memory cache implementation. This should be replaced with
    // something like SoftReference or BitmapOptions.inPurgeable(since 1.6)
    private HashMap<String, Bitmap> cache = new HashMap<>();
    private File cacheDir;
    private Context ctx;
    private PhotosLoader photoLoaderThread = new PhotosLoader();
    private PhotosQueue photosQueue = new PhotosQueue();
    private final int stub_id = R.drawable.ic_launcher;

    private View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param context
     * @param view
     */
    public ImageLoader(Context context, View view) {
        this(context);
        this.container = view;
    }

    public ImageLoader(Context context) {
        ctx = context;

        // Make the background thead low priority. This way it will not affect
        // the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    ctx.getExternalCacheDir(),
                    "LazyList");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            if (!cacheDir.mkdirs()) {
                Log.w("ImageLoader", "Cache directory could not be created");
            }

    }

    public void DisplayImage(String url, ImageView imageView) {
        if (cache.containsKey(url))
            imageView.setImageBitmap(cache.get(url));
        else {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        // This ImageView may be used for other images before. So there may be
        // some old tasks in the queue. We need to discard them.
        photosQueue.Clean(imageView);
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        synchronized (photosQueue.photosToLoad) {
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }

        // start thread if it's not started yet
        if (photoLoaderThread.getState() == Thread.State.NEW)
            photoLoaderThread.start();
    }

    @Nullable
    private Bitmap getBitmap(String url) {
        // I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);

        // from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        // from web
        try {
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            return decodeFile(f);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Nullable
    // decodes image and scales it to reduce memory consumption
    private Bitmap  decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.w("ImageLoader", "Image to decode could not found", e);
        }
        return null;
    }

    @Override
    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask( urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0
                    + result.getIntrinsicHeight());

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            ImageLoader.this.container.invalidate();
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                Drawable drawable = new BitmapDrawable(getBitmap(urlString));
                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0
                        + drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

    }

    // Task for the queue
    private class PhotoToLoad {
        String url;
        ImageView imageView;

        PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    public void stopThread() {
        photoLoaderThread.interrupt();
    }

    // stores list of photos to download
    private class PhotosQueue {
        private final Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

        // removes all instances of this ImageView
        void Clean(ImageView image) {
            for (int j = 0; j < photosToLoad.size(); ) {
                if (photosToLoad.get(j).imageView == image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }

    private class PhotosLoader extends Thread {
        public void run() {
            try {
                while (true) {
                    // thread waits until there are any images to load in the queue
                    if (photosQueue.photosToLoad.size() == 0)
                        synchronized (photosQueue.photosToLoad) {
                            photosQueue.photosToLoad.wait();
                        }
                    if (photosQueue.photosToLoad.size() != 0) {
                        PhotoToLoad photoToLoad;
                        synchronized (photosQueue.photosToLoad) {
                            photoToLoad = photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp = getBitmap(photoToLoad.url);
                        cache.put(photoToLoad.url, bmp);
                        if (photoToLoad.imageView.getTag().equals(photoToLoad.url)) {
                            BitmapDisplayer bd = new BitmapDisplayer(bmp,
                                    photoToLoad.imageView);
                            ((Activity) ctx).runOnUiThread(bd);
                        }
                    }
                    if (Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
                // allow thread to exit
            }
        }
    }



    // Used to display bitmap in the UI thread
    private class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;

        BitmapDisplayer(Bitmap b, ImageView i) {
            bitmap = b;
            imageView = i;
        }

        public void run() {
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
            else
                imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        // clear memory cache
        cache.clear();

        // clear SD cache
        File[] files = cacheDir.listFiles();
        for (File f : files)
            if (!f.delete()) {
                Log.w("ImageLoader", "Cached image could not delete: " + f.getAbsolutePath());
            }
    }

    class URLDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if(drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

}
