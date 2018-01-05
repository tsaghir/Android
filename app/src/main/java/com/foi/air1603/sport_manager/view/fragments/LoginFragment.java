package com.foi.air1603.sport_manager.view.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foi.air1603.sport_manager.BaseActivity;
import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.helper.enums.LoginViewEnums;
import com.foi.air1603.sport_manager.presenter.LoginPresenter;
import com.foi.air1603.sport_manager.presenter.LoginPresenterImpl;
import com.foi.air1603.sport_manager.view.LoginView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends android.app.Fragment implements LoginView {

    private LoginPresenter presenter;
    private Button btnLogin;
    private TextView txtViewRegistration;
    private EditText usernameInput;
    private EditText passwordInput;
    private User user;
    private SharedPreferences pref;
    private Profile profile;
    private BaseActivity activity;
    private FragmentManager fragmentManager;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String fEmail = null;

    public static void logOutOfFacebook() {
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
            facebookLogin = false;
        }
    }

    public static Boolean facebookLogin = false;
    public static User facebookUser = null;
    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            profile = Profile.getCurrentProfile();
            facebookLogin = true;
            presenter.checkFacebookUserInDb(profile.getId());

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            // Application code

                            try {
                                fEmail = object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //String birthday = object.getString("birthday"); // 01/31/1980 format
                            System.out.println("!!!!!!!!!!!FACEBOOK " + fEmail);
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Log.e("Facebook Error: ", error.getMessage());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.app_name));
        View v = inflater.inflate(R.layout.fragment_login, null);
        activity = (BaseActivity) getActivity();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = this.getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);


        if (pref.contains("User")) {
            Intent intent = new Intent(this.getActivity(), MainActivity.class).putExtra("User", retrieveLoginSession());
            startActivity(intent);
        } else {
            presenter = new LoginPresenterImpl(this);
            setValuesToTextViews();

            txtViewRegistration = (TextView) getView().findViewById(R.id.twRegistracija);   //tw is short for TextView

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.checkInputData();
                }
            });

            txtViewRegistration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new RegisterFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }
        loginButton = (LoginButton) view.findViewById(R.id.face_login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void createLoginSession(User user) {
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        user.nfcModule = 0;
        user.passwordModule = 0;
        user.hide_notifications = 0;
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.commit();
    }

    private User retrieveLoginSession() {
        Gson gson = new Gson();
        String json = pref.getString("User", "");
        user = gson.fromJson(json, User.class);
        return user;
    }

    @Override
    public String getUsernameFromEditText() {
        return usernameInput.getText().toString();
    }

    @Override
    public String getPasswordFromEditText() {
        return passwordInput.getText().toString();
    }

    @Override
    public Boolean userLoggedInFacebook() {
        return facebookLogin;
    }

    @Override
    public void displayError(LoginViewEnums textView, String message) {
        final TextInputLayout usernameWrapper = (TextInputLayout) getView().findViewById(R.id.txiUsernameL);
        final TextInputLayout passwordWrapper = (TextInputLayout) getView().findViewById(R.id.txiPasswordL);

        if (message.equals("Unesite vrijednost")) {
            message = getResources().getString(R.string.errorFieldNecessary);
        } else if (message.equals("Korisničko ime ne postoji")) {
            message = getResources().getString(R.string.errorUsernameNotFound);
        } else if (message.equals("Unijeli ste krivu lozinku")) {
            message = getResources().getString(R.string.errorWrongPassword);
        }

        if (textView == LoginViewEnums.Username) {
            usernameWrapper.setError(message);
        } else if (textView == LoginViewEnums.Password) {
            passwordWrapper.setError(message);
        }
    }

    @Override
    public void dataLoadingError(String message) {
        buildAlertDialogForWebServiceError(message);
    }

    @Override
    public void removeError(LoginViewEnums textView) {
        final TextInputLayout usernameWrapper = (TextInputLayout) getView().findViewById(R.id.txiUsernameL);
        final TextInputLayout passwordWrapper = (TextInputLayout) getView().findViewById(R.id.txiPasswordL);

        usernameWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
    }

    @Override
    public void loginSuccessful(User userObject) {
        createLoginSession(userObject);
        Intent intent = new Intent(getActivity(), MainActivity.class).putExtra("User", userObject);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void createNewFaceUserObject() {
        if (profile != null) {
            User faceUser = new User();
            faceUser.firstName = profile.getFirstName();
            faceUser.lastName = profile.getLastName();
            faceUser.img = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";
            faceUser.type = 0;
            faceUser.facebook_id = profile.getId();
            faceUser.email = fEmail;
            facebookUser = faceUser;
            initRegisterProfileFragment();
        }
    }

    private void initRegisterProfileFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, registerFragment).addToBackStack("LoginFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void buildAlertDialogMessage() {
        String message = "Primjetili smo da nedostajete u bazi podataka. Molim vas nadopunite podatke?";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Dopunite podatke!");
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterFragment registerFragment = new RegisterFragment();
                        activity.getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, registerFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setValuesToTextViews() {
        btnLogin = (Button) getView().findViewById(R.id.bPrijava);
        usernameInput = (EditText) getView().findViewById(R.id.etUsername);
        passwordInput = (EditText) getView().findViewById(R.id.etPassword);
    }

    private void buildAlertDialogForWebServiceError(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getResources().getString(R.string.alertLogin));
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
