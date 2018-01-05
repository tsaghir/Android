package com.foi.air1603.sport_manager.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.helper.enums.AddPlaceViewEnums;
import com.foi.air1603.sport_manager.model.PlaceInteractor;
import com.foi.air1603.sport_manager.model.PlaceInteractorImpl;
import com.foi.air1603.sport_manager.view.AddPlaceView;
import com.foi.air1603.webservice.AirWebServiceResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marko on 24.12.2016..
 */

public class AddPlacePresenterImpl implements AddPlacePresenter, PresenterHandler {

    private final AddPlaceView view;
    private PlaceInteractor interactor;
    private String filePath = "";
    private static Boolean expectingImageUrl = false;


    public AddPlacePresenterImpl(AddPlaceView addAppointmentView) {
        this.view = addAppointmentView;
        this.interactor = new PlaceInteractorImpl(this);
    }

    @Override
    public void checkInputData(Integer userId) {
        Boolean checkWorkingHoursDifference, requiredFieldsNotEmpty;
        requiredFieldsNotEmpty = checkWorkingHoursDifference = true;


        for (AddPlaceViewEnums input_id : AddPlaceViewEnums.values()) {
            if (view.getInputText(input_id).isEmpty()) {
                view.displayError(input_id, "Polje je obavezno");
                requiredFieldsNotEmpty = false;
            } else {
                view.removeError(input_id);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date start = sdf.parse(view.getInputText(AddPlaceViewEnums.PlaceWorkingHoursStart));
            Date stop = sdf.parse(view.getInputText(AddPlaceViewEnums.PlaceWorkingHoursStop));

            if (start.compareTo(stop) >= 0) {
                view.displayError(AddPlaceViewEnums.PlaceWorkingHoursStop, "Kraj radnog vremena je prije početka");
                checkWorkingHoursDifference = false;
            } else {
                view.removeError(AddPlaceViewEnums.PlaceWorkingHoursStop);
            }
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        if (requiredFieldsNotEmpty && checkWorkingHoursDifference) {
            //MainActivity.showProgressDialog("Spremanje objekta");
            //view.checkPlaceResponse(createNewPlaceObject(userId));
            interactor.setPlaceObject(createNewPlaceObject(userId));
        }
    }

    @Override
    public void addPlacePicture(Intent data, Activity activity) {
        Uri fileUri = data.getData();

        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(fileUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = activity.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        assert cursor != null;
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        System.out.println("----------------->2. ProfilePresenterImpl:changeProfilePicture");
        interactor.uploadPlacePicture(filePath);
        expectingImageUrl = true;
    }

    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->8. ProfilePresenterImpl:getResponseData");

        AirWebServiceResponse response = (AirWebServiceResponse) result;
        if (expectingImageUrl) {
            if (response.getStatusCode() == 200 && response.getMessage() != null) {
                expectingImageUrl = false;
                view.showUploadedImageLink(response.getMessage());
            }
        } else {
            MainActivity.dismissProgressDialog();
            view.returnResponseCode(response.getStatusCode(), response.getMessage());
        }
    }

    private Place createNewPlaceObject(Integer userId) {
        Place place = new Place();

        place.name = view.getInputText(AddPlaceViewEnums.PlaceName);
        place.address = view.getInputText(AddPlaceViewEnums.PlaceAddress);
        place.contact = view.getInputText(AddPlaceViewEnums.PlaceNumber);
        place.workingHoursFrom = view.getInputText(AddPlaceViewEnums.PlaceWorkingHoursStart);
        place.workingHoursTo = view.getInputText(AddPlaceViewEnums.PlaceWorkingHoursStop);
        place.userId = userId;
        place.img = view.getInputText(AddPlaceViewEnums.PlaceImage);

        return place;
    }
}
