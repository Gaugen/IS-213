package com.example.beerorganizer;



/**
 * Created by TorArne on 12/1/2015.
 */
public class Logg {

    private String _loggName, _loggPrice,  _loggStore;
    private int _loggId, _loggTimeStamp;

    public Logg(int id, String name, String price, String store, int timestamp) {
        _loggId = id;
        _loggName = name;
        _loggPrice = price;
        _loggStore = store;
        _loggTimeStamp = timestamp;
    }

    public int getId() {return _loggId;}

    public int getTimeStamp() {return _loggTimeStamp;}

    public String getLoggName() {return _loggName;}

    public String getLoggPrice() {return _loggPrice;}

    public String getLoggStore() {return _loggStore;}


}
