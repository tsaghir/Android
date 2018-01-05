package com.foi.air1603.sport_manager.presenter;

/**
 * Created by Generalko on 31-Dec-16.
 */

public interface MyPlaceReservationsPresenter {
    void getAllAppointmentsByPlaceId(int placeId);
    void deleteReservationById(int reservationId);
}
