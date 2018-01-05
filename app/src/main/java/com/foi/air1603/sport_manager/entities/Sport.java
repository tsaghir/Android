package com.foi.air1603.sport_manager.entities;

/**
 * Created by Korisnik on 27-Dec-16.
 */

public class Sport {
    public Integer id;
    public String name;

    public Sport(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
