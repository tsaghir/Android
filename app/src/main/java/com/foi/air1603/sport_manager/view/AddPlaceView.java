package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.helper.enums.AddPlaceViewEnums;

/**
 * Created by marko on 24.12.2016..
 */

public interface AddPlaceView {
    interface AddAppointment{
        void returnWholePlace(Place place);
    }
    String getInputText(AddPlaceViewEnums textView);

    void showUploadedImageLink(String message);

    void displayError(AddPlaceViewEnums textView, String message);

    void removeError(AddPlaceViewEnums textView);

    void returnResponseCode(int statusCode, String message);

    void checkPlaceResponse(Place place);
}
