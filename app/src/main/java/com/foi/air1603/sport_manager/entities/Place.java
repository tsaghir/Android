package com.foi.air1603.sport_manager.entities;


/**
 * Created by Karlo on 3.12.2016..
 */

public class Place {
    public Integer id;
    public String name;
    public String address;
    public Integer userId;
    public String img;
    public String contact;
    public String workingHoursFrom;
    public String workingHoursTo;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getImg() {
        return img;
    }

    public String getContact() {
        return contact;
    }

    public String getWorkingHoursFrom() {
        return workingHoursFrom;
    }

    public String getWorkingHoursTo() {
        return workingHoursTo;
    }
}
