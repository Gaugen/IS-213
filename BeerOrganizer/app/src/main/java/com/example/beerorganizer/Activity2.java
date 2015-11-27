package com.example.beerorganizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Activity2 extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText preferenceSum,preferenceCount;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        preferenceSum=(EditText)findViewById(ResourceManager.getInstance().sum);
        preferenceCount=(EditText)findViewById(ResourceManager.getInstance().count);
        final TextView countTextView = (TextView) findViewById(R.id.TextViewCount);
        final TextView sumTextView = (TextView) findViewById(R.id.textSum);
        final ImageButton countButton = (ImageButton) findViewById(R.id.beerCount);
        final ImageButton drinkButton = (ImageButton) findViewById(R.id.drinkCount);
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



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

                /*if((ResourceManager.getInstance().sum != null) & (ResourceManager.getInstance().count != null)) {

                String rememberSum = preferenceSum.getText().toString();
                String rememberCount = preferenceCount.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(rememberSum, ResourceManager.getInstance().sum);
                editor.putInt(rememberCount, ResourceManager.getInstance().count);
                editor.commit();
                Toast.makeText(Activity2.this, "Thanks", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        drinkButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ResourceManager.getInstance().count++;
                ResourceManager.getInstance().sum += ResourceManager.getInstance().cost_drink;
                countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
                sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
                String rememberSum  = preferenceSum.getText().toString();
                String rememberCount  = preferenceCount.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(rememberSum,ResourceManager.getInstance().sum);
                editor.putInt(rememberCount,ResourceManager.getInstance().count);
                editor.commit();
                Toast.makeText(Activity2.this, "Thanks", Toast.LENGTH_LONG).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

        public void onClick (View view) {
            ResourceManager.getInstance().sum = 0;
            ResourceManager.getInstance().count = 0;
            countTextView.setText("Du har drukket " + ResourceManager.getInstance().count + " enheter!");
            sumTextView.setText("Sum:" + ResourceManager.getInstance().sum + "!");
            String rememberSum  = preferenceSum.getText().toString();
            String rememberCount  = preferenceCount.getText().toString();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(rememberSum,ResourceManager.getInstance().sum);
            editor.putInt(rememberCount,ResourceManager.getInstance().count);
            editor.commit();
            Toast.makeText(Activity2.this, "Thanks", Toast.LENGTH_LONG).show();
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
