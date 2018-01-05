package com.foi.air1603.sport_manager.view.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.User;

/**
 * Created by Korisnik on 27-Jan-17.
 */

public class AboutFragment extends Fragment {

    private Context context;
    public User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleAboutActivity));

        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_about, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

        user = activity.getIntent().getExtras().getParcelable("User");
        TextView txtViewEmail = (TextView) getView().findViewById(R.id.about_email);


        txtViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "rpizek.freeride@yahoo.com","karlo.simunovic@gmail.com", "rpkaccvjezbe@gmail.com", "marko.flajsek@gmail.com"  });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sport-Manager");

                startActivity(Intent.createChooser(intent, ""));


            }
        });


    }
}
