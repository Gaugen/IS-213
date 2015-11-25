package com.example.beerorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steff_000 on 07.09.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
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


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //http://developer.android.com/training/basics/data-storage/databases.html
    // Trenger en methode som laster inn/ leser inn data fra forrige gang appen kjørte, eller lager ny db om det er første gang den blir kjørt.
    //getWritableDatabase() or getReadableDatabase() sqlite metoder.
    public void ReadableDatabase(SQLiteDatabase db) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BEERS + "(" + KEY_BEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BEER_NAME + " TEXT," + KEY_BEER_PRICE + " TEXT," + KEY_BEER_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DRINKS + "(" + KEY_DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DRINK_NAME + " TEXT," + KEY_DRINK_PRICE + " TEXT," + KEY_DRINK_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS + TABLE_DRINKS);

        onCreate(db);
    }

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

    public void createDrink(DrinkList drinklist) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DRINK_NAME, drinklist.getDrinkName());
        values.put(KEY_DRINK_PRICE, drinklist.getDrinkPrice());
        values.put(KEY_DRINK_STORE, drinklist.getDrinkStore());
        values.put(KEY_IMAGEURI, drinklist.getImageUri().toString());

        db.insert(TABLE_DRINKS, null, values);
        db.close();
    }

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

    public DrinkList getDrink(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_DRINKS, new String[] {KEY_DRINK_ID, KEY_DRINK_NAME, KEY_DRINK_PRICE, KEY_DRINK_STORE, KEY_IMAGEURI}, KEY_DRINK_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null );
        if (cursor != null)
            cursor.moveToFirst();

        DrinkList drinkList = new DrinkList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
        db.close();
        cursor.close();
        return drinkList;
    }

    // TODO: add editBeer function
   // public void editBeer(Beer beer) {
   //     SQLiteDatabase db = getWritableDatabase();
   //     db.update(TABLE_BEERS, KEY_BEER_ID, KEY_BEER_NAME, KEY_BEER_PRICE, KEY_BEER_STORE, KEY_IMAGEURI)
   // }

    public void deleteBeer(Beer beer) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BEERS, KEY_BEER_ID + "=?", new String[]{String.valueOf(beer.getId())});
        db.close();
    }

    public void deleteDrink(DrinkList drinkList) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DRINKS, KEY_DRINK_ID + "=?", new String[]{String.valueOf(drinkList.getId())});
        db.close();
    }


    public int getBeersCount(){
        //SELECT * FROM CONTACTS
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BEERS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    public int getDrinksCount(){
        //SELECT * FROM CONTACTS
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRINKS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

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

    public int updateDrink(DrinkList drinkList) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DRINK_NAME, drinkList.getDrinkName());
        values.put(KEY_DRINK_PRICE, drinkList.getDrinkPrice());
        values.put(KEY_DRINK_STORE, drinkList.getDrinkStore());
        values.put(KEY_IMAGEURI, drinkList.getImageUri().toString());

        int rowsAffected = db.update(TABLE_DRINKS, values, KEY_DRINK_ID + "=?", new String[]{String.valueOf(drinkList.getId())});
        db.close();

        return rowsAffected;

    }

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

    public List<DrinkList> getAllDrinks() {
        List<DrinkList> drinks = new ArrayList<DrinkList>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DRINKS, null);
        if(cursor.moveToFirst()) {
            do {
                drinks.add(new DrinkList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return drinks;
    }
}
