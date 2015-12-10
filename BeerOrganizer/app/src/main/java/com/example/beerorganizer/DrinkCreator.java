package com.example.beerorganizer;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsenj on 23.10.2015.
 */

//DrinkCreator lets us create Drink objects and put in to the list as well enabling us to choose as standard
public class DrinkCreator extends Activity {

    private static final int STANDARD = 0, EDIT = 1, DELETE = 2;

    EditText dNameTxt, dPriceTxt, dStoreTxt;
    ImageView drinkImageImgview;
    List<Drink> Drinks = new ArrayList<Drink>();
    ListView drinkListView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.beerorganizer/drawable/no_user_logo.png");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Drink> drinkListAdapter;

    //Set up fields that connects to the XML-file. Also creates tabs, one for the list and one for the creator.
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

        //Creates a drink object and adds it to DrinkList with out chosen values.
        final Button addDrinkBtn = (Button) findViewById(R.id.btnDrinkAdd);
        addDrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drink drink = new Drink(dbHandler.getDrinksCount(), String.valueOf(dNameTxt.getText()), String.valueOf(dPriceTxt.getText()), String.valueOf(dStoreTxt.getText()), imageUri);
                if (!drinkListExists(drink)) {
                    dbHandler.createDrink(drink);
                    Drinks.add(drink);
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
            //This method is called to notify you that, the count characters beginning at start are about to be replaced by new text with length after
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            //This method is called to notify you that, the count characters beginning at start have just replaced old text that had length before.
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addDrinkBtn.setEnabled(String.valueOf(dNameTxt.getText()).trim().length() > 0);
            }

            @Override
            //This method is called to notify you that, the text has been changed.
            public void afterTextChanged(Editable editable) {

            }
        });

        //Gives you the option to click on the image to add an image
        drinkImageImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Drink Image"), 1);
            }
        });

        // Populates the list if there's any drinks.
       if (dbHandler.getDrinksCount() != 0)
           Drinks.addAll(dbHandler.getAllDrinks());

        populateList();
    }

//When you click and hold over a drink in the list, this menu pops up.
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Drink Options");
        menu.add(Menu.NONE, STANDARD, menu.NONE, "Choose as standard");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Edit Drink");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete Drink");

    }

    //Methods following the menu above.
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Choose drink as standard-drink.
            case STANDARD:
                // TODO: Implement standardizing a beer
                ResourceManager.getInstance().cost_drink = Integer.parseInt(Drinks.get(longClickedItemIndex).getDrinkPrice());
                break;
            // Not yet created
            case EDIT:
                break;
            // Deletes a drink from the list.
            case DELETE:
                dbHandler.deleteDrink(Drinks.get(longClickedItemIndex));
                Drinks.remove(longClickedItemIndex);
                drinkListAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    //Adds a list if no list exists.
    private boolean drinkListExists(Drink drink) {
        String dName = drink.getDrinkName();
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

// populates the list by demand
    private void populateList() {
        drinkListAdapter = new DrinkListAdapter();
        drinkListView.setAdapter(drinkListAdapter);
    }


//Gets the content of creator and puts it in the list.
    private class DrinkListAdapter extends ArrayAdapter<Drink> {
        public DrinkListAdapter() {
            super (DrinkCreator.this, R.layout.drink_list, Drinks);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.drink_list, parent, false);

            Drink currentDrink = Drinks.get(position);

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
    //Takes you back to the Main Page
    public void buttonOnClick(View v) {
        Button orgBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Main_Activity.class));
        finish();
    }
    //Takes you back to the Main Page
    public void buttonOnClick2(View v) {
        Button drinkBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Main_Activity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}




