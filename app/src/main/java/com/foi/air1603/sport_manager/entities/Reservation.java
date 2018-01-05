package com.foi.air1603.sport_manager.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Generalko on 28-Dec-16.
 */

public class Reservation {

    public int id;
    public String created;
    public String submitted;
    public int confirmed;
    public Appointment appointment;
    public Integer appointmentId;
    public Sport sport;
    public Integer sportId;
    public Team team;
    public transient Integer maxPlayers;

    public Reservation() {
    }

    public Reservation(Reservation reservation) {
        id = reservation.id;
        created = reservation.created;
        appointment = reservation.appointment;
        submitted = reservation.submitted;
        confirmed = reservation.confirmed;
        sport = reservation.sport;
    }

    public List<Reservation> getReservationsChildList() {
        List<Reservation> reservationChildList = new ArrayList<>();
        reservationChildList.add(this);
        return reservationChildList;
    }
}
