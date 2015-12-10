package com.example.beerorganizer;

/**
 * Created by EMU on 11.11.2015.
 */
//ResourceManager is a file for keeping track over count and sum functions.
public class ResourceManager {
    private static ResourceManager singleton = new ResourceManager();
    private ResourceManager() {}

    public static ResourceManager getInstance() {
        return singleton;
    }

    protected static int cost_beer = 0;
    protected static int cost_drink = 0;
    protected static int count = 0;
    protected static int sum = 0;
}
