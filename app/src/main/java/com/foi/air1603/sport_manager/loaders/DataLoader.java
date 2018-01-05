package com.foi.air1603.sport_manager.loaders;

import android.net.Uri;

import java.lang.reflect.Type;

/**
 * Apstrakna klasa koju trebaju nasljediti sve klase koje dohvaćaju podatke
 * Created by Karlo on 9.11.2016..
 */
public abstract class DataLoader {

    protected DataLoadedListener mDataLoadedListener;

    /**
     * Zahtjeva implementaciju funkcije za učitavanje podataka od svih klasa koje je nasljeđuju
     * @param dataLoadedListener Listner tipa DataLoadedListener na kojeg će se slati nazad podaci
     * @param method Metoda koja se poziva za dohvaćanje podataka
     *
     * @param entityType Tip objekta koji se očekuje kao rezultat
     * @param data Podaci koji se šalju na spremanje
     */
    public void loadData(DataLoadedListener dataLoadedListener, String method, String tableName, String searchBy, String value, Type entityType, Object data) {
        this.mDataLoadedListener = dataLoadedListener;
    }

    public void uploadFile(DataLoadedListener dataLoadedListener, String tableName, String fileUri, Integer user_id) {
        this.mDataLoadedListener = dataLoadedListener;
    }
}
