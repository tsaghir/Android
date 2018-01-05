package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.model.PlaceInteractor;
import com.foi.air1603.sport_manager.model.PlaceInteractorImpl;
import com.foi.air1603.sport_manager.view.PlaceView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karlo on 3.12.2016..
 */

public class PlacePresenterImpl implements PlacePresenter, PresenterHandler {

    public static boolean updateData = false;
    private static PlacePresenterImpl instance;
    private PlaceInteractor placeInteractor;
    private List<Place> places = null;

    private PlaceView view;

    private PlacePresenterImpl() {
    }

    // Implementacija singletona da se ne skidaju placesi kod svakog otvaranja fragmenta
    public static PlacePresenterImpl getInstance() {
        if (instance == null) {
            instance = new PlacePresenterImpl();
        }
        return instance;
    }

    public PlacePresenter Init(PlaceView placeView) {
        this.view = placeView;
        this.placeInteractor = new PlaceInteractorImpl(this);
        instance = this;
        return this;
    }

    public void getAllPlaces() {
        System.out.println("----------------->2. PlacePresenterImpl:getAllPlaces");

        if (this.places == null || updateData) {
            updateData = false;
            placeInteractor.getAllPlacesObjects(this);
        } else {
            getResponseData(places);
        }
    }

    @Override
    public void getResponseData(Object result) {
        Boolean placesAlreadyLoaded = false;

        if (result.getClass() == ArrayList.class && ((ArrayList) result).size() >= 1) {
            placesAlreadyLoaded = true;
        }

        if (!placesAlreadyLoaded) {
            AirWebServiceResponse response = (AirWebServiceResponse) result;

            System.out.println(response.getData());
            Type collectionType = new TypeToken<List<Place>>() {
            }.getType();
            try {
                places = new Gson().fromJson(response.getData(), collectionType);
            } catch (JsonParseException e) {
                System.out.println("[ERROR] " + e);
            }
        }
        if (places != null) {
            view.showAllPlaces(places);
        }
    }
}
