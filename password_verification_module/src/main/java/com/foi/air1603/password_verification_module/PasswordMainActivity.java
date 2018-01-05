package com.foi.air1603.password_verification_module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.foi.air1603.password_verification_module.presenter.PasswordMainActivityPresenter;
import com.foi.air1603.password_verification_module.presenter.PasswordMainActivityPresenterImpl;
import com.foi.air1603.password_verification_module.view.PasswordView;

/**
 * Created by Korisnik on 12-Jan-17.
 */

public class PasswordMainActivity extends AppCompatActivity implements PasswordView {

    public PasswordMainActivity activity;
    private Button btnPotvrdi;
    private EditText passInput;
    private PasswordMainActivityPresenter presenter;
    private String pass;
    private PasswordVerificationCaller passwordVerificationCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordVerificationCaller = PasswordVerificationCaller.getInstance();

        System.out.println("----------------->6. PasswordMainActivity:onCreate");

        pass = getIntent().getStringExtra("pass");
        setContentView(R.layout.activity_password_main);
        passInput = (EditText) this.findViewById(R.id.etPasswordV);
        btnPotvrdi = (Button) this.findViewById(R.id.btnConfirm);
        presenter = new PasswordMainActivityPresenterImpl(this);
        btnPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer flag = presenter.checkInputPass();
                passwordVerificationCaller.mPasswordVerificationHandler.onResultArrived(flag);
                finish();
            }
        });
    }

    @Override
    public String getPassFromEditText() {
        return passInput.getText().toString();
    }

    @Override
    public String getPassFromApp() {
        return pass;
    }
}
