package com.ytosko.bloodhub;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class BloodHub extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
