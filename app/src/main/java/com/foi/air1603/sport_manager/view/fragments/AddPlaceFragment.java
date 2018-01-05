package com.foi.air1603.sport_manager.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.helper.enums.AddPlaceViewEnums;
import com.foi.air1603.sport_manager.presenter.AddPlacePresenter;
import com.foi.air1603.sport_manager.presenter.AddPlacePresenterImpl;
import com.foi.air1603.sport_manager.presenter.PlacePresenterImpl;
import com.foi.air1603.sport_manager.view.AddPlaceView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marko on 24.12.2016..
 */

public class AddPlaceFragment extends Fragment implements AddPlaceView {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 43;
    private static final int PICK_IMAGE_REQUEST = 74;
    static Map<String, Integer> inputs = new HashMap<>();

    static {
        inputs.put("PlaceName", R.id.txiPlaceName);
        inputs.put("PlaceAddress", R.id.txiPlaceAddress);
        inputs.put("PlaceNumber", R.id.txiPlaceNumber);
        inputs.put("PlaceWorkingHoursStart", R.id.txiPlaceWorkingHoursStart);
        inputs.put("PlaceWorkingHoursStop", R.id.txiPlaceWorkingHoursStop);
        inputs.put("PlaceImage", R.id.txiPlaceImageUrl);
    }

    private User user;
    private AddPlacePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        user = activity.getIntent().getExtras().getParcelable("User");

        getActivity().setTitle(getResources().getString(R.string.titleAddPlaceFragment));
        View v = inflater.inflate(R.layout.fragment_add_place, null);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AddPlacePresenterImpl(this);

        Button btnAddPlace = (Button) getActivity().findViewById(R.id.buttonPlaceAdd);
        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                presenter.checkInputData(user.id);
            }
        });

        Button btnAddPlacePicture = (Button) getActivity().findViewById(R.id.PlaceAddPicture);
        btnAddPlacePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    openImagePicker();
                }
            }
        });

        final EditText placeStartInput = (EditText) getActivity().findViewById(R.id.etPlaceWorkingHoursStart);
        final EditText placeStopInput = (EditText) getActivity().findViewById(R.id.etPlaceWorkingHoursStop);

        placeStartInput.setFocusable(false);
        placeStopInput.setFocusable(false);

        placeStartInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selH = String.valueOf(selectedHour);
                        String selM = String.valueOf(selectedMinute);
                        if (selectedHour < 10) selH = "0" + selectedHour;
                        if (selectedMinute < 10) selM = "0" + selectedMinute;
                        placeStartInput.setText(selH + ":" + selM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.titleTimePicker));
                mTimePicker.show();

            }
        });

        placeStopInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selH = String.valueOf(selectedHour);
                        String selM = String.valueOf(selectedMinute);
                        if (selectedHour < 10) selH = "0" + selectedHour;
                        if (selectedMinute < 10) selM = "0" + selectedMinute;
                        placeStopInput.setText(selH + ":" + selM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getResources().getString(R.string.titleTimePicker));
                mTimePicker.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker();
                } else {
                    System.out.println("Couldn't get read files permission!");
                }
            }
        }
    }

    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getInputText(AddPlaceViewEnums textView) {
        TextInputLayout textInputLayout = (TextInputLayout) getActivity().findViewById(inputs.get(textView.toString()));
        EditText editText = textInputLayout.getEditText();
        assert editText != null;
        return editText.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("----------------->1. AddPlaceFragment:onActivityResult");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            MainActivity.showProgressDialog("Uploadanje slike");
            System.out.println("----------------->1. AddPlaceFragment:onActivityResult");
            presenter.addPlacePicture(data, getActivity());
        }
    }

    @Override
    public void showUploadedImageLink(String message) {
        MainActivity.dismissProgressDialog();
        System.out.println("----------------->9. AddPlaceFragment:showUploadedImageLink " + message);
        ((TextInputLayout) getActivity().findViewById(inputs.get(AddPlaceViewEnums.PlaceImage.toString()))).getEditText().setText(message);
    }

    @Override
    public void displayError(AddPlaceViewEnums textView, String message) {
        if (message.equals("Polje je obavezno")){
            message = getResources().getString(R.string.errorFieldNecessary);
        } else if (message.equals("Kraj radnog vremena je prije početka")){
            message = getResources().getString(R.string.errorWorkingHours);
        }

        ((TextInputLayout) getActivity().findViewById(inputs.get(textView.toString()))).setError(message);
    }

    @Override
    public void removeError(AddPlaceViewEnums textView) {
        ((TextInputLayout) getActivity().findViewById(inputs.get(textView.toString()))).setErrorEnabled(false);
    }

    @Override
    public void checkPlaceResponse(Place place) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Place", new Gson().toJson(place));
        Fragment newFragment = new AddAppointmentFragment();
        newFragment.setArguments(bundle);

        MainActivity.replaceFragment(newFragment);
    }

    @Override
    public void returnResponseCode(int statusCode, String message) {
        PlacePresenterImpl.updateData = true;
        if (statusCode == 200) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastFacilityAddedTrue), Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastFacilityAddedFalse) + message, Toast.LENGTH_LONG).show();
        }
    }
}
