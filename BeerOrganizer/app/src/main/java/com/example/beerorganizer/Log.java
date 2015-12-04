package com.example.beerorganizer;


import android.net.Uri;


/**
 * Created by TorArne on 12/1/2015.
 */
public class Log {

    private String _beverageName, _beveragePrice, _beverageStore;
    private Uri _imageUri;
    private int _beverageId, _beverageTimestamp;

    public Log(int id, String name, String price, String store, Uri imageUri, int timestamp) {
        _beverageId = id;
        _beverageName = name;
        _beveragePrice = price;
        _beverageStore = store;
        _imageUri = imageUri;
        _beverageTimestamp = timestamp;
    }

    public int getId() {return _beverageId;}

    public int getTimeStamp() {return _beverageTimestamp;}

    public String getBeverageName() {return _beverageName;}

    public String getBeveragePrice() {return _beveragePrice;}

    public String getBeverageStore() {return _beverageStore;}

    public Uri getImageUri() {return _imageUri;}


}

