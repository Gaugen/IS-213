package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsenj on 23.10.2015.
 */
public class DrinkCreator extends Activity {

    private static final int STANDARD = 0, EDIT = 1, DELETE = 2;

    EditText dNameTxt, dPriceTxt, dStoreTxt;
    ImageView drinkImageImgview;
    List<DrinkList> Drinks = new ArrayList<DrinkList>();
    ListView drinkListView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.beerorganizer/drawable/no_user_logo.png");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<DrinkList> drinkListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_creator);

        dNameTxt = (EditText) findViewById(R.id.txtDrinkName);
        dPriceTxt = (EditText) findViewById(R.id.txtDrinkPrice);
        dStoreTxt = (EditText) findViewById(R.id.txtDrinkStore);
        drinkListView = (ListView) findViewById(R.id.listDrinkView);
        drinkImageImgview = (ImageView) findViewById(R.id.imgViewDrinkImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(drinkListView);

        drinkListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        TabHost tabHost2 = (TabHost) findViewById(R.id.tabHost2);

        tabHost2.setup();

        TabHost.TabSpec tabSpec = tabHost2.newTabSpec("listDrink");
        tabSpec.setContent(R.id.tabDrinkList);
        tabSpec.setIndicator("ListDrink");
        tabHost2.addTab(tabSpec);

        tabSpec = tabHost2.newTabSpec("creatorDrink");
        tabSpec.setContent(R.id.tabDrinkCreator);
        tabSpec.setIndicator("CreatorDrink");
        tabHost2.addTab(tabSpec);


        final Button addDrinkBtn = (Button) findViewById(R.id.btnDrinkAdd);
        // addBtn.setOnClickListener((view) -> {
        addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrinkList drinkList = new DrinkList(dbHandler.getDrinksCount(), String.valueOf(dNameTxt.getText()), String.valueOf(dPriceTxt.getText()), String.valueOf(dStoreTxt.getText()), imageUri);
                if (!drinkListExists(drinkList)) {
                    dbHandler.createDrink(drinkList);
                    Drinks.add(drinkList);
                    drinkListAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(dNameTxt.getText()) + " has been added to your Drink list!", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Your Beer has been created!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(dNameTxt.getText()) + " already exists. Please use another name.", Toast.LENGTH_SHORT).show();

            }
        });


        dNameTxt.addTextChangedListener(new TextWatcher() {
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

        drinkImageImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Drink Image"), 1);
            }
        });

       if (dbHandler.getDrinksCount() != 0)
           Drinks.addAll(dbHandler.getAllDrinks());

        populateList();
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Drink Options");
        menu.add(Menu.NONE, STANDARD, menu.NONE, "Choose as standard");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Edit Drink");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete Drink");

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case STANDARD:
                // TODO: Implement standardizing a beer
                ResourceManager.getInstance().cost_drink = Integer.parseInt(Drinks.get(longClickedItemIndex).getDrinkPrice());
                break;
            case EDIT:
                //dbHandler.editBeer(Beers.get(longClickedItemIndex));
                //Beers
                // TODO: Implement editing a contact
                break;
            case DELETE:
                dbHandler.deleteDrink(Drinks.get(longClickedItemIndex));
                Drinks.remove(longClickedItemIndex);
                drinkListAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private boolean drinkListExists(DrinkList drinkList) {
        String dName = drinkList.getDrinkName();
        int drinkListCount = Drinks.size();

        for (int i = 0; i < drinkListCount; i++) {
            if (dName.compareToIgnoreCase(Drinks.get(i).getDrinkName()) == 0)
                return true;
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1){
                imageUri = data.getData();
                drinkImageImgview.setImageURI(data.getData());
            }
        }
    }


    private void populateList() {
        drinkListAdapter = new DrinkListAdapter();
        drinkListView.setAdapter(drinkListAdapter);
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
            ImageView ivDrinkImage = (ImageView) view.findViewById(R.id.ivDrinkImage);
            ivDrinkImage.setImageURI(currentDrink.getImageUri());
            return view;
        }
    }

    public void buttonOnClick(View v) {
        Button orgBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Activity2.class));
        finish();
    }

    public void buttonOnClick2(View v) {
        Button drinkBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Activity2.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}




