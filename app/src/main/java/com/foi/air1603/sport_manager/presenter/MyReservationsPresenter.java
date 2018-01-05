package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Reservation;

/**
 * Created by Karlo on 31.12.2016..
 */

public interface MyReservationsPresenter {
    void getUserReservationsData();
    void updateReservation(Reservation reservation);
    void deleteReservationById(int id);
}
