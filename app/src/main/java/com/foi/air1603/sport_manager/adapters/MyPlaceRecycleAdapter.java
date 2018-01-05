package com.foi.air1603.sport_manager.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Place;
import com.foi.air1603.sport_manager.view.fragments.MyPlacesFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Korisnik on 28-Dec-16.
 */

public class MyPlaceRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MyPlacesFragment context;
    List<Place> places;
    Context cont;

    public MyPlaceRecycleAdapter(List<Place> places, MyPlacesFragment context) {
        this.places = places;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_places_list_item, parent, false);
        MyPlaceRecycleAdapter.MyPlaceViewHolder item = new MyPlaceRecycleAdapter.MyPlaceViewHolder(view);
        cont = parent.getContext();
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Place place = places.get(position);

        ((MyPlaceRecycleAdapter.MyPlaceViewHolder) holder).my_place_name_view.setText(place.name);
        ((MyPlaceRecycleAdapter.MyPlaceViewHolder) holder).my_place_address_view.setText(place.address);
        ((MyPlaceRecycleAdapter.MyPlaceViewHolder) holder).my_place_reserved_appointments_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.changeFragmentToPlaceReservationFragment(place.id);
            }
        });
        ((MyPlaceRecycleAdapter.MyPlaceViewHolder) holder).my_place_add_appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.changeFragmentToAddAppointmentFragment(place);
            }
        });
        ((MyPlaceRecycleAdapter.MyPlaceViewHolder) holder).my_place_reserved_appointments_btn.setText("Rezervirani termini");

        if (place.img != null && !place.img.isEmpty()) {
            Uri uri = Uri.parse(place.img);
            Picasso.with(cont).load(uri).into(((MyPlaceViewHolder) holder).my_place_img_view);
        } else {
            ((MyPlaceViewHolder) holder).my_place_img_view.setImageResource(R.drawable.place_stock);
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class MyPlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView my_place_name_view;
        TextView my_place_address_view;
        ImageView my_place_img_view;
        Button my_place_add_appointment_btn;
        Button my_place_reserved_appointments_btn;

        public MyPlaceViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            my_place_name_view = (TextView) view.findViewById(R.id.my_place_name);
            my_place_add_appointment_btn = (Button) view.findViewById(R.id.btn_my_place_add_appointment);
            my_place_reserved_appointments_btn = (Button) view.findViewById(R.id.btn_my_place_reserved);
            my_place_address_view = (TextView) view.findViewById(R.id.my_place_address);
            my_place_img_view = (ImageView) view.findViewById(R.id.my_place_image);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Place place = places.get(position);
            context.changeFragmentToAllAppointmentPlacesFragment(place.id);

        }
    }
}
