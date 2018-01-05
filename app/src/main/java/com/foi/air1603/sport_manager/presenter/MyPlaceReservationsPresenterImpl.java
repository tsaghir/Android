package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.model.MyPlaceReservationsInteractorImpl;
import com.foi.air1603.sport_manager.view.MyPlacesReservationView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Generalko on 31-Dec-16.
 */

public class MyPlaceReservationsPresenterImpl implements PresenterHandler, MyPlaceReservationsPresenter {
    private MyPlacesReservationView myPlacesReservationView;
    private MyPlaceReservationsInteractorImpl myPlaceReservationInteractor;
    List<Reservation> reservationList = null;

    public MyPlaceReservationsPresenterImpl(MyPlacesReservationView myPlacesReservationView) {
        this.myPlacesReservationView = myPlacesReservationView;
        this.myPlaceReservationInteractor = new MyPlaceReservationsInteractorImpl(this);
    }

    @Override
    public void getResponseData(Object result) {
        AirWebServiceResponse response = (AirWebServiceResponse) result;
        if (response.data == null && response.statusCode == 200) {
            myPlacesReservationView.successfulDeletedReservation();
            return;
        }

        Type collectionTypeAppointments = new TypeToken<List<Reservation>>() {
        }.getType();
        reservationList = (List<Reservation>) new Gson().fromJson(response.getData(), collectionTypeAppointments);

        if (reservationList != null){
            myPlacesReservationView.showPlaceReservations(reservationList);
        } else {
            MainActivity.dismissProgressDialog();
            System.out.println("nema vise reza");
            myPlacesReservationView.backFragment();
        }
    }

    @Override
    public void getAllAppointmentsByPlaceId(int placeId) {
        String placeID = placeId+"";
        myPlaceReservationInteractor.getAllMyPlacesReservationsObject(this, placeID);
    }

    @Override
    public void deleteReservationById(int reservationId) {
        myPlaceReservationInteractor.deleteReservationObjectById(reservationId);
    }
}
