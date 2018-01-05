package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.model.MyReservationsInteractor;
import com.foi.air1603.sport_manager.model.MyReservationsInteractorImpl;
import com.foi.air1603.sport_manager.view.MyReservationsView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyReservationsPresenterImpl implements MyReservationsPresenter, PresenterHandler {

    public static boolean updateData = false;
    private static MyReservationsPresenterImpl instance;
    private Boolean myReservationAlreadyLoaded = false;
    private MyReservationsView myReservationsView;
    private MyReservationsInteractor myReservationsInteractor;
    private List<Reservation> reservationsList = null;

    private MyReservationsPresenterImpl() {
    }

    public static MyReservationsPresenterImpl getInstance() {
        if (instance == null) {
            instance = new MyReservationsPresenterImpl();
        }
        return instance;
    }

    public MyReservationsPresenter Init(MyReservationsView myReservationsView) {
        this.myReservationsView = myReservationsView;
        this.myReservationsInteractor = new MyReservationsInteractorImpl(this);
        instance = this;
        return this;
    }

    @Override
    public void getUserReservationsData() {
        if (this.reservationsList == null || updateData) {
            updateData = false;
            long unixTime = System.currentTimeMillis() / 1000L;
            myReservationsInteractor.getMyReservationsObject(MainActivity.user.id, unixTime);
        } else {
            getResponseData(reservationsList);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        Reservation temp = new Reservation();
        temp.id = reservation.id;
        temp.confirmed = 1;

        myReservationsInteractor.setReservationsObject(temp);
    }

    @Override
    public void deleteReservationById(int id) {
        myReservationsInteractor.deleteReservationObjectById(id);
    }

    @Override
    public void getResponseData(Object result) {

        if (result.getClass() == ArrayList.class && ((ArrayList) result).size() >= 0) {
            myReservationAlreadyLoaded = true;
        }

        System.out.println("----------------MyReservationPresenterImpl:getResponseData");
        if (!myReservationAlreadyLoaded) {
            AirWebServiceResponse response = (AirWebServiceResponse) result;
            if (response.data == null && response.statusCode == 200) {
                myReservationsView.successfulDeletedReservation();
                return;
            }

            Type collectionType = new TypeToken<List<Reservation>>() {
            }.getType();
            reservationsList = new Gson().fromJson(response.getData(), collectionType);
        }
        if (reservationsList != null) {
            myReservationsView.loadRecycleViewData(reservationsList);
        } else {
            MainActivity.dismissProgressDialog();
            myReservationsView.backFragment();
        }
    }
}
