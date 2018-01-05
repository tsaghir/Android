package com.foi.air1603.sport_manager.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.presenter.ProfilePresenter;
import com.foi.air1603.sport_manager.presenter.ProfilePresenterImpl;
import com.foi.air1603.sport_manager.view.ProfileView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


/**
 * Created by marko on 21.12.2016..
 */

public class ProfileFragment extends Fragment implements ProfileView {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 35;
    public static User user;
    private MainActivity activity;
    private Button btnChangeProfilePicture;
    private ProfilePresenter presenter;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleProfileActivity));
        activity = (MainActivity) getActivity();
        user = activity.getIntent().getExtras().getParcelable("User");


        View v = inflater.inflate(R.layout.fragment_profile, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ProfilePresenterImpl(this);

        btnChangeProfilePicture = (Button) getView().findViewById(R.id.btnChangePicture);

        btnChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // Request permissions if not already set
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    openImagePicker();
                }
            }
        });

        getImageForImageView(null, null);
        getUserDataForTextView();
    }

    public void getImageForImageView(String imageUrl, Uri imageUri) {

        ImageView imageView = (ImageView) getView().findViewById(R.id.profileImage);

        if (imageUrl != null) {
            imageView.setImageURI(imageUri);

            user.img = imageUrl;
            activity.getIntent().putExtra("User", user);

            activity.updateHeaderView();

            pref = this.getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            String json = new Gson().toJson(user);
            editor.putString("User", json);
            editor.commit();
            return;
        }

        if (user.img != null && !user.img.isEmpty()) {
            Picasso.with(activity).load(user.img).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.profile_stock);
        }
    }

    @Override
    public User getProfileUser() {
        return user;
    }

    private void getUserDataForTextView() {
        TextView nameProfile = (TextView) getView().findViewById(R.id.profileName);
        TextView lastNameProfile = (TextView) getView().findViewById(R.id.profileLastName);
        TextView emailProfile = (TextView) getView().findViewById(R.id.profileEmail);
        TextView addressProfile = (TextView) getView().findViewById(R.id.profileAddress);
        TextView phoneProfile = (TextView) getView().findViewById(R.id.profilePhone);

        nameProfile.setText(user.firstName);
        lastNameProfile.setText(user.lastName);
        emailProfile.setText(user.email);
        addressProfile.setText(user.address);
        phoneProfile.setText(user.phone);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker();
                } else {
                    System.out.println("Couldn't get read files permission!");
                }
                return;
            }
        }
    }

    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            System.out.println("----------------->1. ProfileFragment:onActivityResult");
            presenter.changeProfilePicture(data, user.id, activity);
        }
    }
}
