package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums;

/**
 * Created by Robert on 11-Nov-16.
 */

public interface RegisterView {
    String getUsernameFromEditText();

    String getPasswordFromEditText();

    String getPassword1FromEditText();

    String getEmailFromEditText();

    String getNameFromEditText();

    String getLastNameFromEditText();

    String getAddressFromEditText();

    String getPhoneNumberFromEditText();

    void displayError(RegisterViewEnums textView, String message);

    void removeError(RegisterViewEnums textView);

    void returnResponseCode(int statusCode, String message);
}
