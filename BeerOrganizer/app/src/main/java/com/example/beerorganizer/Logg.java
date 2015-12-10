package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EMU on 10.12.2015.
 */
public class Logg extends Activity {
    List<Logg> Loggs = new ArrayList<Logg>();
    ListView loggListView;
    ArrayAdapter<Logg> loggListAdapter;
    int longClickedItemIndex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logg);


        //registerForContextMenu(loggListView);

      /*  loggListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });
        private void populateList() {
            loggListAdapter = new loggListAdapter();
            loggListView.setAdapter(loggListAdapter);
        } */

        class LoggListAdapter extends ArrayAdapter<Logg> {
            public LoggListAdapter() {
                super (Logg.this, R.layout.logg_list, Loggs);
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null)
                    view = getLayoutInflater().inflate(R.layout.logg_list, parent, false);
                return view;
            }
        }}

    public void loggBackOnClick(View v) {
        Button loggBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Main_Activity.class));
        finish();
    }

}
