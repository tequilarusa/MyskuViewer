package tequilarusa.mysku_parser;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_my_profile).withIcon(FontAwesome.Icon.faw_user),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_message).withIcon(FontAwesome.Icon.faw_envelope).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_comments).withIcon(FontAwesome.Icon.faw_comments),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .build();
    }

    private void displayTitleOfReview (String message) {
        TextView titleOfReviewTextView = (TextView) findViewById(R.id.title_of_review);
        titleOfReviewTextView.setText(message);
    }

    private void displayPrice (String message) {
        TextView price = (TextView)findViewById(R.id.price);
        price.setText(message);
    }

    private void displayShortTextDescription (String message) {
        TextView shortTextDescription = (TextView) findViewById(R.id.short_text_description);
        shortTextDescription.setText(message);
    }

    private void displayDateOfPublication (String message) {
        TextView dateOfPublicationTextView = (TextView) findViewById(R.id.date_of_publication);
        dateOfPublicationTextView.setText(message);
    }

    private void displayAuthorOfReview (String message) {
        TextView authorOfReviewTextView = (TextView) findViewById(R.id.author_of_review);
        authorOfReviewTextView.setText(message);
    }

    private void displayQuantityOfFavourite (String message){
        TextView quantityOfFavourite = (TextView) findViewById(R.id.quantity_of_favourite);
        quantityOfFavourite.setText(message);
    }

    private void displayNumberTotalVote (String message){
        TextView numberTotalVote = (TextView) findViewById(R.id.number_total_vote);
        numberTotalVote.setText(message);
    }

    private void displayPrizeRanking (String message){
        TextView prizeRanking = (TextView) findViewById(R.id.prize_ranking);
        prizeRanking.setText(message);
    }

    public void voteForReview (View view) {
        //add method request to the service
        ImageButton voteForReview = (ImageButton) findViewById(R.id.button_voting_positive);
        voteForReview.setImageResource(R.drawable.after_like);
        displayPrizeRanking("функция парсера");
        displayNumberTotalVote("функция парсера");
    }

    public void addInFavourite (View view) {
    //call method that send request to the server
        Button buttonAddInFavourite = (Button) findViewById(R.id.button_add_in_favourite);
        buttonAddInFavourite.setText("Добавлено");
}
    /** Called when the user clicks the Send button
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}
