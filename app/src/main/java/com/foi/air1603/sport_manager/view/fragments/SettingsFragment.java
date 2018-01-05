package com.foi.air1603.sport_manager.view.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.view.SettingsView;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends android.app.Fragment implements SettingsView {
    public static Boolean spinnerInitialized = false;
    private User user;
    private int passModul;
    private int nfcModul;
    private int notification;
    private Switch switchPass;
    private Switch switchNfc;
    private Switch switchNotifications;
    private SharedPreferences pref;
    private Locale myLocale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleSettingsActivity));
        pref = this.getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        user = getActivity().getIntent().getExtras().getParcelable("User");

        assert user != null;
        this.passModul = user.passwordModule;
        this.nfcModul = user.nfcModule;
        this.notification = user.hide_notifications;

        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.switchPass = (Switch) getActivity().findViewById(R.id.switchPassword);
        this.switchNfc = (Switch) getActivity().findViewById(R.id.switchNFC);
        this.switchNotifications = (Switch) getActivity().findViewById(R.id.switchNotification);
        setSettings();

        switchNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value;
                if (user.nfcModule == 0) {
                    value = 1;
                } else {
                    value = 0;
                }
                updateSession("nfc", value);
                setSettings();
            }
        });

        switchPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value;
                if (user.passwordModule == 0) {
                    value = 1;
                } else {
                    value = 0;
                }
                updateSession("pass", value);
                setSettings();
            }
        });

        switchNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value;
                if (user.hide_notifications == 0) {
                    value = 1;
                } else {
                    value = 0;
                }
                updateSession("notification", value);
                setSettings();
            }
        });

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String currentLanguage = "";
        if (Objects.equals(currentLanguage, "hr")) {
            spinner.setSelection(0);
        } else {
            if(getActivity().getTitle().equals("Settings")){
                currentLanguage = "en";
                spinner.setSelection(1);
            }
            if(getActivity().getTitle().equals("Postavke")){
                currentLanguage = "hr";
                spinner.setSelection(0);
            }
        }

        if(currentLanguage.isEmpty()){
            currentLanguage = loadLocale();
        }

        final String finalCurrentLanguage = currentLanguage;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newLanguage = "";

                if (position == 1) {
                    newLanguage = "en";
                } else if (position == 0) {
                    newLanguage = "hr";
                }

                if (newLanguage != finalCurrentLanguage) {
                    saveLocale(newLanguage);
                    changeLanguage(newLanguage);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setSettings() {
        if (user.passwordModule == 0) {
            switchPass.setChecked(false);
        } else {
            switchPass.setChecked(true);
        }
        if (user.nfcModule == 0) {
            switchNfc.setChecked(false);
        } else {
            switchNfc.setChecked(true);
        }
        if (user.hide_notifications == 0) {
            switchNotifications.setChecked(false);
        } else {
            switchNotifications.setChecked(true);
        }
    }

    private void updateSession(String item, int value) {
        switch (item) {
            case "nfc":
                user.nfcModule = value;
                MainActivity.user.nfcModule = value;

                int option = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (value == 1) {
                    option = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
                }

                PackageManager pm = getActivity().getApplicationContext().getPackageManager();
                ComponentName componentName = new ComponentName("com.foi.air1603.sport_manager",
                        "com.foi.air1603.nfc_verification_module.NfcMainActivity");
                pm.setComponentEnabledSetting(componentName, option,
                        PackageManager.DONT_KILL_APP);

                break;
            case "pass":
                user.passwordModule = value;
                MainActivity.user.passwordModule = value;
                break;
            case "notification":
                user.hide_notifications = value;
                MainActivity.user.hide_notifications = value;
                break;
        }

        getActivity().getIntent().putExtra("User", user);
        SharedPreferences.Editor editor = pref.edit();

        String json = new Gson().toJson(user);
        editor.putString("User", json);
        editor.apply();
    }

    private User retrieveLoginSession() {
        Gson gson = new Gson();
        String json = pref.getString("User", "");
        user = gson.fromJson(json, User.class);
        return user;
    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    public String loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return prefs.getString(langPref, "");
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }
}

