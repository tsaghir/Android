package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.Appointment;

/**
 * Created by Korisnik on 26-Dec-16.
 */

public interface AppointmentInteractor {

    void getAppointmentsObjects(String searchBy, String value);

    void getAllAppointmentsByPlaceObjects(String searchBy, String value);

    void setAppointmentObject(Appointment appointment);

    void deleteAppointmentObjectById(Integer id);
}
