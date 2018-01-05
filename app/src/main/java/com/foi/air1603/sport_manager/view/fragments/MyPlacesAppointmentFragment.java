package com.foi.air1603.sport_manager.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.adapters.MyPlaceAppointmentRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Appointment;

import com.foi.air1603.sport_manager.presenter.MyPlacesAppointmentPresenter;
import com.foi.air1603.sport_manager.presenter.MyPlacesAppointmentPresenterImpl;
import com.foi.air1603.sport_manager.view.MyPlacesAppointmentView;

import java.util.List;

/**
 * Created by Korisnik on 25-Jan-17.
 */

public class MyPlacesAppointmentFragment  extends android.app.Fragment implements MyPlacesAppointmentView {

    MyPlacesAppointmentPresenter presenter;
    private RecyclerView recyclerView;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleMyPlacesAppointmentFragment));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressDataLoading));
        View v = inflater.inflate(R.layout.fragment_my_places_appointments, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.id = bundle.getInt("place_id");
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_appointments);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            presenter = MyPlacesAppointmentPresenterImpl.getInstance().Init(this);
            presenter.getAllAppointmentByPlaceID(id);
        }

    }

    @Override
    public void showAllAppointment(List<Appointment> appointments) {

        if (appointments == null){
            MainActivity.dismissProgressDialog();
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastNoTermsForFacility), Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println("trebam uci u adapter");
        recyclerView.setAdapter(new MyPlaceAppointmentRecycleAdapter(appointments, this));
        MainActivity.dismissProgressDialog();

        }

    @Override
    public void deleteAppointment(Integer id) {
        presenter.deleteAppointmentById(id);

    }

    @Override
    public void successfulDeletedAppointment() {
        Toast.makeText(getActivity(),
                getResources().getString(R.string.toastAppointmentDeleted), Toast.LENGTH_LONG).show();
        MyPlacesAppointmentPresenterImpl.updateAppointments = true;
        presenter.getAllAppointmentByPlaceID(this.id);

    }

    @Override
    public void backFragment() {
        getFragmentManager().popBackStack();
        Toast.makeText(getActivity(), getResources().getString(R.string.toastNoTerm), Toast.LENGTH_LONG).show();
    }
}
