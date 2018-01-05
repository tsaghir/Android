package com.foi.air1603.sport_manager.view;

import com.foi.air1603.sport_manager.entities.User;

import java.util.Map;

/**
 * Created by Karlo on 30.12.2016..
 */
public interface InviteFriendsView {
    void initializeAutoComplete(Map<Integer, String> userEmails);

    void addUserToInviteList(User user);

    void successfulReservation();
}
