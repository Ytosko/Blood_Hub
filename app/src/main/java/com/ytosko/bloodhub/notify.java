package com.ytosko.bloodhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class notify extends AppCompatActivity {

    Vibrator x;
    ListView l;
    DatabaseReference iref,ref;
    FirebaseListAdapter<NotiHelper> ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notify);

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                notify.class));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();

        l = findViewById (R.id.notilist);
        iref = FirebaseDatabase.getInstance().getReference("notification");
        iref.keepSynced(true);;
        //ad = new ArrayAdapter( this, android.R.layout.simple_expandable_list_item_1, al);
        //l.setAdapter( ad );
        ref = iref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.keepSynced(true);
        Query q = ref;
        FirebaseListOptions<NotiHelper> al = new FirebaseListOptions.Builder<NotiHelper>()
                .setLayout(R.layout.castnoti)
                .setQuery(q, NotiHelper.class)
                .build();

        ad = new FirebaseListAdapter<NotiHelper> (al) {
            @Override
            protected void populateView(@NonNull  View v,@NonNull NotiHelper model, int position) {
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManager.setReverseLayout(true);

                TextView name = v.findViewById(R.id.notific);
                TextView time = v.findViewById(R.id.time1);

                NotiHelper std = model;

                if(std.getNotification() != null && std.getTime() != null){
                    name.setText (std.getNotification());
                    time.setText (std.getTime());
                }
            }
        };
        ad.startListening();
        l.setAdapter(ad);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ad.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ad.stopListening();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(notify.this, MainActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
