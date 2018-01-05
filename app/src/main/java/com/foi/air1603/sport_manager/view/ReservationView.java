package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Appointment;

import java.util.List;

/**
 * Created by Korisnik on 26-Dec-16.
 */

public interface ReservationView {

    int getDate();

    int getIdPlace();

    void showAppointmentsForDate(List<Appointment> appointments);

    void showSports(List<Integer> id, List<String> name);

    void initializeCalendar();


}
