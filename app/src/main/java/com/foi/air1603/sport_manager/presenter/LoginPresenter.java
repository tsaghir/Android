package com.foi.air1603.sport_manager.presenter;

/**
 * Created by Karlo on 11.11.2016..
 */

public interface LoginPresenter {
    /**
     * Validate user login
     */
    void checkInputData();
    void checkFacebookUserInDb(String FacebookId);
}
