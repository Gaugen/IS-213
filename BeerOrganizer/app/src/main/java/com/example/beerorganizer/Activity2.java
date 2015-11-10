package com.example.beerorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    // Private member field to keep track of the count
    private int mCount = 0;
    private int mSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        final TextView countTextView = (TextView) findViewById(R.id.TextViewCount);
        final TextView sumTextView = (TextView) findViewById(R.id.textSum);
        final ImageButton countButton = (ImageButton) findViewById(R.id.beerCount);
        final ImageButton drinkButton = (ImageButton) findViewById(R.id.drinkCount);
        final Button resetButton = (Button) findViewById(R.id.resetButton);


        countButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mCount++;
                mSum += 72;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
            }
        });

        drinkButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mCount++;
                mSum += 96;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

        public void onClick (View view) {
            mSum = 0;
            mCount = 0;
            sumTextView.setText("Sum:" + mSum + "!");
            countTextView.setText("Du har drukket " + mCount + " enheter!");
        }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDrinkActivity(View view) {
// Do something in response to button
        Intent intent = new Intent(this, DrinkCreator.class);
        startActivity(intent);
        finish();

    }

    public void openBeerActivity(View view) {
// Do something in response to button
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



   // public void buttonOnClick(View v) {
     //   Button chooseBeerBtn=(Button) v;
     //   startActivity(new Intent(getApplicationContext(),MainActivity.class));
    //    finish();
   // }

}
