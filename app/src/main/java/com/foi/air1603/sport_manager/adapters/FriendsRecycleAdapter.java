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
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.view.fragments.InviteFriendsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Korisnik on 04-Dec-16.
 */

public class FriendsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<User> users;
    InviteFriendsFragment context;
    Context cont;


    public FriendsRecycleAdapter(List<User> users, InviteFriendsFragment context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_invite_friends_list_item, parent, false);
        FriendsViewHolder item = new FriendsViewHolder(view);
        cont = parent.getContext();
        return item;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final User user = users.get(position);

        ((FriendsViewHolder) holder).friendsName.setText(user.firstName + " " + user.lastName);
        ((FriendsViewHolder) holder).friendsEmail.setText(user.email);

        if (user.img != null && !user.img.isEmpty()) {
            Uri uri = Uri.parse(user.img);
            Picasso.with(cont).load(uri).into(((FriendsViewHolder) holder).friendsImage);
        } else {
            ((FriendsViewHolder) holder).friendsImage.setImageResource(R.drawable.profile_stock);
        }
        ((FriendsViewHolder) holder).friendRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView friendsName;
        TextView friendsEmail;
        ImageView friendsImage;
        Button friendRemove;


        public FriendsViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            friendsName = (TextView) view.findViewById(R.id.tvFriendsName);
            friendsEmail = (TextView) view.findViewById(R.id.tvFriendsEmail);
            friendsImage = (ImageView) view.findViewById(R.id.ivFriendsImg);
            friendRemove = (Button) view.findViewById(R.id.btnRemoveFriend);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            User user = users.get(position);
            // context.changeFragment(user);
        }
    }
}
