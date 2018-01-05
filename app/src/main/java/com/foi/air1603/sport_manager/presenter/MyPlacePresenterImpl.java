package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.model.MyPlaceInteractor;
import com.foi.air1603.sport_manager.model.MyPlaceInteractorImpl;
import com.foi.air1603.sport_manager.view.MyPlacesView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 28-Dec-16.
 */

public class MyPlacePresenterImpl implements MyPlacePresenter, PresenterHandler {

    private static boolean updateData = false;
    private static MyPlacePresenterImpl instance;
    private MyPlaceInteractor myPlaceInteractor;
    private List<Place> myPlaces = null;
    private MyPlacesView myPlacesView;

    private MyPlacePresenterImpl() {
    }

    public static MyPlacePresenterImpl getInstance() {
        if (instance == null) {
            instance = new MyPlacePresenterImpl();
        }
        return instance;
    }

    public MyPlacePresenterImpl Init(MyPlacesView myPlacesView) {
        this.myPlacesView = myPlacesView;
        this.myPlaceInteractor = new MyPlaceInteractorImpl(this);
        instance = this;
        return this;
    }

    @Override
    public void getAllMyPlaces(int id) {
        if (myPlaces == null || updateData) {
            updateData = false;
            String searchBy = "user_id";
            String value = id + "";
            myPlaceInteractor.getAllMyPlacesObjects(this, searchBy, value);
        } else {
            getResponseData(myPlaces);
        }
    }

    @Override
    public void getResponseData(Object result) {
        Boolean myPlacesAlreadyLoaded = false;

        if (result.getClass() == ArrayList.class && ((ArrayList) result).size() >= 1) {
            myPlacesAlreadyLoaded = true;
        }

        if (!myPlacesAlreadyLoaded) {
            AirWebServiceResponse response = (AirWebServiceResponse) result;
            Type collectionType = new TypeToken<List<Place>>() {
            }.getType();

            myPlaces = new Gson().fromJson(response.getData(), collectionType);
        }
        myPlacesView.showMyPlaces(myPlaces);
    }
}
