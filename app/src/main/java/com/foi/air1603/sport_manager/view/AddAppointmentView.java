package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.helper.enums.AddAppointmentViewEnums;

/**
 * Created by Korisnik on 29-Dec-16.
 */

public interface AddAppointmentView {

    String getAppointmentStartFromEditText();

    String getAppointmentEndFromEditText();

    String getMaxPlayer();

    String getPass();

    void initializeCalendar();

    int getDate();

    int getCurrentDate();

    int getIdPlace();

    void displayError(AddAppointmentViewEnums textView, String message);

    void removeError(AddAppointmentViewEnums textView);

    void returnResponseCode(int statusCode, String message);

}
