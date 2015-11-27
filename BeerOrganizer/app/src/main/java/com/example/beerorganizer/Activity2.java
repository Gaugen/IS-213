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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        final TextView countTextView = (TextView) findViewById(R.id.TextViewCount);
        final TextView sumTextView = (TextView) findViewById(R.id.textSum);
        final ImageButton countButton = (ImageButton) findViewById(R.id.beerCount);
        final ImageButton drinkButton = (ImageButton) findViewById(R.id.drinkCount);
        final Button resetButton = (Button) findViewById(R.id.resetButton);

        // Here OnClickListener is used to create a button, which has a counter, showing "enheter" and "sum" each time you press it.
        countButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /*
                mCount++;
                mSum += ResourceManager.getInstance().cost_beer;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
                */
                //System.out.println("Beer name: nøgne ø, pris: 399, butikk: rema. ");
                ResourceManager.getInstance().count++;
                ResourceManager.getInstance().sum += ResourceManager.getInstance().cost_beer;
                countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
                sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
            }
        });

        drinkButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ResourceManager.getInstance().count++;
                ResourceManager.getInstance().sum += ResourceManager.getInstance().cost_drink;
                countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
                sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

        public void onClick (View view) {
            ResourceManager.getInstance().sum = 0;
            ResourceManager.getInstance().count = 0;
            countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
            sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
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
