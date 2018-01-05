package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.entities.Reservation;

import java.util.List;

/**
 * Created by Generalko on 28-Dec-16.
 */

public interface MyReservationsView {
    void loadRecycleViewData(List<Reservation> reservations);

    void verifyAppointment();

    void setObject(Reservation reservation);

    void deleteReservation(int id);

    void successfulDeletedReservation();

    void backFragment();
    //Reservation getObject();
}
