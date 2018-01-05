package com.foi.air1603.sport_manager.view.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.adapters.PlaceRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.helper.enums.Rights;
import com.foi.air1603.sport_manager.presenter.PlacePresenter;
import com.foi.air1603.sport_manager.presenter.PlacePresenterImpl;
import com.foi.air1603.sport_manager.view.PlaceView;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Karlo on 3.12.2016..
 */

public class AllPlacesFragment extends android.app.Fragment implements PlaceView {
    PlacePresenter presenter;
    FloatingActionButton fabAdd;
    Rights rights;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleAllPlacesActivity));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressDataLoading));

        View v = inflater.inflate(R.layout.fragment_places, null);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.replaceFragment(new AddPlaceFragment());
            }
        });
        hideFloatingActionButton();

        presenter = PlacePresenterImpl.getInstance().Init(this);
        presenter.getAllPlaces();

        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshAllPlaces);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PlacePresenterImpl.updateData = true;
                presenter.getAllPlaces();
            }
        });

    }

    private void hideFloatingActionButton() {
        rights = rights.getRightFormInt(MainActivity.user.type);
        switch (rights) {
            case User:
                fabAdd.hide();
        }
    }

    @Override
    public void showAllPlaces(List<Place> places) {
        System.out.println("9. AllPlacesFragment:showAllPlaces");
        recyclerView.setAdapter(new PlaceRecycleAdapter(places, this));
        MainActivity.dismissProgressDialog();

        recyclerView.getAdapter().notifyDataSetChanged();
        if(swipeContainer != null){
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void changeFragment(Place place) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Place", new Gson().toJson(place));
        Fragment newFragment = new PlaceDetailsFragment();
        newFragment.setArguments(bundle);

        MainActivity.replaceFragment(newFragment);
    }
}
