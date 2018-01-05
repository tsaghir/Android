package com.foi.air1603.sport_manager.presenter;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.model.AppointmentInteractor;
import com.foi.air1603.sport_manager.model.AppointmentInteractorImpl;
import com.foi.air1603.sport_manager.view.MyPlacesAppointmentView;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 25-Jan-17.
 */

public class MyPlacesAppointmentPresenterImpl implements MyPlacesAppointmentPresenter, PresenterHandler {

    public static boolean updateAppointments = false;
    private static MyPlacesAppointmentPresenterImpl instance;
    private AppointmentInteractor appointmentInteractor;
    private List<Appointment> appointments = null;
    private int idP;

    private MyPlacesAppointmentView view;

    public MyPlacesAppointmentPresenterImpl() {
    }

    public static MyPlacesAppointmentPresenterImpl getInstance() {
        if (instance == null) {
            instance = new MyPlacesAppointmentPresenterImpl();
        }
        return instance;
    }

    public MyPlacesAppointmentPresenter Init(MyPlacesAppointmentView myPlacesAppointmentView) {
        this.view = myPlacesAppointmentView;
        this.appointmentInteractor = new AppointmentInteractorImpl(this);
        instance = this;
        return this;
    }
    @Override
    public void getAllAppointmentByPlaceID(Integer id) {
        if (this.appointments == null || idP != id || updateAppointments) {
            updateAppointments = false;
            this.idP = id;
            String searchBy = "place_id";
            String value = id + "";
            appointmentInteractor.getAllAppointmentsByPlaceObjects(searchBy,value);
        } else {
                getResponseData(appointments);
        }

    }

    @Override
    public void deleteAppointmentById(Integer id) {
        appointmentInteractor.deleteAppointmentObjectById(id);

    }

    @Override
    public void getResponseData(Object result) {

        Boolean placesAlreadyLoaded = false;

        if (result.getClass() == ArrayList.class && ((ArrayList) result).size() >= 0) {
            placesAlreadyLoaded = true;
        }

        if (!placesAlreadyLoaded) {
            AirWebServiceResponse response = (AirWebServiceResponse) result;
            if (response.data == null && response.statusCode == 200) {
                view.successfulDeletedAppointment();
                return;
            }
            System.out.println(response.getData());
            Type collectionType = new TypeToken<List<Appointment>>() {
            }.getType();
            try {
                appointments = new Gson().fromJson(response.getData(), collectionType);
            } catch (JsonParseException e) {
                System.out.println("[ERROR] " + e);
            }
        }

        if (appointments != null) {
            view.showAllAppointment(appointments);
        } else {
            MainActivity.dismissProgressDialog();
            view.backFragment();
        }

    }
}
