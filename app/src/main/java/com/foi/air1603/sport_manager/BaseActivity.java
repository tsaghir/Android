package com.foi.air1603.sport_manager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.facebook.CallbackManager;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.loaders.DataLoadedListener;
import com.foi.air1603.sport_manager.loaders.DataLoader;
import com.foi.air1603.sport_manager.loaders.WsDataLoader;
import com.foi.air1603.sport_manager.view.fragments.LoginFragment;
import com.foi.air1603.webservice.AirWebServiceResponse;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by Karlo on 3.12.2016..
 */

public class BaseActivity extends AppCompatActivity implements DataLoadedListener {

    static public BaseActivity activity;
    private static ProgressDialog progressDialog;
    private CallbackManager callbackManager;

    public static void showProgressDialog(String message) {
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    static public void unlinkDevice() {
        if (activity != null) {
            DataLoader dataLoader = new WsDataLoader();
            String android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            dataLoader.loadData(activity, "updateToken", android_id, "", "", User.class, null);
            MainActivity.tokenNeedsUpdating = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginFragment.logOutOfFacebook();

        SharedPreferences prefs = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String language = prefs.getString("Language", "");

        //defaultni jezik
        if (language.isEmpty()) {
            language = "hr";
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        callbackManager = CallbackManager.Factory.create();
        progressDialog = new ProgressDialog(this);
        activity = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        LoginFragment login = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, login, "HELLO");
        fragmentTransaction.commit();

        unlinkDevice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDataLoaded(AirWebServiceResponse result) {
        System.out.println("MyFirebaseInstanceIDService:onDataLoaded");
        System.out.println(result);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LoginFragment.facebookLogin) {
            LoginFragment.logOutOfFacebook();
            getFragmentManager().popBackStack();
        }
    }
}