package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Appointment;

import java.util.List;

/**
 * Created by Korisnik on 25-Jan-17.
 */

public interface MyPlacesAppointmentView {

    void showAllAppointment(List<Appointment> appointments);
    void deleteAppointment(Integer id);
    void successfulDeletedAppointment();
    void backFragment();

}
