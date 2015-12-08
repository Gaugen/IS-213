package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsenj on 23.10.2015.
 */
public class LoggList extends Activity {

    private static final int DELETE = 0;

    EditText lNameTxt, lPriceTxt, lStoreTxt, lTimeStamp;
    List<Logg> Loggs = new ArrayList<Logg>();
    ListView loggListView;
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Logg> loggListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logg);

        lNameTxt = (EditText) findViewById(R.id.loggName);
        lPriceTxt = (EditText) findViewById(R.id.loggPrice);
        lStoreTxt = (EditText) findViewById(R.id.loggStore);
        lTimeStamp = (EditText) findViewById(R.id.loggTimeStamp);
        loggListView = (ListView) findViewById(R.id.listLoggView);
        dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(loggListView);

        loggListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });


        /*dNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addDrinkBtn.setEnabled(String.valueOf(dNameTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (dbHandler.getLogsCount() != 0)
            Log.addAll(dbHandler.getAllLogs());

        populateList();
        */
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Log Options");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete Log");

    }

    public void addLogg(View view) {
        LoggList loggList = new LoggList(dbHandler.getDrinksCount(), String.valueOf(lNameTxt.getText()), String.valueOf(lPriceTxt.getText()), String.valueOf(lStoreTxt.getText()),String.valueOf(lTimeStamp.getText()));
        if (!loggListExists(loggList)) {
            dbHandler.createDrink(loggList);
            Loggs.add(loggList);
            loggListAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), String.valueOf(lNameTxt.getText()) + " has been added to your Log!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Your Beer has been created!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), String.valueOf(lNameTxt.getText()) + " already exists. Please use another name.", Toast.LENGTH_SHORT).show();

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE:
                dbHandler.deleteLogg(Loggs.get(longClickedItemIndex));
                Loggs.remove(longClickedItemIndex);
                loggListAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private boolean loggListExists(LoggList loggList) {
        String lName = loggList.getLoggName();
        int loggListCount = Loggs.size();

        for (int i = 0; i < loggListCount; i++) {
            if (lName.compareToIgnoreCase(Loggs.get(i).getLoggName()) == 0)
                return true;
        }
        return false;
    }

    private void populateList() {
        loggListAdapter = new LoggListAdapter();
        loggListView.setAdapter(loggListAdapter);
    }


    private class LoggListAdapter extends ArrayAdapter<LoggList> {
        public LoggListAdapter() {super (LoggList.this, R.layout.logg, Loggs);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.logg, parent, false);

            LoggList currentLogg = Loggs.get(position);

            TextView loggName = (TextView) view.findViewById(R.id.loggName);
            loggName.setText(currentLogg.getLoggName());
            TextView drinkPrice = (TextView) view.findViewById(R.id.loggPrice);
            drinkPrice.setText(currentLogg.getLoggPrice());
            TextView loggStore = (TextView) view.findViewById(R.id.loggStore);
            loggStore.setText(currentLogg.getLoggStore());
            TextView loggTimeStamp = (TextView) view.findViewById(R.id.loggTimeStamp);
            loggTimeStamp.setText(currentLogg.getTimeStamp());

            return view;
        }
    }

    public void buttonOnClick(View v) {
        Button orgBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Activity2.class));
        finish();
    }

    public void backToMain(View v) {
        Button loggBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Activity2.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}




