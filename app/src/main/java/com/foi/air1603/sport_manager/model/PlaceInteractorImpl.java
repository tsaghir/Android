package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Karlo on 3.12.2016..
 */

public class PlaceInteractorImpl implements PlaceInteractor, DataLoadedListener {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;

    public PlaceInteractorImpl(PresenterHandler mPresenterHandler) {
        this.mPresenterHandler = mPresenterHandler;
        this.dataLoader = new WsDataLoader();
    }

    @Override
    public void getPlaceObject(Object listener, String searchBy, String value) {
        System.out.println("----------------->3. PlaceInteractorImpl:getPlaceObject");
        dataLoader.loadData(this, "getData", "Places", searchBy, value, Place.class, null);
    }

    @Override
    public void getAllPlacesObjects(Object listener) {
        System.out.println("----------------->3. PlaceInteractorImpl:getAllPlacesObjects");
        dataLoader.loadData(this, "getData", "Places", null, null, Place.class, null);
    }

    @Override
    public void setPlaceObject(Place place) {
        System.out.println("----------------->3. PlaceInteractorImpl:setPlaceObject");
        dataLoader.loadData(this, "setData", "Places", null, null, Place.class, place);
    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        System.out.println("----------------->7. PlaceInteractorImpl:onDataLoaded");
        mPresenterHandler.getResponseData(result);
    }

    public void uploadPlacePicture(String fileUri) {
        System.out.println("----------------->3. UserInteractorImpl:changeUserPicture");
        dataLoader.uploadFile(this, fileUri, "Places", 0);
    }
}
