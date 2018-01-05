package com.foi.air1603.sport_manager.loaders;


import com.foi.air1603.webservice.AirWebServiceResponse;

/**
 * Listener koji čeka da se učitaju podaci
 * Created by Karlo on 9.11.2016.
 */
public interface DataLoadedListener {
    void onDataLoaded(AirWebServiceResponse result);
}