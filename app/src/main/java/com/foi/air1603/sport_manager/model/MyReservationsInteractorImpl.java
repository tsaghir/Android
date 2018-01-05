package com.foi.air1603.sport_manager.model;

import android.util.Log;

import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Generalko on 28-Dec-16.
 */

public class MyReservationsInteractorImpl implements DataLoadedListener, MyReservationsInteractor {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;

    public MyReservationsInteractorImpl(PresenterHandler presenterHandler) {
        mPresenterHandler = presenterHandler;
        dataLoader = new WsDataLoader();
    }

    @Override
    public void getMyReservationsObject(int userId, long unixTime) {
        try {

            dataLoader.loadData(this, "getData", "Reservations,Sports,Appointments.Places,Teams.Users", "Teams.Users.id;Appointments.date >=", userId + ";" +unixTime, Reservation.class, null);

        } catch (Exception ex) {
            AirWebServiceResponse result = null;
            onDataLoaded(result);
            Log.e("WebService Error: ", ex.getMessage());
        }
    }

    @Override
    public void setReservationsObject(Reservation reservation) {
        dataLoader.loadData(this, "setData", "Reservations", null, null, Reservation.class, reservation);
    }

    @Override
    public void deleteReservationObjectById(int id) {
        dataLoader.loadData(this, "deleteData", "Reservations", "id", id+"", null, null);
    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        mPresenterHandler.getResponseData(result);

    }
}
