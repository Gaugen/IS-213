package com.example.beerorganizer;

/**
 * Created by EMU on 11.11.2015.
 */
public class ResourceManager {
    private static ResourceManager singleton = new ResourceManager();
    private ResourceManager() {}

    public static ResourceManager getInstance() {
        return singleton;
    }

    protected static int cost = 0;
}
