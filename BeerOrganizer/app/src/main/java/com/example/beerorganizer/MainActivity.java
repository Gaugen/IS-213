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

public class MainActivity extends Activity {

    private static final int STANDARD = 0, EDIT = 1, DELETE = 2;


    EditText nameTxt, priceTxt, storeTxt;
    ImageView beerImageImgView;
    List<Beer> Beers = new ArrayList<Beer>();
    ListView beerListView;
    Uri imageUri = Uri.parse("android.resource://org.intracode.beerorganizer/drawable/no_user_logo.png");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Beer> beerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt = (EditText) findViewById(R.id.txtName);
        priceTxt = (EditText) findViewById(R.id.txtPrice);
        storeTxt = (EditText) findViewById(R.id.txtStore);
        beerListView = (ListView) findViewById(R.id.listView);
        beerImageImgView = (ImageView) findViewById(R.id.imgViewBeerImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(beerListView);

        beerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabBeerCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabBeerList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);


        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        // addBtn.setOnClickListener((view) -> {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beer beer = new Beer(dbHandler.getBeersCount(), String.valueOf(nameTxt.getText()), String.valueOf(priceTxt.getText()), String.valueOf(storeTxt.getText()), imageUri);
                if (!contactExists(beer)) {
                    dbHandler.createBeer(beer);
                    Beers.add(beer);
                    beerAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " has been added to your Beer list!", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Your Beer has been created!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " already exists. Please use another name.", Toast.LENGTH_SHORT).show();

            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
                addBtn.setEnabled(String.valueOf(nameTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        beerImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Beer Image"), 1);
            }
        });

        if (dbHandler.getBeersCount() != 0)
        Beers.addAll(dbHandler.getAllBeers());

        populateList();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Beer Options");
        menu.add(Menu.NONE, STANDARD, menu.NONE, "Choose as standard");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Edit Beer");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete Beer");

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case STANDARD:
                // TODO: Implement standardizing a beer
                break;
            case EDIT:
                //dbHandler.editBeer(Beers.get(longClickedItemIndex));
                //Beers
                // TODO: Implement editing a contact
                break;
            case DELETE:
                dbHandler.deleteBeer(Beers.get(longClickedItemIndex));
                Beers.remove(longClickedItemIndex);
                beerAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private boolean contactExists(Beer beer) {
        String name = beer.getBeerName();
        int contactCount = Beers.size();

        for (int i = 0; i < contactCount; i++ ) {
            if (name.compareToIgnoreCase(Beers.get(i).getBeerName()) == 0)
                return true;
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                beerImageImgView.setImageURI(data.getData());
            }
        }
    }

    private void populateList() {
        beerAdapter = new ContactListAdapter();
        beerListView.setAdapter(beerAdapter);
    }


    private class ContactListAdapter extends ArrayAdapter<Beer> {
        public ContactListAdapter(){
            super (MainActivity.this, R.layout.listview_item, Beers);
        }

        @Override
        public View getView(int position,View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Beer currentBeer = Beers.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentBeer.getBeerName());
            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentBeer.getBeerPrice());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentBeer.getBeerStore());
            ImageView ivBeerImage = (ImageView) view.findViewById(R.id.ivBeerImage);
            ivBeerImage.setImageURI(currentBeer.getImageUri());
            return view;
        }
    }

    public void buttonOnClick(View v) {
        Button orgBack=(Button) v;
        startActivity(new Intent(getApplicationContext(),Activity2.class));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
