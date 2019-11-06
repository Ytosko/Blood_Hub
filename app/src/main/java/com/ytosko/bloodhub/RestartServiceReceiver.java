package com.ytosko.bloodhub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RestartServiceReceiver extends BroadcastReceiver
{

    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive");

        SimpleDateFormat sdf121 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        String timelast = sdf121.format(new Date ());
        DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
        DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
        io.setValue (timelast);

    }

}