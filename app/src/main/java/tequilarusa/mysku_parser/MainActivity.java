package tequilarusa.mysku_parser;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new MyskuDrawer(this, toolbar).build();

        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
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
        displayPrizeRanking("функция парсера");
        displayNumberTotalVote("функция парсера");
    }

    public void addInFavourite(View view) {
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
