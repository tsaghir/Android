package com.foi.air1603.password_verification_module;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Karlo on 13.1.2017..
 */

public class PasswordVerificationCaller {

    public PasswordVerificationHandler mPasswordVerificationHandler;

    private static PasswordVerificationCaller instance;

    public PasswordVerificationCaller() {
    }

    public static PasswordVerificationCaller getInstance() {
        if (instance == null) {
            instance = new PasswordVerificationCaller();
        }
        return instance;
    }

    public PasswordVerificationCaller Init(PasswordVerificationHandler passwordVerificationHandler) {
        mPasswordVerificationHandler = passwordVerificationHandler;

        instance = this;
        return this;
    }

    public void startActivity(Activity activity, String pass) {

        System.out.println("----------------->5. PasswordVerificationCaller:startActivity");

        Intent intent = new Intent();
        intent.putExtra("pass", pass);
        intent.setClass(activity, PasswordMainActivity.class);
        activity.startActivity(intent);

    }

}
