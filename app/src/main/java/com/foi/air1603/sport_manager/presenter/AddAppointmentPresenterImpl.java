package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.helper.enums.AddAppointmentViewEnums;
import com.foi.air1603.sport_manager.model.AppointmentInteractor;
import com.foi.air1603.sport_manager.model.AppointmentInteractorImpl;
import com.foi.air1603.sport_manager.model.MyReservationsInteractor;
import com.foi.air1603.sport_manager.view.AddAppointmentView;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Korisnik on 30-Dec-16.
 */

public class AddAppointmentPresenterImpl implements AddAppointmentPresenter, PresenterHandler {

    private final AddAppointmentView view;
    private Boolean checkTimeStart, checkTimeEnd, checkMaxPlayers, checkPlayersSize;
    private AppointmentInteractor appointmentInteractor;
    private MyReservationsInteractor reservationsInteractor;

    public AddAppointmentPresenterImpl(AddAppointmentView addAppointmentView) {
        this.view = addAppointmentView;
        this.appointmentInteractor = new AppointmentInteractorImpl(this);
    }

    @Override
    public void validateAppointment() {
        checkTimeStart = checkTimeEnd = checkMaxPlayers = checkPlayersSize = true;

        if (view.getAppointmentStartFromEditText().isEmpty()) {
            view.displayError(AddAppointmentViewEnums.AppointmentTimeStart, "Polje je obavezno");
            checkTimeStart = false;
        } else {
            view.removeError(AddAppointmentViewEnums.AppointmentTimeStart);
        }

        if (view.getAppointmentEndFromEditText().isEmpty()) {
            view.displayError(AddAppointmentViewEnums.AppointmentTimeEnd, "Polje je obavezno");
            checkTimeEnd = false;
        } else {
            view.removeError(AddAppointmentViewEnums.AppointmentTimeEnd);
        }

        if (view.getMaxPlayer().isEmpty() || Integer.parseInt(view.getMaxPlayer()) < 1) {
            view.displayError(AddAppointmentViewEnums.MaxPlayers, "Mora biti barem jedan sudionik");
            checkPlayersSize = false;
        } else {
            view.removeError(AddAppointmentViewEnums.MaxPlayers);
        }

        if (checkTimeStart && checkTimeEnd && checkPlayersSize) {
           // Appointment app = createNewAppointmentObject();
            appointmentInteractor.setAppointmentObject(createNewAppointmentObject());
        }
    }

    private Appointment createNewAppointmentObject() {
        Appointment appointment = new Appointment();
        appointment.placeId = view.getIdPlace();

        java.util.Date utilDate=new java.util.Date((long)view.getDate()*1000);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        appointment.date = sqlDate.toString();
        System.out.println("---------------" + sqlDate.toString());
        appointment.start = view.getAppointmentStartFromEditText();
        appointment.end = view.getAppointmentEndFromEditText();
        String maxPly = view.getMaxPlayer();
        appointment.maxPlayers = Integer.parseInt(maxPly);
        appointment.password = view.getPass();

        return appointment;
    }

    @Override
    public void getResponseData(Object result) {
        AirWebServiceResponse test = (AirWebServiceResponse) result;

        view.returnResponseCode(test.getStatusCode(), test.getMessage());
    }
}
