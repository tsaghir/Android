package com.foi.air1603.sport_manager.presenter;

import android.util.Patterns;

import com.foi.air1603.sport_manager.view.fragments.LoginFragment;
import com.foi.air1603.webservice.AirWebServiceResponse;
import com.foi.air1603.sport_manager.BaseActivity;
import com.foi.air1603.sport_manager.entities.User;
import com.foi.air1603.sport_manager.model.UserInteractor;
import com.foi.air1603.sport_manager.model.UserInteractorImpl;
import com.foi.air1603.sport_manager.view.RegisterView;

import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.AddressR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.EmailR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.LastNameR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.NameR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.PasswordR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.PasswordR1;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.PhoneNumberR;
import static com.foi.air1603.sport_manager.helper.enums.RegisterViewEnums.UsernameR;

/**
 * Created by Robert on 11-Nov-16.
 */

public class RegisterPresenterImpl implements RegisterPresenter, PresenterHandler {

    public static User faceBookUser = null;
    private final RegisterView view;
    boolean valid = false;
    boolean emailFlag, usernameFlag, passwordFlag, password1Flag, nameFlag, lastNameFlag, addressFlag, phoneNumberFlag = true;
    //UserModel userModel;
    UserInteractor userInteractor;

    /**
     * Setter
     */
    public RegisterPresenterImpl(RegisterView registerView) {
        this.view = registerView;
        this.userInteractor = new UserInteractorImpl(this);
    }

    /**
     * Validates entered data in the Registration window and depending on the input
     * sets the associated flags to the value of true or false
     */
    @Override
    public void validateUserRegister() {
        emailFlag = usernameFlag = passwordFlag = password1Flag = nameFlag = lastNameFlag = addressFlag = phoneNumberFlag = true;


        if (view.getEmailFromEditText().isEmpty()) {
            view.displayError(EmailR, "Polje je obavezno");
            emailFlag = false;

        } else {
            valid = isValidEmailAddress(view.getEmailFromEditText());

            if (valid) {
                view.removeError(EmailR);
                emailFlag = true;
            } else {
                view.displayError(EmailR, "Email nije validan");
                emailFlag = false;
            }
        }

        if (view.getUsernameFromEditText().isEmpty()) {
            view.displayError(UsernameR, "Polje je obavezno");
            usernameFlag = false;
        } else {
            view.removeError(UsernameR);
            usernameFlag = true;
        }

        if (view.getPasswordFromEditText().isEmpty()) {
            view.displayError(PasswordR, "Polje je obavezno");
            passwordFlag = false;
        } else {
            view.removeError(PasswordR);
            passwordFlag = true;
        }

        if (view.getPassword1FromEditText().isEmpty()) {
            view.displayError(PasswordR1, "Polje je obavezno");
            password1Flag = false;
        } else {
            if (view.getPasswordFromEditText().equals(view.getPassword1FromEditText())) {
                password1Flag = true;
                view.removeError(PasswordR1);
            } else {
                password1Flag = false;
                view.displayError(PasswordR1, "Lozinke se ne podudaraju");
            }
        }

        if (view.getNameFromEditText().isEmpty()) {
            view.displayError(NameR, "Polje je obavezno");
            nameFlag = false;
        } else {
            nameFlag = true;
            view.removeError(NameR);
        }

        if (view.getLastNameFromEditText().isEmpty()) {
            view.displayError(LastNameR, "Polje je obavezno");
            lastNameFlag = false;
        } else {
            view.removeError(LastNameR);
            lastNameFlag = true;
        }

        if (view.getAddressFromEditText().isEmpty()) {
            view.displayError(AddressR, "Polje je obavezno");
            addressFlag = false;
        } else {
            view.removeError(AddressR);
            addressFlag = true;
        }

        if (view.getPhoneNumberFromEditText().isEmpty()) {
            view.displayError(PhoneNumberR, "Polje je obavezno");
            phoneNumberFlag = false;
        } else{
            view.removeError(PhoneNumberR);
            phoneNumberFlag = true;
        }

        if(emailFlag && usernameFlag && passwordFlag && password1Flag && nameFlag && lastNameFlag && addressFlag && phoneNumberFlag){
            System.out.println("----------------->2. RegisterPresenterImpl:validateUserRegister");
            if(LoginFragment.facebookLogin){
                userInteractor.setUserObject(createNewFaceUserObject());
            }else {
                userInteractor.setUserObject(createNewUserObject());
            }

        }

    }

    /**
     * Checks the entered E-mail address with a regex pattern
     * and returns the true or false value depending on the output
     */
    private boolean isValidEmailAddress(String emailFromEditText) {
        java.util.regex.Pattern p = Patterns.EMAIL_ADDRESS;
        java.util.regex.Matcher m = p.matcher(emailFromEditText);
        return m.matches();
    }

    /**
     * Creates a new User object if the data entered in the Registration window
     * matches the given set of rules;
     */
    private User createNewUserObject() {
        User user = new User();
        user.username = view.getUsernameFromEditText();
        user.address = view.getAddressFromEditText();
        user.email = view.getEmailFromEditText();
        user.firstName = view.getNameFromEditText();
        user.lastName = view.getLastNameFromEditText();
        user.phone = view.getPhoneNumberFromEditText();
        user.password = BaseActivity.get_SHA_512_SecurePassword(view.getPasswordFromEditText(), "");

        return user;
    }

    private User createNewFaceUserObject(){
        if(LoginFragment.facebookUser != null){
            User user = new User();
            user.username = view.getUsernameFromEditText();
            user.address = view.getAddressFromEditText();
            user.email = view.getEmailFromEditText();
            user.firstName = view.getNameFromEditText();
            user.lastName = view.getLastNameFromEditText();
            user.phone = view.getPhoneNumberFromEditText();
            user.password = BaseActivity.get_SHA_512_SecurePassword(view.getPasswordFromEditText(), "");
            user.facebook_id = LoginFragment.facebookUser.facebook_id;
            user.img = LoginFragment.facebookUser.img;
            user.type = LoginFragment.facebookUser.type;
            faceBookUser = user;
            return user;
        }
       return null;
    }

    /**
     * Depending on the success of the update operation on the database,
     * it returns a status code
     */
    @Override
    public void getResponseData(Object result) {
        System.out.println("----------------->7. RegisterPresenterImpl:getResponseData");

        AirWebServiceResponse test = (AirWebServiceResponse) result;

        view.returnResponseCode(test.getStatusCode(), test.getMessage());
    }
}



