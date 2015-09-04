package com.example.beerapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



public class MainActivity extends Activity {

    // Private member field to keep track of the count
    private int mCount = 0;
    private int mSum = 0;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView countTextView = (TextView) findViewById(R.id.TextViewCount);
        final TextView sumTextView = (TextView) findViewById(R.id.textSum);
        final ImageButton countButton = (ImageButton) findViewById(R.id.beerCount);
        final ImageButton drinkButton = (ImageButton) findViewById(R.id.drinkCount);


        countButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mCount++;
                mSum+=72;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
            }
        });

        drinkButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mCount++;
                mSum+=96;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
            }
        });
    }
}
