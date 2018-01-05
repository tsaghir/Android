package com.foi.air1603.sport_manager.model;

import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.presenter.PresenterHandler;
import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Created by Generalko on 12.11.2016..
 */

public class UserInteractorImpl implements UserInteractor, DataLoadedListener {

    private DataLoader dataLoader;
    private PresenterHandler mPresenterHandler;

    public UserInteractorImpl(PresenterHandler presenterHandler) {
        mPresenterHandler = presenterHandler;
        this.dataLoader = new WsDataLoader();
    }

    @Override
    public void getUsersEmails() {
        System.out.println("----------------->3. UserInteractorImpl:getUsersEmails");
        dataLoader.loadData(this, "getList", "Users", "email", "", User.class, null);
    }

    @Override
    public void getUserObject(String searchBy, String value) {
        System.out.println("----------------->3. UserInteractorImpl:getUserObject");
        dataLoader.loadData(this, "getData", "Users", searchBy, value, User.class, null);
    }

    public void changeUserPicture(String fileUri, Integer user_id) {
        System.out.println("----------------->3. UserInteractorImpl:changeUserPicture");
        dataLoader.uploadFile(this, fileUri, "Users", user_id);
    }

    @Override
    public void setUserObject(User user) {
        System.out.println("----------------->3. UserInteractorImpl:setUserObject");
        dataLoader.loadData(this, "setData", "Users", null, null, User.class, user);
    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        System.out.println("----------------->7. UserInteractorImpl:onDataLoaded");
        mPresenterHandler.getResponseData(result);
    }
}
