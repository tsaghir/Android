package com.foi.air1603.sport_manager.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.view.fragments.MyReservationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Generalko on 28-Dec-16.
 */

public class MyReservationsChildViewHolder extends ChildViewHolder {

    @BindView(R.id.myAppointmentType)
    TextView mReservationType;
    @BindView(R.id.myAppointmentAddress)
    TextView mReservationAddress;
    @BindView(R.id.myAppointmentPlayers)
    TextView mReservationPlayers;
    @BindView(R.id.myAppointmentID)
    TextView mReservationValue;
    @BindView(R.id.myAppointmentConfirm)
    Button myAppointmentConfirm;
    @BindView(R.id.myAppointmentDelete)
    Button myAppointmentDelete;


    MyReservationsRecycleAdapter mAdapter;
    View mItemView;
    Activity mActivity;
    MyReservationsFragment co;
    public String pass;

    public MyReservationsChildViewHolder(@NonNull View itemView, MyReservationsRecycleAdapter adapter, MyReservationsFragment con) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
        ButterKnife.bind(this, itemView);
        this.co = con;
    }

    public void bind(final Reservation reservation) {
        mReservationType.setText(reservation.sport.name);
        mReservationAddress.setText(reservation.appointment.place.address);
        mReservationPlayers.setText(reservation.team.name);
        mReservationValue.setText("ID " + reservation.id + "");
        if(reservation.team.userId != MainActivity.user.id || reservation.confirmed != 0){
            myAppointmentConfirm.setVisibility(View.INVISIBLE);
            myAppointmentDelete.setVisibility(View.INVISIBLE);

        } else {
           this.pass = reservation.appointment.password;
        }
        myAppointmentConfirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                co.setObject(reservation);
                co.verifyAppointment();
            }
        });
        myAppointmentDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                co.deleteReservation(reservation.id);
            }
        });


    }

}
