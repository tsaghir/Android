package com.foi.air1603.sport_manager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Appointment;
import com.foi.air1603.sport_manager.view.fragments.MyPlacesAppointmentFragment;

import java.util.List;

/**
 * Created by Korisnik on 25-Jan-17.
 */

public class MyPlaceAppointmentRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    List<Appointment> appointmens;
    MyPlacesAppointmentFragment context;
    Context cont;

    public MyPlaceAppointmentRecycleAdapter(List<Appointment> appointments, MyPlacesAppointmentFragment context) {
        this.appointmens = appointments;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_places_appointments_list_item, parent, false);
        AppointmentViewHolder item = new AppointmentViewHolder(view);
        cont = parent.getContext();
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Appointment appointment = appointmens.get(position);
        String string = appointment.date;
        string = string.substring(0, string.indexOf(" "));
        String[] parts = string.split("-");
        String date = parts[2]+"."+parts[1]+"."+parts[0]+".";
        ((AppointmentViewHolder) holder).appointment_date_view.setText(date);
        ((AppointmentViewHolder) holder).appointment_id_view.setText("ID " + appointment.id);
        ((AppointmentViewHolder) holder).appointment_start_view.setText(appointment.start+" -");
        ((AppointmentViewHolder) holder).appointment_end_view.setText(appointment.end);
        ((AppointmentViewHolder) holder).appointment_maxPlayers_view.setText("No.: " + appointment.maxPlayers);
        if(appointment.password != null) {
            ((AppointmentViewHolder) holder).appointment_password_view.setText("Password: " + appointment.password);
        } else {
            ((AppointmentViewHolder) holder).appointment_password_view.setText("");
        }
        ((AppointmentViewHolder) holder).appointment_img_view.setImageResource(R.drawable.running);

        ((AppointmentViewHolder) holder).appointment_delete_view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                context.deleteAppointment(appointment.id);
            }
        });


    }

    @Override
    public int getItemCount() {
        return appointmens.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView appointment_end_view;
        TextView appointment_date_view;
        TextView appointment_id_view;
        TextView appointment_start_view;
        TextView appointment_maxPlayers_view;
        TextView appointment_password_view;
        Button appointment_delete_view;
        ImageView appointment_img_view;


        public AppointmentViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            appointment_date_view = (TextView) view.findViewById(R.id.appointment_date);
            appointment_id_view = (TextView) view.findViewById(R.id.appointment_id2);
            appointment_img_view = (ImageView) view.findViewById(R.id.appointment_image);
            appointment_start_view = (TextView) view.findViewById(R.id.appointment_start);
            appointment_end_view = (TextView) view.findViewById(R.id.appointment_end);
            appointment_maxPlayers_view = (TextView) view.findViewById(R.id.appointment_max_players);
            appointment_password_view = (TextView) view.findViewById(R.id.appointment_password);
            appointment_delete_view = (Button) view.findViewById(R.id.appointment_delete);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
