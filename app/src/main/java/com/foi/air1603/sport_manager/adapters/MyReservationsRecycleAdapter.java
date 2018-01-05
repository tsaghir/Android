package com.foi.air1603.sport_manager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.view.fragments.MyReservationsFragment;

import java.util.List;

/**
 * Created by Generalko on 28-Dec-16.
 */

public class MyReservationsRecycleAdapter extends ExpandableRecyclerAdapter<MyReservationsExpandableItem, Reservation, MyReservationsParentViewHolder, MyReservationsChildViewHolder> {

    private LayoutInflater mInflator;
    private Context context;
    MyReservationsFragment con;

    public MyReservationsRecycleAdapter(Context context, @NonNull List<MyReservationsExpandableItem> parentList ,MyReservationsFragment myReservationsFragment) {
        super(parentList);
        this.context = context;
        mInflator = LayoutInflater.from(context);
        this.con = myReservationsFragment;
    }

    @NonNull
    @Override
    public MyReservationsParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View mReservationsParentView = mInflator.inflate(R.layout.fragment_my_reservations_parent, parentViewGroup, false);
        return new MyReservationsParentViewHolder(mReservationsParentView);
    }

    @NonNull
    @Override
    public MyReservationsChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View mReservationsChildView = mInflator.inflate(R.layout.fragment_my_reservations_child, childViewGroup, false);
        return new MyReservationsChildViewHolder(mReservationsChildView,this,con);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MyReservationsParentViewHolder parentViewHolder, int parentPosition, @NonNull MyReservationsExpandableItem parentListItem) {
        MyReservationsExpandableItem expandableItem = (MyReservationsExpandableItem) parentListItem;
        //todo: pass the parameter to bind
        parentViewHolder.bind(expandableItem);
    }

    @Override
    public void onBindChildViewHolder(@NonNull MyReservationsChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Reservation child) {
        childViewHolder.bind(child);
    }

}
