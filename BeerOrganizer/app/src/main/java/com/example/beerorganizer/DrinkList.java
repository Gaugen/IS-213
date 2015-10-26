package com.example.beerorganizer;

import android.net.Uri;

import java.net.URI;

/**
 * Created by larsenj on 23.10.2015.
 */
public class DrinkList {
    private String _drinkName, _drinkPrice, _drinkStore;
    private Uri _imageUri;
    private int _drinkId;

    public DrinkList(int id, String drinkName, String drinkPrice, String drinkStore, Uri imageUri) {
        _drinkId = id;
        _drinkName = drinkName;
        _drinkPrice = drinkPrice;
        _drinkStore = drinkStore;
        _imageUri = imageUri;
    }

    public int getId() {return _drinkId;}

    public String getDrinkName() {
        return _drinkName;
    }

    public String getDrinkPrice() { return _drinkPrice; }

    public String getDrinkStore() { return _drinkStore; }

    public Uri getImageUri() { return _imageUri; }
}
