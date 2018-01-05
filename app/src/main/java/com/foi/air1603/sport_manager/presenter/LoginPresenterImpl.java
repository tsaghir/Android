package com.foi.air1603.sport_manager.presenter;


import com.foi.air1603.sport_manager.BaseActivity;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.model.UserInteractor;
import com.foi.air1603.sport_manager.model.UserInteractorImpl;
import com.foi.air1603.sport_manager.view.LoginView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.foi.air1603.sport_manager.helper.enums.LoginViewEnums.Password;
import static com.foi.air1603.sport_manager.helper.enums.LoginViewEnums.Username;

/**
 * Created by Generalko on 10.11.2016..
 */

public class LoginPresenterImpl implements LoginPresenter, UserInteractor.OnLoginFinishedListener, PresenterHandler {

    public static User facebookUser = null;
    private final LoginView view;
    private UserInteractor userInteractor;
    private User user;

    public LoginPresenterImpl(LoginView loginView) {
        this.view = loginView;
        this.userInteractor = new UserInteractorImpl(this);
    }

    @Override
    public void checkInputData() {
        if (view.getUsernameFromEditText().isEmpty()) {
            view.displayError(Username, "Unesite vrijednost");
        }
        if (view.getPasswordFromEditText().isEmpty()) {
            view.displayError(Password, "Unesite vrijednost");
        }
        if (!view.getUsernameFromEditText().isEmpty() && !view.getPasswordFromEditText().isEmpty()) {
            view.removeError(Username);
            view.removeError(Password);
            //BaseActivity.showProgressDialog("Provjera podataka");
            compareInputTextToData();
        }
    }
    private void compareInputTextToData() {
        userInteractor.getUserObject("username", view.getUsernameFromEditText());
    }

    @Override
    public void checkFacebookUserInDb(String FacebookId) {
        userInteractor.getUserObject("facebook_id", FacebookId);
    }

    @Override
    public void onUsernameError() {
        view.displayError(Username, "Korisničko ime ne postoji");
    }

    @Override
    public void onPasswordError() {
        view.displayError(Password, "Unijeli ste krivu lozinku");
    }

    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->8. LoginPresenterImpl:getResponseData");

        AirWebServiceResponse response = (AirWebServiceResponse) result;

        Type collectionType = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = new Gson().fromJson(response.getData(), collectionType);

        BaseActivity.dismissProgressDialog();

        if(view.userLoggedInFacebook()){
            if(users == null){
                view.buildAlertDialogMessage();
                view.createNewFaceUserObject();
                view.loginSuccessful(RegisterPresenterImpl.faceBookUser);
            }else{
                user = users.get(0);
                view.loginSuccessful(user);
            }
        }

        if (users == null) {
            onUsernameError();
        } else {
            user = users.get(0);
            System.out.println("LoginPresenterImpl:getResponseData: " + user.toString());

            view.removeError(Username);
            String userEnteredPassword = BaseActivity.get_SHA_512_SecurePassword(view.getPasswordFromEditText(), "");
            System.out.println(userEnteredPassword);
            if (!userEnteredPassword.equals(user.password)) {
                onPasswordError();
            } else {
                view.removeError(Password);


                view.loginSuccessful(user);
            }
        }
    }
}
