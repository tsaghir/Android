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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.adapters.FriendsRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.entities.Team;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.presenter.InviteFriendsPresenter;
import com.foi.air1603.sport_manager.presenter.InviteFriendsPresenterImpl;
import com.foi.air1603.sport_manager.view.InviteFriendsView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Karlo on 30.12.2016..
 */

public class InviteFriendsFragment extends Fragment implements InviteFriendsView {
    Reservation reservation;
    private List<User> friendsForInvite = new ArrayList<>();
    private InviteFriendsPresenter presenter;
    private Button btnAdd;
    private TextView tvTeamName;
    private AutoCompleteTextView textView;
    private View view;
    private String[] USEREMAILS;
    private RecyclerView recyclerView;
    private FriendsRecycleAdapter friendsRecycleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleInviteFriendsActivity));
        return inflater.inflate(R.layout.fragment_invite_friends, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey("userReservation")) {
                String place_serialized = bundle.getString("userReservation");
                this.reservation = new Gson().fromJson(place_serialized, Reservation.class);
            }
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_friend_invites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.view = view;

        tvTeamName = (TextView) view.findViewById(R.id.tvTeamName);

        btnAdd = (Button) view.findViewById(R.id.btnInviteFriends);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean emailInDatabase, userAlreadyInvited, userAddsSelf;
                userAlreadyInvited = userAddsSelf = emailInDatabase = false;
                String inputEmail = textView.getText().toString();

                if (Objects.equals(inputEmail, MainActivity.user.email)) {
                    userAddsSelf = true;
                }
                for (String email : USEREMAILS) {
                    if (Objects.equals(email, inputEmail)) {
                        emailInDatabase = true;
                    }
                }
                for (User user : friendsForInvite) {
                    if (Objects.equals(user.email, inputEmail)) {
                        userAlreadyInvited = true;
                    }
                }

                if (userAddsSelf) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.toastInviteFriendsAuto), Toast.LENGTH_SHORT).show();
                } else if (userAlreadyInvited) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.toastInviteFriendsAdded), Toast.LENGTH_SHORT).show();
                } else if (!emailInDatabase) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.toastInviteFriendsNotFound), Toast.LENGTH_SHORT).show();
                } else {
                    presenter.loadUserByEmail(textView.getText().toString());
                }
            }
        });

        Button btnBack = (Button) view.findViewById(R.id.btnSubmit);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                reservation.created = sqlDate.toString();

                Team team = new Team();
                friendsForInvite.add(MainActivity.user);
                team.users = friendsForInvite;
                team.created = sqlDate.toString();
                team.userId = MainActivity.user.id;
                if(tvTeamName.getText().toString().isEmpty()){
                    team.name = "Team_"+reservation.sportId+"_"+reservation.appointmentId;
                } else {
                    team.name = tvTeamName.getText().toString();
                }
                reservation.team = team;

                //System.out.println(1);

                MainActivity.showProgressDialog(getResources().getString(R.string.progressReservationCreation));
                presenter.reservateAppointment(reservation);
            }
        });

        presenter = new InviteFriendsPresenterImpl(this);
        presenter.loadAllUserEmails();

        // friendsForInvite.add(MainActivity.user);
        friendsRecycleAdapter = new FriendsRecycleAdapter(friendsForInvite, this);
        recyclerView.setAdapter(friendsRecycleAdapter);
    }

    @Override
    public void successfulReservation() {
        MainActivity.dismissProgressDialog();
        Toast.makeText(getActivity(),
                getResources().getString(R.string.toastReservationComplete), Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void initializeAutoComplete(Map<Integer, String> userEmails) {
        USEREMAILS = userEmails.values().toArray(new String[0]);

        //Inicijalizacija autocompleta
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_dropdown_item_1line, USEREMAILS);
        textView = (AutoCompleteTextView)
                view.findViewById(R.id.actvInviteFriends);
        textView.setAdapter(adapter);
    }

    @Override
    public void addUserToInviteList(User user) {
        friendsForInvite.add(user);
        textView.setText("");
        friendsRecycleAdapter.notifyDataSetChanged();
    }
}
