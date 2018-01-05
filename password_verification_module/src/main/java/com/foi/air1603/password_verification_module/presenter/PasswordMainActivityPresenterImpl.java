package com.foi.air1603.password_verification_module.presenter;

import com.foi.air1603.password_verification_module.view.PasswordView;

/**
 * Created by Korisnik on 12-Jan-17.
 */

public class PasswordMainActivityPresenterImpl implements PasswordMainActivityPresenter {
    private final PasswordView view;

    public PasswordMainActivityPresenterImpl(PasswordView passwordView) {
        this.view = passwordView;
    }

    @Override
    public Integer checkInputPass() {
        if (view.getPassFromApp().equals(view.getPassFromEditText())) {
            return 1;
        } else {
            return -1;
        }
    }
}
