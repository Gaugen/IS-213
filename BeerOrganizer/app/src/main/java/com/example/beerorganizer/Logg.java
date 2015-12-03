package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by TorArne on 12/1/2015.
 */
public class Logg extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logg);
        SharedPreferences sp = getSharedPreferences("LogPreference", Activity.MODE_PRIVATE);

        final TextView sharedPreferenceTxt = (TextView) findViewById(R.id.sharedPreferenceTxt);

        sharedPreferenceTxt.setText(Map<String,?> keys = sp.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
        });

    }

    public void saveCurrent (View view) {
// Do something in response to button

        finish();
    }

    public void clearLog (View view) {
// Do something in response to button
        SharedPreferences sp = getSharedPreferences("LogPreference", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        finish();
    }

    public void goBack (View view) {
// Do something in response to button
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
        finish();
    }

}
