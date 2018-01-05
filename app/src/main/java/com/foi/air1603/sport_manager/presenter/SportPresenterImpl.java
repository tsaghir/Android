package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.webservice.AirWebServiceResponse;
import com.foi.air1603.sport_manager.entities.Sport;
import com.foi.air1603.sport_manager.model.SportInteractor;
import com.foi.air1603.sport_manager.model.SportInteractorImpl;
import com.foi.air1603.sport_manager.view.ReservationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 27-Dec-16.
 */

public class SportPresenterImpl implements SportPresenter, PresenterHandler {
    SportInteractor sportInteractor;
    private final ReservationView view;
    List<Integer> id = new ArrayList<Integer>();
    List<String> name = new ArrayList<String>();
    List<Sport> sports = null;

    public SportPresenterImpl(ReservationView reservationView) {
        this.view = reservationView;
        this.sportInteractor = new SportInteractorImpl(this);
    }

    @Override
    public void getMultipleSports() {
        sportInteractor.getAllSportsObjects(this);

    }

    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->8. SportPresenterImpl:getResponseData");

        AirWebServiceResponse response = (AirWebServiceResponse) result;
        Type collectionType = new TypeToken<List<Sport>>() {}.getType();
        this.sports = (List<Sport>) new Gson().fromJson(response.getData(), collectionType);
        for (final Sport sport : this.sports) {
            id.add(sport.getId());
            name.add(sport.getName());

        }

        this.view.showSports(id,name);



    }


}
