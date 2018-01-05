package com.foi.air1603.sport_manager.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.adapters.MyReservationsExpandableItem;
import com.foi.air1603.sport_manager.adapters.MyReservationsRecycleAdapter;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.presenter.MyReservationsPresenter;
import com.foi.air1603.sport_manager.presenter.MyReservationsPresenterImpl;
import com.foi.air1603.sport_manager.verifications.VerificationListener;
import com.foi.air1603.sport_manager.verifications.VerificationLoader;
import com.foi.air1603.sport_manager.view.MyReservationsView;

import java.util.ArrayList;
import java.util.List;

public class MyReservationsFragment extends android.app.Fragment implements MyReservationsView, VerificationListener {

    private static final String TAG = "MyReservationsFragment";
    public Reservation reservation;
    protected RecyclerView mRecyclerView;
    MyReservationsPresenter myReservationsPresenter;
    MyReservationsRecycleAdapter adapter;
    VerificationLoader verificationLoader;
    private Activity activity;
    private SharedPreferences pref;
    private User user;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleMyReservationsActivity));
        MainActivity.showProgressDialog(getResources().getString(R.string.progressDataLoading));

        View rootView = inflater.inflate(R.layout.fragment_my_reservations, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        verificationLoader = new VerificationLoader();
        if (MainActivity.user.type > 0) {
            verificationLoader.initializeNfc(this);
        }

        activity = getActivity();
        myReservationsPresenter = MyReservationsPresenterImpl.getInstance().Init(this);
        myReservationsPresenter.getUserReservationsData();

        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshMyReservations);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyReservationsPresenterImpl.updateData = true;
                myReservationsPresenter.getUserReservationsData();
            }
        });
    }

    @Override
    public void loadRecycleViewData(List<Reservation> reservations) {
        List<MyReservationsExpandableItem> reservationsItems = new ArrayList<>();
        System.out.println("MyReservationsFragment:LoadRecyclerViewData");

        if (reservations == null) {
            MainActivity.dismissProgressDialog();
            Toast.makeText(activity,
                    getResources().getString(R.string.toastNoReservations), Toast.LENGTH_LONG).show();
            return;
        }

        int expandParentPosition = -1, reservationId = -1;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            reservationId = Integer.parseInt(bundle.get("reservation_id").toString());
        }

        for (Reservation res : reservations) {
            if (res.appointment == null) {
                continue;
            }

            MyReservationsExpandableItem tmp = new MyReservationsExpandableItem(res);
            reservationsItems.add(tmp);

            if (bundle != null && res.id == reservationId) {
                expandParentPosition = reservationsItems.size() - 1;
            }
        }
        if (mRecyclerView != null) {
            adapter = new MyReservationsRecycleAdapter(getActivity(), reservationsItems, this);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        MainActivity.dismissProgressDialog();

        if (expandParentPosition != -1) {
            adapter.expandParent(expandParentPosition);
            //System.out.println("EXPNDING PARENT " + expandParentPosition);
        }

        adapter.notifyDataSetChanged();
        if(swipeContainer != null){
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void verifyAppointment() {
        //  pref = this.getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //this.user = getActivity().getIntent().getExtras().getParcelable("User");

        CharSequence modules[] = VerificationLoader.getEnabledModules(MainActivity.user);
        if (modules.length == 0) {
            Toast.makeText(activity,
                    getResources().getString(R.string.toastNoModule), Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Verify by");
        builder.setItems(modules, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                runVerification(which);
            }
        });
        builder.show();
    }

    void runVerification(int verificationMethod) {
        verificationLoader.startVerification(this, getActivity(), reservation, verificationMethod);
    }

    @Override
    public void setObject(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public void deleteReservation(int id) {
        myReservationsPresenter.deleteReservationById(id);
    }

    @Override
    public void successfulDeletedReservation() {
        Toast.makeText(activity,
                getResources().getString(R.string.toastTermDeletionSuccessful), Toast.LENGTH_SHORT).show();
        MyReservationsPresenterImpl.updateData = true;
        myReservationsPresenter.getUserReservationsData();
    }

    @Override
    public void backFragment() {
        getFragmentManager().popBackStack();
        Toast.makeText(getActivity(), getResources().getString(R.string.toastNoReservation), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onVerificationResult(Integer result) {
        System.out.println("MyReservationsFragment:onVerificationResult ---- result is -- " + result);
        String toastMessage = "";

        if (result == 1) {
            toastMessage = getResources().getString(R.string.toastAppointmentConfirmation);
            myReservationsPresenter.updateReservation(reservation);
        } else if (result == 0) {
            toastMessage = getResources().getString(R.string.toastAppointmentCheck);
        } else if (result == -1) {
            toastMessage = getResources().getString(R.string.toastError);
        } else {
            Reservation mReservation = new Reservation();
            mReservation.id = result;
            mReservation.confirmed = 1;
            myReservationsPresenter.updateReservation(mReservation);
            toastMessage = "Uspješno ste verificirali dolazak na termin id " + result;
        }

        final String finalToastMessage = toastMessage;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, finalToastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
