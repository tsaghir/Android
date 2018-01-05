package com.foi.air1603.sport_manager.adapters;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.foi.air1603.sport_manager.entities.Reservation;

import java.util.List;

/**
 * Created by Generalko on 29-Dec-16.
 */

public class MyReservationsExpandableItem extends Reservation implements Parent<Reservation> {

    private List<Reservation> mReservationsList;

    public MyReservationsExpandableItem(Reservation reservations) {
        super(reservations);
        mReservationsList = reservations.getReservationsChildList();
    }

    @Override
    public List<Reservation> getChildList() {
        return mReservationsList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
