package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.model.MyReservationsInteractor;
import com.foi.air1603.sport_manager.model.MyReservationsInteractorImpl;
import com.foi.air1603.sport_manager.model.UserInteractor;
import com.foi.air1603.sport_manager.model.UserInteractorImpl;
import com.foi.air1603.sport_manager.view.InviteFriendsView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Karlo on 31.12.2016..
 */

public class InviteFriendsPresenterImpl implements InviteFriendsPresenter, PresenterHandler {

    //TODO: Dodati validaciju emaila prije searcha, input za naziv tima, counter pozvanih
    // frendova/max players, dovršiti implementaciju u sljedećem sprintu

    private final InviteFriendsView view;
    private final UserInteractor userInteractor;
    private final MyReservationsInteractor reservationsInteractor;
    private Boolean autoCompleteLoaded = false;

    public InviteFriendsPresenterImpl(InviteFriendsView view) {
        this.view = view;
        this.userInteractor = new UserInteractorImpl(this);
        this.reservationsInteractor = new MyReservationsInteractorImpl(this);
    }

    @Override
    public void loadAllUserEmails() {
        userInteractor.getUsersEmails();
    }

    @Override
    public void loadUserByEmail(String userEmail) {
        userInteractor.getUserObject("email", userEmail);
    }

    @Override
    public void reservateAppointment(Reservation userReservation) {
        reservationsInteractor.setReservationsObject(userReservation);
    }

    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->8. InviteFriendsPresenterImpl:getResponseData");

        AirWebServiceResponse response = (AirWebServiceResponse) result;

        if (!autoCompleteLoaded) {
            Type collectionType = new TypeToken<Map<Integer, String>>() {
            }.getType();
            Map<Integer, String> userEmails = new Gson().fromJson(response.getData(), collectionType);
            autoCompleteLoaded = true;
            view.initializeAutoComplete(userEmails);
        } else if (response.data == null && response.statusCode == 200) {
            view.successfulReservation();
        } else {
            Type collectionType = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = new Gson().fromJson(response.getData(), collectionType);
            view.addUserToInviteList(users.get(0));
        }
    }
}
