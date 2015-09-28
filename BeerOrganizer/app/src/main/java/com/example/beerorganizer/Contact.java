package com.example.beerorganizer;

import android.net.Uri;

/**
 * Created by steff_000 on 04.09.2015.
 */
public class Contact {

    private String _name, _phone, _email;
    private Uri _imageUri;
    private int _id;

    public Contact (int id, String name, String phone, String email, Uri imageUri) {
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _imageUri = imageUri;
    }

    public int getId() {return _id;}

    public String getName() {
        return _name;
    }

    public String getPhone() { return _phone; }

    public String getEmail() { return _email; }

    public Uri getImageUri() { return _imageUri; }
}
