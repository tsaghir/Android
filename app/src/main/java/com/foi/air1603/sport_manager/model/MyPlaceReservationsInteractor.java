package com.foi.air1603.sport_manager.model;

/**
 * Created by Generalko on 31-Dec-16.
 */

public interface MyPlaceReservationsInteractor {
    void getAllMyPlacesReservationsObject(Object listner, String searchBy);
    void deleteReservationObjectById(Integer id);
}
