package com.foi.air1603.sport_manager.verifications;

import android.app.Activity;

import com.foi.air1603.sport_manager.entities.Reservation;
import com.foi.air1603.sport_manager.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Karlo on 28.1.2017..
 */

public class VerificationLoader {
    private static List<String> modules;
    private Verification verification;

    public static CharSequence[] getEnabledModules(User user) {
        modules = new ArrayList<>();

        if (user.nfcModule == 1) {
            modules.add("NFC");
        }
        if (user.passwordModule == 1) {
            modules.add("Password");
        }
        return modules.toArray(new CharSequence[modules.size()]);
    }

    public void startVerification(VerificationListener verificationListener, Activity activity, Reservation reservation, int verificationMethod) {
        if (modules == null) {
            return;
        }

        String module = modules.get(verificationMethod);
        if (Objects.equals(module, "NFC")) {
            verification = new NfcVerification();
        } else if (Objects.equals(module, "Password")) {
            verification = new PasswordVerification();
        }
        verification.VerifyApp(verificationListener, activity, reservation);
    }

    public void initializeNfc(VerificationListener verificationListener) {
        verification = new NfcVerification();
        verification.VerifyApp(verificationListener, null, null);
    }
}
