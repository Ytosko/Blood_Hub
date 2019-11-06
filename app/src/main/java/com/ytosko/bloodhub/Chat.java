package com.ytosko.bloodhub;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    TextView titlebar;
    ScrollView scrollView;
    Firebase reference1, reference2, reference11, ref3, ref4;
    String uid, value;
    FirebaseFirestore noti, mfire;
    FirebaseAuth mAuth;
    String s, s1;
    int xv = 0;
    int xw = 0;
    int w = 0;
    String osthir = null;
    Button call,ind;
    TextView status1;
    DatabaseReference statusGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        s = getIntent().getStringExtra("s");
        s1 = getIntent().getStringExtra("s1");

        SimpleDateFormat sdf121 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        String timelast = sdf121.format(new Date ());
        statusGet = FirebaseDatabase.getInstance ().getReference ("Status");
        DatabaseReference io = statusGet.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
        io.setValue (timelast);

        Log.e("Might ne", s + " " + s1);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.whiteCardColor));
        }
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        titlebar = (TextView) findViewById(R.id.title);
        noti = FirebaseFirestore.getInstance();
        status1 = (TextView)findViewById (R.id.status);
        ind = findViewById (R.id.statusind);

        titlebar.setText(s1);

        DatabaseReference io1 = statusGet.child (s);
        io1.keepSynced (true);
        io1.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists ()){
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                        String timelast1 = format.format(new Date ());

                        String st = dataSnapshot.getValue (String.class);

                        Date d1 = null;
                        Date d2 = null;

                        if(timelast1 == null){
                            ind.setBackgroundResource (R.drawable.notactive);
                            status1.setText ("Away");

                        }
                        else{

                            try {
                                d2 = format.parse(timelast1);
                            } catch (ParseException e) {
                                e.printStackTrace ();
                            }
                            try {
                                d1 = format.parse(st);
                            } catch (ParseException e) {
                                e.printStackTrace ();
                            }


                        long diff = d2.getTime() - d1.getTime();

                        long diffSeconds = diff / 1000 % 60;
                        long diffMinutes = diff / (60 * 1000) % 60;
                        long diffHours = diff / (60 * 60 * 1000) % 24;
                        long diffDays = diff / (24 * 60 * 60 * 1000);

                        String boxtext = null;

                        Log.e("Time" , diffDays + " " + diffHours + " " + diffMinutes + " " + diff);

                        if(diffDays<1 && diffHours<1 && diffMinutes<=2){
                            ind.setBackgroundResource (R.drawable.activenow);
                            boxtext = "Active now";
                        }
                        else if(diffDays<1 && diffHours<1 && diffMinutes>2){
                            ind.setBackgroundResource (R.drawable.notactive);
                            boxtext = "Active " + diffMinutes + " minutes ago";
                        }
                        else if(diffDays<1 && diffHours>=1){
                            ind.setBackgroundResource (R.drawable.notactive);
                            boxtext = "Active " + diffHours + " hours ago";
                        }
                        else if(diffDays>=1){
                            ind.setBackgroundResource (R.drawable.notactive);
                            boxtext = "Active " + diffDays + " days ago";
                        }
                            status1.setText (boxtext);
                        }


                }
                else{
                    ind.setBackgroundResource (R.drawable.notactive);
                    status1.setText ("Away");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ind.setBackgroundResource (R.drawable.notactive);
                status1.setText ("Away");
            }
        });

        final ScrollView scrollView = ((ScrollView) findViewById(R.id.scrollView));
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scrollView.setNestedScrollingEnabled(true);
        }

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://bldhub.firebaseio.com/messages/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "-" + s);
        reference2 = new Firebase("https://bldhub.firebaseio.com/messages/" + s + "-" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference11 = new Firebase("https://bldhub.firebaseio.com/notification/" + s);
        //reference22 = new Firebase("https://bldhub.firebaseio.com/notification/" + value1 + "-" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref3 = new Firebase("https://bldhub.firebaseio.com/Chat/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + s);
        ref4 = new Firebase("https://bldhub.firebaseio.com/Chat/" + s + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference1.keepSynced(true);
        reference2.keepSynced(true);
        ref3.keepSynced(true);
        ref4.keepSynced(true);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O-").child(uid);
        dbRefFirstTimeCheck.keepSynced(true);
        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    value = dataSnapshot.child("name").getValue(String.class);
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O+").child(uid);
                    dbRefFirstTimeCheck.keepSynced(true);
                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                value = dataSnapshot.child("name").getValue(String.class);
                            } else {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A-").child(uid);
                                dbRefFirstTimeCheck.keepSynced(true);
                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            value = dataSnapshot.child("name").getValue(String.class);
                                        } else {
                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A+").child(uid);
                                            dbRefFirstTimeCheck.keepSynced(true);
                                            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        value = dataSnapshot.child("name").getValue(String.class);
                                                    } else {
                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB-").child(uid);
                                                        dbRefFirstTimeCheck.keepSynced(true);
                                                        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    value = dataSnapshot.child("name").getValue(String.class);
                                                                } else {
                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB+").child(uid);
                                                                    dbRefFirstTimeCheck.keepSynced(true);
                                                                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                value = dataSnapshot.child("name").getValue(String.class);
                                                                            } else {
                                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B-").child(uid);
                                                                                dbRefFirstTimeCheck.keepSynced(true);
                                                                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                        if (dataSnapshot.exists()) {
                                                                                            value = dataSnapshot.child("name").getValue(String.class);

                                                                                        } else {
                                                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B+").child(uid);
                                                                                            dbRefFirstTimeCheck.keepSynced(true);
                                                                                            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                    if (dataSnapshot.exists()) {
                                                                                                        value = dataSnapshot.child("name").getValue(String.class);
                                                                                                    } else {

                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(DatabaseError databaseError) {
                                                                                                    throw databaseError.toException();
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                                        throw databaseError.toException();
                                                                                    }
                                                                                });
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {
                                                                            throw databaseError.toException();
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                throw databaseError.toException();
                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    throw databaseError.toException();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        throw databaseError.toException();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        final String key = FirebaseAuth.getInstance().getCurrentUser().getUid();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa", Locale.getDefault());
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String currentDateandTime1 = sdf1.format(new Date());

                SimpleDateFormat sdf121 = new SimpleDateFormat("dd/MM", Locale.getDefault());
                String currentDateandTime21 = sdf121.format(new Date());

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", value);
                    map.put("time", currentDateandTime);
                    map.put("date" , currentDateandTime1);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);

                    Map<String, String> mapx = new HashMap<String, String>();
                    if(!value.equals (titlebar.getText().toString())){
                        mapx.put("notification" , value + " sent you a sms");
                        mapx.put("time" , currentDateandTime + " at " + currentDateandTime1);
                    }
                    reference11.push().setValue(mapx);
                    Map<String, String> chat = new HashMap<String, String>();
                    chat.put("name", titlebar.getText().toString());
                    chat.put("uid", s);
                    chat.put("lastsender", value);
                    chat.put("time" , currentDateandTime);
                    chat.put("lastmsg" , messageText);
                    chat.put("date" , currentDateandTime21);
                    ref3.setValue(chat);
                    Map<String, String> chat2 = new HashMap<String, String>();
                    chat2.put("name", value);
                    chat2.put("uid", uid);
                    chat2.put("lastsender", value);
                    chat2.put("time" , currentDateandTime);
                    chat2.put("lastmsg" , messageText);
                    chat2.put("date" , currentDateandTime21);
                    ref4.setValue(chat2);
                    messageArea.setText("");
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("message", messageText);
                    map2.put("from", key);
                    map2.put("chatwith", s);
                    map2.put("chatfrom", value);
                    SimpleDateFormat sdf1214 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                    String timelast = sdf1214.format(new Date ());
                    DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
                    DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
                    io.setValue (timelast);
                    noti.collection("Users/" + s + "/Notifications").add(map2).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    });

                    DatabaseReference io1 = statusGet.child (s);
                    io1.keepSynced (true);
                    io1.addListenerForSingleValueEvent (new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists ()){
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                                String timelast1 = format.format(new Date ());

                                String st = dataSnapshot.getValue (String.class);

                                Date d1 = null;
                                Date d2 = null;

                                if(timelast1 == null){
                                    ind.setBackgroundResource (R.drawable.notactive);
                                    status1.setText ("Away");

                                }
                                else{

                                    try {
                                        d2 = format.parse(timelast1);
                                    } catch (ParseException e) {
                                        e.printStackTrace ();
                                    }
                                    try {
                                        d1 = format.parse(st);
                                    } catch (ParseException e) {
                                        e.printStackTrace ();
                                    }


                                    long diff = d2.getTime() - d1.getTime();

                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;
                                    long diffDays = diff / (24 * 60 * 60 * 1000);

                                    String boxtext = null;

                                    Log.e("Time" , diffDays + " " + diffHours + " " + diffMinutes + " " + diff);

                                    if(diffDays<1 && diffHours<1 && diffMinutes<=2){
                                        ind.setBackgroundResource (R.drawable.activenow);
                                        boxtext = "Active now";
                                    }
                                    else if(diffDays<1 && diffHours<1 && diffMinutes>2){
                                        ind.setBackgroundResource (R.drawable.notactive);
                                        boxtext = "Active " + diffMinutes + " minutes ago";
                                    }
                                    else if(diffDays<1 && diffHours>=1){
                                        ind.setBackgroundResource (R.drawable.notactive);
                                        boxtext = "Active " + diffHours + " hours ago";
                                    }
                                    else if(diffDays>=1){
                                        ind.setBackgroundResource (R.drawable.notactive);
                                        boxtext = "Active " + diffDays + " days ago";
                                    }
                                    status1.setText (boxtext);
                                }


                            }
                            else{
                                ind.setBackgroundResource (R.drawable.notactive);
                                status1.setText ("Away");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            ind.setBackgroundResource (R.drawable.notactive);
                            status1.setText ("Away");
                        }
                    });

                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                vhathelp map = dataSnapshot.getValue(vhathelp.class);
                String message = map.getMessage();
                String userName = map.getUser();
                String timeC = map.getTime();
                String timeD = map.getDate();
                reference1.keepSynced(true);

                /*DatabaseReference io1 = statusGet.child (value1);
                io1.keepSynced (true);
                io1.addListenerForSingleValueEvent (new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists ()){
                            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                            String timelast1 = format.format(new Date ());

                            String st = dataSnapshot.getValue (String.class);

                            Date d1 = null;
                            Date d2 = null;

                            if(timelast1 == null){
                                ind.setBackgroundResource (R.drawable.notactive);
                                status1.setText ("Away");

                            }
                            else{

                                try {
                                    d2 = format.parse(timelast1);
                                } catch (ParseException e) {
                                    e.printStackTrace ();
                                }
                                try {
                                    d1 = format.parse(st);
                                } catch (ParseException e) {
                                    e.printStackTrace ();
                                }


                                long diff = d2.getTime() - d1.getTime();

                                long diffSeconds = diff / 1000 % 60;
                                long diffMinutes = diff / (60 * 1000) % 60;
                                long diffHours = diff / (60 * 60 * 1000) % 24;
                                long diffDays = diff / (24 * 60 * 60 * 1000);

                                String boxtext = null;

                                Log.e("Time" , diffDays + " " + diffHours + " " + diffMinutes + " " + diff);

                                if(diffDays<1 && diffHours<1 && diffMinutes<=2){
                                    ind.setBackgroundResource (R.drawable.activenow);
                                    boxtext = "Active now";
                                }
                                else if(diffDays<1 && diffHours<1 && diffMinutes>2){
                                    ind.setBackgroundResource (R.drawable.notactive);
                                    boxtext = "Active " + diffMinutes + " minutes ago";
                                }
                                else if(diffDays<1 && diffHours>=1){
                                    ind.setBackgroundResource (R.drawable.notactive);
                                    boxtext = "Active " + diffHours + " hours ago";
                                }
                                else if(diffDays>=1){
                                    ind.setBackgroundResource (R.drawable.notactive);
                                    boxtext = "Active " + diffDays + " days ago";
                                }
                                status1.setText (boxtext);
                            }


                        }
                        else{
                            ind.setBackgroundResource (R.drawable.notactive);
                            status1.setText ("Away");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ind.setBackgroundResource (R.drawable.notactive);
                        status1.setText ("Away");
                    }
                });*/


                if (!userName.equals(s1)) {
                    addMessageBox(userName, message, timeC, timeD, 1);
                } else {
                    addMessageBox(userName, message, timeC, timeD, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void addMessageBox(String userName, String message, String timeC, String timeD, int type) {


            final TextView textView_1 = new TextView(Chat.this);
            SimpleDateFormat sdf121 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDateandTime21 = sdf121.format(new Date());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            String s = dateFormat.format(cal.getTime());

            if(timeD.equals(currentDateandTime21)){
                textView_1.setText("Today");
            }
            else if(timeD.equals(s)){
                textView_1.setText("Yesterday");
            }else{
                textView_1.setText(timeD);
            }

            textView_1.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            textView_1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView_1.setTextColor(Color.GRAY);
            textView_1.setVisibility(View.GONE);
            textView_1.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            final TextView textView0 = new TextView(Chat.this);
            textView0.setText(userName);
            final TextView textView = new TextView(Chat.this);
            textView.setText(message);
        final TextView textView1 = new TextView(Chat.this);
        textView1.setText(timeC);
        textView1.setVisibility(View.GONE);


        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (type == 1) {
            xw = 0;
            if(xv!=0){
                textView0.setVisibility(View.GONE);
            }
            lp2.gravity = Gravity.RIGHT;
            textView0.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setBackgroundResource(R.drawable.layout_bg1);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            if(w!=0){
                textView_1.setVisibility(View.GONE);
                osthir = timeD;
            }
            scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 1000);
            xv++;

        } else {
            xv = 0;
            if(xw!=0){
                textView0.setVisibility(View.GONE);
            }
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.layout_bg2);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            textView.setTextColor(Color.WHITE);
            textView0.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 1000);
            xw++;
        }
        textView0.setLayoutParams(lp2);
        textView0.setPadding(0, 40, 0, 0);
        textView0.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(lp2);
        textView.setPadding(50, 20, 50, 20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView1.setLayoutParams(lp2);
        layout.addView(textView_1);
        layout.addView(textView0);
        layout.addView(textView);
        layout.addView(textView1);
        scrollView.fullScroll(View.FOCUS_DOWN);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView1.getVisibility() == View.GONE){
                    textView1.setVisibility(View.VISIBLE);
                    textView_1.setVisibility(View.VISIBLE);
                }
                else if(textView1.getVisibility() == View.VISIBLE){
                    textView1.setVisibility(View.GONE);
                    textView_1.setVisibility(View.GONE);
                }
            }
        });
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
        Intent i = new Intent(Chat.this, chatl.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
