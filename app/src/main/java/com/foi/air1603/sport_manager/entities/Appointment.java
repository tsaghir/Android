package com.foi.air1603.sport_manager.entities;

import java.util.List;

/**
 * Created by Korisnik on 26-Dec-16.
 */

public class Appointment {
    public Integer id;
    public Integer placeId;
    public String date;
    public String start;
    public String end;
    public Integer maxPlayers;
    public Place place;
    public List<Reservation> reservations;
    public String password;

    public int getId() {
        return id;
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public String getPassword() { return password;}
}
