package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Generalko on 31-Dec-16.
 */

public class MyPlaceReservationsInteractorImpl implements MyPlaceReservationsInteractor, DataLoadedListener {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;

    public MyPlaceReservationsInteractorImpl(PresenterHandler mPresenterHandler) {
        this.mPresenterHandler = mPresenterHandler;
        this.dataLoader = new WsDataLoader();
    }

    @Override
    public void getAllMyPlacesReservationsObject(Object listner, String place_id) {
        //dataLoader.loadData(this, "getData", "Appointments,Reservations.Sports,Reservations.Teams.Users", "place_id;Appointments.date >=" , place_id + ";" +System.currentTimeMillis()/1000, Appointment.class, null);
        dataLoader.loadData(this, "getData", "Reservations,Appointments.Places,Sports,Teams.Users", "Places.id;Appointments.date >=" , place_id + ";" +System.currentTimeMillis()/1000, Reservation.class, null);
    }

    @Override
    public void deleteReservationObjectById(Integer id) {
        dataLoader.loadData(this, "deleteData", "Reservations", "id", id+"", null, null);
    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        mPresenterHandler.getResponseData(result);
    }
}
