package com.foi.air1603.nfc_verification_module;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Karlo on 20.1.2017..
 */

public class NfcVerificationCaller {

    private static NfcVerificationCaller instance;
    NfcVerificationHandler mNfcVerificationHandler;

    private NfcVerificationCaller() {
    }

    public static NfcVerificationCaller getInstance() {
        if (instance == null) {
            instance = new NfcVerificationCaller();
        }
        return instance;
    }

    public NfcVerificationCaller Init(NfcVerificationHandler nfcVerificationHandler) {
        mNfcVerificationHandler = nfcVerificationHandler;

        instance = this;
        return this;
    }

    public void startActivity(Activity activity, String challengeText) {

        System.out.println("----------------->5. NfcVerificationCaller:startActivity");

        Intent intent = new Intent();
        intent.putExtra("pass", challengeText);
        intent.setClass(activity, NfcMainActivity.class);
        activity.startActivity(intent);
    }
}
