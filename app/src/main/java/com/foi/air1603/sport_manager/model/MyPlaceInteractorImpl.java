package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Korisnik on 28-Dec-16.
 */

public class MyPlaceInteractorImpl implements MyPlaceInteractor, DataLoadedListener {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;
    private MyPlaceInteractor mListener;

    public MyPlaceInteractorImpl(PresenterHandler presenterHandler) {
        mPresenterHandler = presenterHandler;
        this.dataLoader = new WsDataLoader();
    }

    @Override
    public void getAllMyPlacesObjects(Object listner, String searchBy, String value) {
        dataLoader.loadData(this, "getData", "Places", searchBy, value, Place.class, null);
    }


    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        mPresenterHandler.getResponseData(result);
    }


}
