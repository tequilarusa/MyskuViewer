package com.tequilarusa.mysku.viewer.images;

import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.FloatMath;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Maks on 04.01.2017.
 */

public class FlowTextHelper {

    private static boolean mNewClassAvailable;

    static {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) { // Froyo 2.2, API level 8
            mNewClassAvailable = true;
        }
    }

    public static void tryFlowText(String text, View thumbnailView, TextView messageView, Display display){
        // There is nothing I can do for older versions, so just return
        if(!mNewClassAvailable) return;

        // Get height and width of the image and height of the text line
        thumbnailView.measure(display.getWidth(), display.getHeight());
        int height = thumbnailView.getMeasuredHeight();
        int width = thumbnailView.getMeasuredWidth();
        float textLineHeight = messageView.getPaint().getTextSize();

        // Set the span according to the number of lines and width of the image
        int lines = (int) ceil(height / textLineHeight);
        //For an html text you can use this line: SpannableStringBuilder ss = (SpannableStringBuilder)Html.fromHtml(text);
        SpannableStringBuilder ss = (SpannableStringBuilder) Html.fromHtml(text);
        //SpannableString ss = new SpannableString(text);
        ss.setSpan(new MyLeadingMarginSpan2(lines, width), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageView.setText(ss);

        // Align the text with the image by removing the rule that the text is to the right of the image
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)messageView.getLayoutParams();
        int[]rules = params.getRules();
        rules[RelativeLayout.RIGHT_OF] = 0;
    }

    public static float ceil(float value) {
        return (float) Math.ceil(value);
    }
}
