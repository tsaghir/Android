package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Reservation;

/**
 * Created by Karlo on 31.12.2016..
 */

public interface InviteFriendsPresenter {
    void loadAllUserEmails();

    void loadUserByEmail(String userEmail);

    void reservateAppointment(Reservation userReseravation);
}
