package com.foi.air1603.sport_manager.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.foi.air1603.sport_manager.model.UserInteractor;
import com.foi.air1603.sport_manager.model.UserInteractorImpl;
import com.foi.air1603.sport_manager.view.ProfileView;
import com.foi.air1603.webservice.AirWebServiceResponse;


/**
 * Created by Karlo on 25.12.2016..
 */

public class ProfilePresenterImpl implements ProfilePresenter, PresenterHandler {

    private ProfileView view;
    private String filePath = "";
    private Uri fileUri;

    public ProfilePresenterImpl(ProfileView profileView) {
        this.view = profileView;
    }

    @Override
    public void changeProfilePicture(Intent data, Integer user_id, Activity activity) {
        fileUri = data.getData();

        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(fileUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];
        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = activity.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        System.out.println("----------------->2. ProfilePresenterImpl:changeProfilePicture");
        UserInteractor interactor = new UserInteractorImpl(this);
        interactor.changeUserPicture(filePath, user_id);

    }

    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->8. ProfilePresenterImpl:getResponseData");

        AirWebServiceResponse response = (AirWebServiceResponse) result;

        if(response.getStatusCode() == 200 && response.getMessage() != null){
            view.getImageForImageView(response.getMessage(), fileUri);
        }

    }


}
