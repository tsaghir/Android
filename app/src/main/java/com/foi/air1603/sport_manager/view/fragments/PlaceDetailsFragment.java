package com.foi.air1603.sport_manager.view.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.view.PlaceDetailsView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;


/**
 * Created by Korisnik on 06-Dec-16.
 */

public class PlaceDetailsFragment extends Fragment implements PlaceDetailsView {

    public GoogleMap map;
    protected MapView mMapView;
    private Place place;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titlePlaceDetailsActivity));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressMapLoading));
        View v = inflater.inflate(R.layout.fragment_details_place, null);

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(getActivity());
        if (code == ConnectionResult.SUCCESS) {
            mMapView = (MapView) v.findViewById(R.id.mapViewPlace);
            mMapView.onCreate(savedInstanceState);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String place_serialized = bundle.getString("Place");
            Place place = new Gson().fromJson(place_serialized, Place.class);
            this.place = place;
            showPlace();
        }

        Button reservation_btn = (Button) getActivity().findViewById(R.id.buttonPlaceBook);
        reservation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ReservationFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Place", new Gson().toJson(place));
                newFragment.setArguments(bundle);
                MainActivity.replaceFragment(newFragment);

                System.out.println("----------------->RegisterFragment:onClickListener");
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                System.out.println("Error while attempting MapView.onDestroy(), ignoring exception:" + e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void showPlace() {
        TextView txtViewName, txtViewRadnoVrijeme, txtViewKontakt, txtViewAdresa;

        txtViewAdresa = (TextView) getView().findViewById(R.id.tvPlaceAddress);
        txtViewName = (TextView) getView().findViewById(R.id.tvPlaceName);
        txtViewRadnoVrijeme = (TextView) getView().findViewById(R.id.tvPlaceWorkingHours);
        txtViewKontakt = (TextView) getView().findViewById(R.id.tvPlacePhone);

        txtViewAdresa.setText(place.address);
        txtViewName.setText(place.name);
        txtViewRadnoVrijeme.setText(place.workingHoursFrom.substring(0, 5) + " - " + place.workingHoursTo.substring(0, 5));
        txtViewKontakt.setText(place.contact);

        configureMap();
    }

    @SuppressWarnings({"MissingPermission"})
    private void configureMap() throws SecurityException {
        if (mMapView == null) {
            MainActivity.dismissProgressDialog();
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastMapUnavailable), Toast.LENGTH_SHORT).show();
            return;
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                Geocoder coder = new Geocoder(getActivity());
                List<Address> address = null;
                LatLng p1;
                try {
                    address = coder.getFromLocationName(place.address, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());

                MapsInitializer.initialize(getActivity());

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(p1, 15));
                map.addMarker(new MarkerOptions()
                        .position(p1)
                        .title(place.name)
                        .draggable(false).visible(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                MainActivity.dismissProgressDialog();
            }
        });
    }
}
