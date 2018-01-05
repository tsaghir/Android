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
import com.foi.air1603.sport_manager.adapters.MyPlaceRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.presenter.MyPlacePresenter;
import com.foi.air1603.sport_manager.presenter.MyPlacePresenterImpl;
import com.foi.air1603.sport_manager.view.MyPlacesView;

import java.util.List;

/**
 * Created by Korisnik on 28-Dec-16.
 */

public class MyPlacesFragment extends Fragment implements MyPlacesView {
    public User user;
    MyPlacePresenter presenter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleMyPlacesActivity));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressDataLoading));
        return inflater.inflate(R.layout.fragment_my_places, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

        user = activity.getIntent().getExtras().getParcelable("User");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_Myplaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter = MyPlacePresenterImpl.getInstance().Init(this);
        presenter.getAllMyPlaces(user.id);
    }

    @Override
    public void showMyPlaces(List<Place> places) {
        if (places == null){
            MainActivity.dismissProgressDialog();
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastNoFacilities), Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView.setAdapter(new MyPlaceRecycleAdapter(places, this));
        MainActivity.dismissProgressDialog();
    }

    @Override
    public void changeFragmentToAddAppointmentFragment(Place place) {
        Fragment newFragment = new AddAppointmentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("place_id", place.id);
        newFragment.setArguments(bundle);

        MainActivity.replaceFragment(newFragment);
    }

    @Override
    public void changeFragmentToPlaceReservationFragment(Integer id) {
        Fragment newFragment = new MyPlacesReservationFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("place_id", id);
        newFragment.setArguments(bundle);

        MainActivity.replaceFragment(newFragment);
    }

    @Override
    public void changeFragmentToAllAppointmentPlacesFragment(Integer id) {
        Fragment newFragment = new MyPlacesAppointmentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("place_id", id);
        newFragment.setArguments(bundle);

        MainActivity.replaceFragment(newFragment);

    }
}
