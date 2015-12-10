package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Activity2 extends AppCompatActivity {

    //kommenter her au
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        SharedPreferences sp = getSharedPreferences("Preference", Activity.MODE_PRIVATE);

        final TextView countTextView = (TextView) findViewById(R.id.TextViewCount);
        final TextView sumTextView = (TextView) findViewById(R.id.textSum);
        final ImageButton countButton = (ImageButton) findViewById(R.id.beerCount);
        final ImageButton drinkButton = (ImageButton) findViewById(R.id.drinkCount);
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        int count = sp.getInt("count",0);
        int sum = sp.getInt("sum",0);

        countTextView.setText("Du har drukket " + count + " enheter!");
        sumTextView.setText("Sum:" + sum + "!");



        //countButton: 'if' Says that you have to choose a beer if the value of cost_beer = 0. 'Else' will add value to the counter and a sum to the beer that you have chosen when you press the button.
        countButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /*
                mCount++;
                mSum += ResourceManager.getInstance().cost_beer;
                countTextView.setText("Du har drukket " + mCount + " enheter!");
                sumTextView.setText("Sum:" + mSum + "!");
                */
                //System.out.println("Beer name: nøgne ø, pris: 399, butikk: rema. ");
                if (ResourceManager.getInstance().cost_beer == 0){
                    Toast.makeText(getApplicationContext(), "You Have to choose a Beer!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ResourceManager.getInstance().count++;
                    ResourceManager.getInstance().sum += ResourceManager.getInstance().cost_beer;
                    countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
                    sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
                    SharedPreferences sp = getSharedPreferences("Preference", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("sum", ResourceManager.getInstance().sum );
                    editor.putInt("count", ResourceManager.getInstance().count );
                    editor.commit();

                }





            }
        });

        //This is the same code as the above, only with drinks instead of beer.
        drinkButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (ResourceManager.getInstance().cost_drink ==0) {
                    Toast.makeText(getApplicationContext(), "You Have to choose a Drink!", Toast.LENGTH_SHORT).show();
                }
                else{
                    ResourceManager.getInstance().count++;
                    ResourceManager.getInstance().sum += ResourceManager.getInstance().cost_drink;
                    countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
                    sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
                    SharedPreferences sp = getSharedPreferences("Preference", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("sum", ResourceManager.getInstance().sum );
                    editor.putInt("count", ResourceManager.getInstance().count);
                    editor.commit();
                }


            }
        });

        //This button resets the counter and the sum of the above classes.
        resetButton.setOnClickListener(new View.OnClickListener() {

        public void onClick (View view) {
            ResourceManager.getInstance().sum = 0;
            ResourceManager.getInstance().count = 0;
            countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
            sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
            SharedPreferences sp = getSharedPreferences("Preference", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();


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
