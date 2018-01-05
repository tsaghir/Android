package com.foi.air1603.sport_manager.verifications;

/**
 * Created by Korisnik on 12-Jan-17.
 * Returns 0, 1, -1 or appointment ID
 */
public interface VerificationListener {
    void onVerificationResult(Integer result);
}
