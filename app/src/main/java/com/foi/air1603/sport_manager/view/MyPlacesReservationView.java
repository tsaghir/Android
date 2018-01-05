package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.entities.Reservation;

import java.util.List;

/**
 * Created by Korisnik on 29-Dec-16.
 */

public interface MyPlacesReservationView {
    void showPlaceReservations(List<Reservation> reservationList);
    void deleteReservation(Integer id);
    void successfulDeletedReservation();
    void backFragment();
}
