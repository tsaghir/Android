package com.foi.air1603.sport_manager.view;

import android.net.Uri;

import com.foi.air1603.sport_manager.entities.User;

/**
 * Created by Karlo on 25.12.2016..
 */

public interface ProfileView {
    void getImageForImageView(String imageUrl, Uri imageUri);
    User getProfileUser();
}
