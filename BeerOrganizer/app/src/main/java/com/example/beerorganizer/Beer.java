package com.example.beerorganizer;

import android.net.Uri;

/**
 * Created by steff_000 on 04.09.2015.
 */

//The beer classes is a layout for objects that we can create and put in the BeerList.
public class Beer {

    private String _beerName, _beerPrice, _beerStore;
    private Uri _imageUri;
    private int _beerId;

    public Beer(int id, String name, String price, String store, Uri imageUri) {
        _beerId = id;
        _beerName = name;
        _beerPrice = price;
        _beerStore = store;
        _imageUri = imageUri;
    }

    public int getId() {return _beerId;}

    public String getBeerName() {
        return _beerName;
    }

    public String getBeerPrice() { return _beerPrice; }

    public String getBeerStore() { return _beerStore; }

    public Uri getImageUri() { return _imageUri; }
}
