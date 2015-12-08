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
    KEY_DRINK_STORE = "drinkstore",

    TABLE_LOGG = "logg",
    KEY_LOGG_ID = "loggid",
    KEY_LOGG_NAME = "loggname",
    KEY_LOGG_PRICE = "loggprice",
    KEY_LOGG_STORE = "loggstore",
    KEY_LOGG_TIMESTAMP = "loggtimestamp";

    private SQLiteDatabase myDB;
    private Context context;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //http://developer.android.com/training/basics/data-storage/databases.html
    // Trenger en methode som laster inn/ leser inn data fra forrige gang appen kjørte, eller lager ny db om det er første gang den blir kjørt.
    //getWritableDatabase() or getReadableDatabase() sqlite metoder.
    //public void ReadableDatabase(SQLiteDatabase db) {

   // }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BEERS + "(" + KEY_BEER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BEER_NAME + " TEXT," + KEY_BEER_PRICE + " TEXT," + KEY_BEER_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DRINKS + "(" + KEY_DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DRINK_NAME + " TEXT," + KEY_DRINK_PRICE + " TEXT," + KEY_DRINK_STORE + " TEXT," + KEY_IMAGEURI + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_LOGG + "(" + KEY_LOGG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LOGG_NAME + " TEXT," + KEY_LOGG_PRICE + " TEXT," + KEY_LOGG_STORE + " TEXT," + KEY_LOGG_TIMESTAMP + " TEXT,");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEERS + TABLE_DRINKS + TABLE_LOGG);

        onCreate(db);
    }

    @Override
    public synchronized void close(){
        if(myDB!=null){
            myDB.close();
        }
        super.close();
    }

    /***
     * Check if the database is exist on device or not
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

    public void createLogg(Logg logg) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LOGG_NAME, logg.getLoggName());
        values.put(KEY_LOGG_PRICE, logg.getLoggPrice());
        values.put(KEY_LOGG_STORE, logg.getLoggStore());
        values.put(KEY_LOGG_TIMESTAMP, logg.getTimeStamp());

        db.insert(TABLE_LOGG, null, values);
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

    public Logg getLogg(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOGG, new String[] {KEY_LOGG_ID, KEY_LOGG_NAME, KEY_LOGG_PRICE, KEY_LOGG_STORE, KEY_LOGG_TIMESTAMP}, KEY_LOGG_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null );
        if (cursor != null)
            cursor.moveToFirst();

        Logg logg = new Logg(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));
        db.close();
        cursor.close();
        return logg;
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

    public void deleteLogg(Logg logg) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LOGG, KEY_LOGG_ID + "=?", new String[]{String.valueOf(logg.getId())});
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

    public int getBeverageCount(){
        //SELECT * FROM Logg
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGG, null);
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

    public int updateLogg(Logg logg) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LOGG_NAME, logg.getLoggName());
        values.put(KEY_LOGG_PRICE, logg.getLoggPrice());
        values.put(KEY_LOGG_STORE, logg.getLoggStore());
        values.put(KEY_LOGG_TIMESTAMP, logg.getTimeStamp());

        int rowsAffected = db.update(TABLE_LOGG, values, KEY_LOGG_ID + "=?", new String[]{String.valueOf(logg.getId())});
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

    public List<Logg> getAllLoggs() {
        List<Logg> loggs = new ArrayList<Logg>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGG, null);
        if(cursor.moveToFirst()) {
            do {
                loggs.add(new Logg(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4))));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return loggs;
    }
}
