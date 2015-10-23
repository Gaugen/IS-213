package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsenj on 23.10.2015.
 */
public class DrinkCreator extends Activity {

    EditText dNameTxt, dPriceTxt, dStoreTxt;
    ImageView drinkImageImgview;
    List<DrinkList> Drinks = new ArrayList<DrinkList>();
    ListView drinkListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_creator);

        dNameTxt = (EditText) findViewById(R.id.txtDrinkName);
        dPriceTxt = (EditText) findViewById(R.id.txtDrinkPrice);
        dStoreTxt = (EditText) findViewById(R.id.txtDrinkStore);
        drinkListView = (ListView) findViewById(R.id.listView);
        drinkImageImgview = (ImageView) findViewById(R.id.imgViewDrinkImage);

        TabHost tabHost2 = (TabHost) findViewById(R.id.tabHost2);

        tabHost2.setup();

        TabHost.TabSpec tabSpec = tabHost2.newTabSpec("creatorDrink");
        tabSpec.setContent(R.id.tabDrinkCreator);
        tabSpec.setIndicator("CreatorDrink");
        tabHost2.addTab(tabSpec);

        tabSpec = tabHost2.newTabSpec("listDrink");
        tabSpec.setContent(R.id.tabDrinkList);
        tabSpec.setIndicator("ListDrink");
        tabHost2.addTab(tabSpec);

        final Button addDrinkBtn = (Button) findViewById(R.id.btnDrinkAdd);
        addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(dNameTxt.getText().toString(), dPriceTxt.getText().toString(), dStoreTxt.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(), dNameTxt.getText().toString() + "has been added to your Drink List!", Toast.LENGTH_SHORT).show();
            }
        });


        dNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addDrinkBtn.setEnabled(!dNameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        drinkImageImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Drink Image"), 1);
            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1)
                drinkImageImgview.setImageURI(data.getData());
        }
    }


    private void addDrink(String drinkName, String drinkPrice, String drinkStore) {
        Drinks.add(new DrinkList(drinkName, drinkPrice, drinkStore));
    }

    private void populateList() {
        ArrayAdapter<DrinkList> adapter = new DrinkListAdapter();
        drinkListView.setAdapter(adapter);
    }



    private class DrinkListAdapter extends ArrayAdapter<DrinkList> {
        public DrinkListAdapter() {
            super (DrinkCreator.this, R.layout.drink_list, Drinks);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.drink_list, parent, false);

            DrinkList currentDrink = Drinks.get(position);

            TextView drinkName = (TextView) view.findViewById(R.id.drinkName);
            drinkName.setText(currentDrink.getDrinkName());
            TextView drinkPrice = (TextView) view.findViewById(R.id.drinkPrice);
            drinkPrice.setText(currentDrink.getDrinkPrice());
            TextView drinkStore = (TextView) view.findViewById(R.id.drinkStore);
            drinkStore.setText(currentDrink.getDrinkStore());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
