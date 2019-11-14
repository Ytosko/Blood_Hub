package com.ytosko.bloodhub;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class chatl extends AppCompatActivity {
    Vibrator x;
    DatabaseReference iref,ref;
    ListView l;
    //ArrayList<ChatHelper1> al = new ArrayList<ChatHelper1> (  );
    //ArrayAdapter<ChatHelper1> ad;
    private ProgressDialog pDialog;
    FirebaseListAdapter<ChatHelper1> ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatl);

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                chatl.class));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();

        SimpleDateFormat sdf1211 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        String timelast = sdf1211.format(new Date ());
        DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
        DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
        io.setValue (timelast);

        l = findViewById(R.id.chatlist);
        pDialog = new ProgressDialog(chatl.this);
        pDialog.setMessage("Retrieving messages");

        iref = FirebaseDatabase.getInstance().getReference("Chat");
        iref.keepSynced(true);;
        //ad = new ArrayAdapter( this, android.R.layout.simple_expandable_list_item_1, al);
        //l.setAdapter( ad );
        ref = iref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.keepSynced(true);

        Query q = ref;
        FirebaseListOptions<ChatHelper1> al = new FirebaseListOptions.Builder<ChatHelper1>()
                .setLayout(R.layout.castarray)
                .setQuery(q, ChatHelper1.class)
                .build();
        SimpleDateFormat sdf121 = new SimpleDateFormat("dd/MM", Locale.getDefault());
        final String currentDateandTime21 = sdf121.format(new Date());

        ad = new FirebaseListAdapter<ChatHelper1>(al) {
            @Override
            protected void populateView(View v, ChatHelper1 model, int position) {
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManager.setReverseLayout(true);

                TextView name = v.findViewById(R.id.name);
                TextView time = v.findViewById(R.id.time);
                TextView msg = v.findViewById(R.id.msg);

                final ChatHelper1 std = model;
                String message = null, c1;
                c1 = std.getLastsender();
                if(c1.equals(std.getName()) && c1 != null){
                    message = std.getName() + " : " + std.getLastmsg();
                }else if((!c1.equals(std.getName())) && (c1 != null )){
                    message = "You : " + std.getLastmsg();
                }



                String Date00 = std.getDate();
                String time1 = null;

                if(Date00.equals(currentDateandTime21) && Date00 != null){
                    time1 = std.getTime();
                }else if((!Date00.equals(currentDateandTime21)) && Date00 != null){
                    time1 = Date00;
                }

                name.setText(std.getName());
                time.setText(time1);
                msg.setText(message);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //x.vibrate(80);
                        String uid = std.getUid();
                        String name1 = std.getName();
                        if(uid != null && name1 != null){
                            Intent i = new Intent(chatl.this, Chat.class);
                            i.putExtra("s", uid);
                            i.putExtra("s1" , name1);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    }
                });
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
        Intent i = new Intent(chatl.this, MainActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
