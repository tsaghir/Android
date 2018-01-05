package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.presenter.PlacePresenterImpl;

/**
 * Created by Karlo on 3.12.2016..
 */

public interface PlaceInteractor {
    void getPlaceObject(Object listener, String searchBy, String value);

    void getAllPlacesObjects(Object listner);

    void setPlaceObject(Place place);

    void uploadPlacePicture(String fileUri);
}
