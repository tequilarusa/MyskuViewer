package com.tequilarusa.mysku.viewer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iamakulov.myskusdk.MyskuSdk;
import com.iamakulov.myskusdk.MyskuSdkFactory;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.images.ImageLoader;


public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private int mCurFragment = 1;
    private MyskuDrawer mDrawer;
    private MyskuSdk mMyskuSdk;
    private ImageLoader imageLoader;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = new MyskuDrawer(this, toolbar);
        progressDialog = new ProgressDialog(this);
        imageLoader = new ImageLoader(this);
        mMyskuSdk = MyskuSdkFactory.createSdk();

        if (savedInstanceState != null) {
            mCurFragment = Integer.valueOf(savedInstanceState.getString("currentFragment"));
        }

        Fragment fragment = mDrawer.getFragment(mCurFragment);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void displayTitleOfReview(String message) {
        TextView titleOfReviewTextView = (TextView) findViewById(R.id.title_of_review);
        titleOfReviewTextView.setText(message);
    }

    private void displayPrice(String message) {
        TextView price = (TextView) findViewById(R.id.price);
        price.setText(message);
    }

    private void displayShortTextDescription(String message) {
        TextView shortTextDescription = (TextView) findViewById(R.id.short_text_description);
        shortTextDescription.setText(message);
    }

    private void displayDateOfPublication(String message) {
        TextView dateOfPublicationTextView = (TextView) findViewById(R.id.date_of_publication);
        dateOfPublicationTextView.setText(message);
    }

    private void displayAuthorOfReview(String message) {
        TextView authorOfReviewTextView = (TextView) findViewById(R.id.author_of_review);
        authorOfReviewTextView.setText(message);
    }

    private void displayQuantityOfFavourite(String message) {
        TextView quantityOfFavourite = (TextView) findViewById(R.id.quantity_of_favourite);
        quantityOfFavourite.setText(message);
    }

    private void displayNumberTotalVote(String message) {
        TextView numberTotalVote = (TextView) findViewById(R.id.number_total_vote);
        numberTotalVote.setText(message);
    }

    private void displayPrizeRanking(String message) {
        TextView prizeRanking = (TextView) findViewById(R.id.prize_ranking);
        prizeRanking.setText(message);
    }

    public void voteForReview(View view) {
        //add method request to the service
        ImageButton voteForReview = (ImageButton) findViewById(R.id.button_voting_positive);
        voteForReview.setImageResource(R.drawable.after_like);
        Toast toast = Toast.makeText(this, "Голос учтен", Toast.LENGTH_LONG);
        toast.show();
//        displayPrizeRanking("функция парсера");
//        displayNumberTotalVote("функция парсера");
    }

    public void addInCart(View view) {
        ImageButton buttonAddInFavourite = (ImageButton) findViewById(R.id.button_add_in_cart);
        buttonAddInFavourite.setImageResource(R.drawable.after_cart);
        Toast toast = Toast.makeText(this, "Добавлено в корзину", Toast.LENGTH_LONG);
        toast.show();
    }

    public void addInFavourite(View view) {
        //call method that send request to the server
        ImageButton buttonAddInFavourite = (ImageButton) findViewById(R.id.button_add_in_favourite);
        buttonAddInFavourite.setImageResource(R.drawable.after_favorite);
        Toast toast = Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentFragment", String.valueOf(mCurFragment));
    }

    @Override
    public void onBackStackChanged() {
        boolean rootLevel = getSupportFragmentManager().getBackStackEntryCount() == 0;
        mDrawer.showDrawerIndicator(rootLevel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mDrawer.checkCloseBeforeExit()) {
            super.onBackPressed();
        }
    }

    public MyskuSdk getMyskuSdk() {
        return mMyskuSdk;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    public int getCurFragment() {
        return mCurFragment;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setCurFragment(int mCurFragment) {
        this.mCurFragment = mCurFragment;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);
        fragmentManager.beginTransaction()
                .hide(currentFragment)
                .add(R.id.content_frame, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}
