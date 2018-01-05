package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Place;

import java.util.List;

/**
 * Created by Karlo on 3.12.2016..
 */
public interface PlaceView {
    void showAllPlaces(List<Place> places);

    void changeFragment(Place place);
}
