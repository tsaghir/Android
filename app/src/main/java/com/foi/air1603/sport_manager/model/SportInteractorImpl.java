package com.foi.air1603.sport_manager.model;

import com.foi.air1603.webservice.AirWebServiceResponse;
import com.foi.air1603.sport_manager.entities.Sport;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.sport_manager.presenter.SportPresenterImpl;

/**
 * Created by Korisnik on 27-Dec-16.
 */

public class SportInteractorImpl implements SportInteractor, DataLoadedListener {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;
    private AppointmentInteractor mListener;

    public SportInteractorImpl(PresenterHandler presenterHandler) {
        mPresenterHandler = presenterHandler;
        this.dataLoader = new WsDataLoader();
    }


    @Override
    public void getAllSportsObjects(Object listner) {
        dataLoader.loadData(this, "getData", "Sports", null, null, Sport.class, null);

    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        mPresenterHandler.getResponseData(result);

    }


}
