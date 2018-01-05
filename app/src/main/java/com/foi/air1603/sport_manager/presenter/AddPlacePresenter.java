package com.foi.air1603.sport_manager.presenter;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by marko on 24.12.2016..
 */

public interface AddPlacePresenter {
    void checkInputData(Integer userId);
    void addPlacePicture(Intent data, Activity activity);
}
