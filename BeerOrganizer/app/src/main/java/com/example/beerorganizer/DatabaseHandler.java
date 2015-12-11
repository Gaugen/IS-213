package com.example.beerorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steff_000 on 07.09.2015.
 */

    //Defines two tables, with fields.
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "data/data/com.example.beerorganizer/databases/";
    private static final String DATABASE_NAME = "Manager",
    TABLE_BEERS = "beers",
    KEY_BEER_ID = "beerid",
    KEY_BEER_NAME = "beername",
    KEY_BEER_PRICE = "beerprice",
    KEY_BEER_STORE = "beerstore",
    KEY_IMAGEURI = "imageUri",

    TABLE_DRINKS = "drinks",
    KEY_DRINK_ID = "drinkid",
    KEY_DRINK_NAME = "drinkname",
    KEY_DRINK_PRICE = "drinkprice",
    KEY_DRINK_STORE = "drinkstore";
    private SQLiteDatabase myDB;
    private Context context;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the two tables, one for beers, then another for drinks.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BEERS + "(" + KEY_BEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BEER_NAME + " TEXT," + KEY_BEER_PRICE + " TEXT," + KEY_BEER_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DRINKS + "(" + KEY_DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DRINK_NAME + " TEXT," + KEY_DRINK_PRICE + " TEXT," + KEY_DRINK_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
    }
    //Upgrades the table if you add/rename or remove columns and populates the a new table. Drops the old table if no exceptions occur.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS + TABLE_DRINKS);

        onCreate(db);
    }
    //Keeps order when invoking the methods, preventing conflicts by not allowing them to make changes in the database at the same time.
    @Override
    public synchronized void close(){
        if(myDB!=null){
            myDB.close();
        }
        super.close();
    }

    /***
     * Check if the database exists on device or not
     * @return
     */
    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    /***
     * Copy database from source code assets to device
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            String outputFileName = DB_PATH + DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

    }

    /***
     * Open database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }

    
    //Adds a beer to the database
    public void createBeer(Beer beer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BEER_NAME, beer.getBeerName());
        values.put(KEY_BEER_PRICE, beer.getBeerPrice());
        values.put(KEY_BEER_STORE, beer.getBeerStore());
        values.put(KEY_IMAGEURI, beer.getImageUri().toString());

        db.insert(TABLE_BEERS, null, values);
        db.close();
    }
    //Adds a drink to the database
    public void createDrink(Drink drinklist) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DRINK_NAME, drinklist.getDrinkName());
        values.put(KEY_DRINK_PRICE, drinklist.getDrinkPrice());
        values.put(KEY_DRINK_STORE, drinklist.getDrinkStore());
        values.put(KEY_IMAGEURI, drinklist.getImageUri().toString());

        db.insert(TABLE_DRINKS, null, values);
        db.close();
    }
    //Populates beerList in BeerCreator (Gets values from the beer table)
    public Beer getBeer(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BEERS, new String[] {KEY_BEER_ID, KEY_BEER_NAME, KEY_BEER_PRICE, KEY_BEER_STORE, KEY_IMAGEURI}, KEY_BEER_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null );
        if (cursor != null)
            cursor.moveToFirst();

        Beer beer = new Beer(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
        db.close();
        cursor.close();
        return beer;
    }
    //Populates drinkList in DrinkCreator (Gets values from the drink table)
    public Drink getDrink(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_DRINKS, new String[] {KEY_DRINK_ID, KEY_DRINK_NAME, KEY_DRINK_PRICE, KEY_DRINK_STORE, KEY_IMAGEURI}, KEY_DRINK_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null );
        if (cursor != null)
            cursor.moveToFirst();

        Drink drink = new Drink(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
        db.close();
        cursor.close();
        return drink;
    }


    //Deletes beer from table
    public void deleteBeer(Beer beer) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BEERS, KEY_BEER_ID + "=?", new String[]{String.valueOf(beer.getId())});
        db.close();
    }
    //Deletes drink from table
    public void deleteDrink(Drink drink) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DRINKS, KEY_DRINK_ID + "=?", new String[]{String.valueOf(drink.getId())});
        db.close();
    }

    //Counts objects from TABLE_BEERS
    public int getBeersCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BEERS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }
    //Counts objects from TABLE_DRINKS
    public int getDrinksCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRINKS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }
    //Replaces old values with new values of the chosen beer
    public int updateBeer(Beer beer) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BEER_NAME, beer.getBeerName());
        values.put(KEY_BEER_PRICE, beer.getBeerPrice());
        values.put(KEY_BEER_STORE, beer.getBeerStore());
        values.put(KEY_IMAGEURI, beer.getImageUri().toString());

        int rowsAffected = db.update(TABLE_BEERS, values, KEY_BEER_ID + "=?", new String[]{String.valueOf(beer.getId())});
        db.close();

        return rowsAffected;

    }
    //Replaces old values with new values of the chosen drink
    public int updateDrink(Drink drink) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DRINK_NAME, drink.getDrinkName());
        values.put(KEY_DRINK_PRICE, drink.getDrinkPrice());
        values.put(KEY_DRINK_STORE, drink.getDrinkStore());
        values.put(KEY_IMAGEURI, drink.getImageUri().toString());

        int rowsAffected = db.update(TABLE_DRINKS, values, KEY_DRINK_ID + "=?", new String[]{String.valueOf(drink.getId())});
        db.close();

        return rowsAffected;

    }
    //Creates ArrayList Beer which contains all objects with values from TABLE_BEERS
    public List<Beer> getAllBeers() {
        List<Beer> beers = new ArrayList<Beer>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BEERS, null);
        if(cursor.moveToFirst()) {
            do {
                beers.add(new Beer(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return beers;
    }
    //Creates ArrayList Drink which contains all objects with values from TABLE_DRINKS
    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<Drink>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRINKS, null);
        if(cursor.moveToFirst()) {
            do {
                drinks.add(new Drink(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return drinks;
    }
}
