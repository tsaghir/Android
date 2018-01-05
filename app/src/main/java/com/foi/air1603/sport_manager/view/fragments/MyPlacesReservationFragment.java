package com.foi.air1603.sport_manager.view.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.adapters.MyPlaceReservationsRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.presenter.MyPlaceReservationsPresenterImpl;
import com.foi.air1603.sport_manager.presenter.MyPlacesAppointmentPresenterImpl;
import com.foi.air1603.sport_manager.view.MyPlacesReservationView;

import java.util.List;

/**
 * Created by Korisnik on 29-Dec-16.
 */

public class MyPlacesReservationFragment extends Fragment implements MyPlacesReservationView {
    private RecyclerView recyclerView;
    MyPlaceReservationsPresenterImpl presenter;
    private MainActivity activity;
    int searchParameter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleMyPlacesReservationActivity));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressDataLoading));
        View v = inflater.inflate(R.layout.fragment_my_places_reservations_list, null);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            searchParameter = bundle.getInt("place_id");
        }
        activity = (MainActivity) getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_MyplacesReservationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new MyPlaceReservationsPresenterImpl(this);
        presenter.getAllAppointmentsByPlaceId(searchParameter);

    }

    @Override
    public void showPlaceReservations(List<Reservation> reservationList) {
        MainActivity.dismissProgressDialog();
        recyclerView.setAdapter(new MyPlaceReservationsRecycleAdapter(this, reservationList));
    }

    @Override
    public void deleteReservation(Integer id) {
        presenter.deleteReservationById(id);

    }

    @Override
    public void successfulDeletedReservation() {
        Toast.makeText(getActivity(),
                getResources().getString(R.string.toastReservationDeleted), Toast.LENGTH_LONG).show();
        MyPlacesAppointmentPresenterImpl.updateAppointments = true;
        presenter.getAllAppointmentsByPlaceId(searchParameter);

    }

    @Override
    public void backFragment() {
        getFragmentManager().popBackStack();
        Toast.makeText(getActivity(), getResources().getString(R.string.toastNoReservation), Toast.LENGTH_LONG).show();
    }

}
