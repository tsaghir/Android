package com.foi.air1603.sport_manager.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.air1603.sport_manager.MainActivity;
import com.foi.air1603.sport_manager.R;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums;
import com.foi.air1603.sport_manager.presenter.RegisterPresenter;
import com.foi.air1603.sport_manager.presenter.RegisterPresenterImpl;
import com.foi.air1603.sport_manager.view.RegisterView;

/**
 * Created by Karlo on 3.12.2016..
 */

public class RegisterFragment extends android.app.Fragment implements RegisterView {

    private RegisterPresenter presenter;
    private Button btnRegister;
    private User facebookUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.titleRegisterActivity));
        View v = inflater.inflate(R.layout.fragment_register, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instance the presenter class
        presenter = new RegisterPresenterImpl(this);
        btnRegister = (Button) getActivity().findViewById(R.id.bRegistracija);
        if(LoginFragment.facebookLogin){
            facebookUser = LoginFragment.facebookUser;
        }


        if(facebookUser != null){
            setFacebookUserTextView();
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                System.out.println("----------------->1. RegisterFragment:onClickListener");
                presenter.validateUserRegister();
            }

        });

    }
    private void setFacebookUserTextView(){

        final TextInputEditText firstName = (TextInputEditText) getActivity().findViewById(R.id.etName);
        final TextInputEditText lastName = (TextInputEditText) getActivity().findViewById(R.id.etLastName);
        final TextInputEditText email = (TextInputEditText) getActivity().findViewById(R.id.etMail);

        firstName.setText(facebookUser.firstName);
        lastName.setText(facebookUser.lastName);
        email.setText(facebookUser.email);

    }

    public void returnResponseCode(int statusCode, String message) {
        if (statusCode == 200) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastRegistrationSuccessful), Toast.LENGTH_LONG).show();

            if(LoginFragment.facebookLogin){
                Intent intent = new Intent(this.getActivity(), MainActivity.class).putExtra("User", RegisterPresenterImpl.faceBookUser);
                startActivity(intent);
            }else {
                getFragmentManager().popBackStack();
            }

        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.toastRegistrationFailed) + message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    /**
     * Returns the entered string from the Email field
     */
    public String getEmailFromEditText() {
        EditText emailInput = (EditText) getActivity().findViewById(R.id.etMail);
        return emailInput.getText().toString();
    }

    /**
     * Returns the entered string from the Username field
     */
    public String getUsernameFromEditText() {
        EditText usernameInput = (EditText) getActivity().findViewById(R.id.etUsernameR);
        return usernameInput.getText().toString();
    }

    /**
     * Returns the entered string from the Password field
     */
    public String getPasswordFromEditText() {
        EditText passwordInput = (EditText) getActivity().findViewById(R.id.etPasswordR);
        return passwordInput.getText().toString();
    }

    /**
     * Returns the entered string from the Password matching field
     */
    public String getPassword1FromEditText() {
        EditText password1Input = (EditText) getActivity().findViewById(R.id.etPasswordR1);
        return password1Input.getText().toString();
    }

    /**
     * Returns the entered string from the Name field
     */
    public String getNameFromEditText() {
        EditText nameInput = (EditText) getActivity().findViewById(R.id.etName);
        return nameInput.getText().toString();
    }

    /**
     * Returns the entered string from the Last Name field
     */
    public String getLastNameFromEditText() {
        EditText lastNameInput = (EditText) getActivity().findViewById(R.id.etLastName);
        return lastNameInput.getText().toString();
    }

    /**
     * Returns the entered string from the Address field
     */
    public String getAddressFromEditText() {
        EditText addressInput = (EditText) getActivity().findViewById(R.id.etAddress);
        return addressInput.getText().toString();
    }

    /**
     * Returns the entered string from the Phone Number field
     */
    public String getPhoneNumberFromEditText() {
        EditText phoneNumberInput = (EditText) getActivity().findViewById(R.id.etPhoneNumber);
        return phoneNumberInput.getText().toString();
    }

    /**
     * Displays error messages underneath the fields in the Registration window
     * which were caused by a set of incorrectly entered characters or by not
     * entering any of them
     *
     * @param textView
     */
    @Override
    public void displayError(RegisterViewEnums textView, String message) {
        final TextInputLayout mailRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiMailR);
        final TextInputLayout usernameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiUsernameR);
        final TextInputLayout passwordRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPasswordR);
        final TextInputLayout passwordR1Wrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPasswordR1);
        final TextInputLayout nameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiImeR);
        final TextInputLayout lastNameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPrezimeR);
        final TextInputLayout addressRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAdresaR);
        final TextInputLayout phoneNumberRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiBrojTelefonaR);

        if (message.equals("Unesite vrijednost")){
            message = getResources().getString(R.string.errorFieldNecessary);
        } else if(message.equals("Email nije validan")){
            message = getResources().getString(R.string.errorEmailInvalid);
        } else if(message.equals("Lozinke se ne podudaraju")) {
            message = getResources().getString(R.string.errorPasswordMatching);
        }

        if (textView == RegisterViewEnums.UsernameR) {
            usernameRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.PasswordR) {
            passwordRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.PasswordR1) {
            passwordR1Wrapper.setError(message);
        } else if (textView == RegisterViewEnums.EmailR) {
            mailRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.NameR) {
            nameRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.LastNameR) {
            lastNameRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.AddressR) {
            addressRWrapper.setError(message);
        } else if (textView == RegisterViewEnums.PhoneNumberR) {
            phoneNumberRWrapper.setError(message);
        }

    }


    /**
     * Removes error messages underneath the fields in the Registration window
     * which were caused by a set of incorrectly entered characters or by not
     * entering any of them
     *
     * @param textView
     */
    @Override
    public void removeError(RegisterViewEnums textView) {
        final TextInputLayout mailRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiMailR);
        final TextInputLayout usernameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiUsernameR);
        final TextInputLayout passwordRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPasswordR);
        final TextInputLayout passwordR1Wrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPasswordR1);
        final TextInputLayout nameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiImeR);
        final TextInputLayout lastNameRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiPrezimeR);
        final TextInputLayout addressRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiAdresaR);
        final TextInputLayout phoneNumberRWrapper = (TextInputLayout) getActivity().findViewById(R.id.txiBrojTelefonaR);

        mailRWrapper.setErrorEnabled(false);
        usernameRWrapper.setErrorEnabled(false);
        passwordRWrapper.setErrorEnabled(false);
        passwordR1Wrapper.setErrorEnabled(false);
        nameRWrapper.setErrorEnabled(false);
        lastNameRWrapper.setErrorEnabled(false);
        addressRWrapper.setErrorEnabled(false);
        phoneNumberRWrapper.setErrorEnabled(false);
    }
}
