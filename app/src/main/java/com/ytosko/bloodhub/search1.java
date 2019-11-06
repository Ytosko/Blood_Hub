package com.ytosko.bloodhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class search1 extends AppCompatActivity {

    Vibrator x;
    DatabaseReference iref,ref,mref;
    String m,n;
    private ProgressDialog pDialog;
    Button z,gk;
    MaterialBetterSpinner materialDesignSpinner, materialDesignSpinner1;
    public TextView abc;
    ListView l;
    ArrayList<Donor> al = new ArrayList<Donor> (  );
    ArrayAdapter<Donor> ad;
    TextView asd;
    String asf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                search1.class));
        iref = FirebaseDatabase.getInstance().getReference("Donor");
        iref.keepSynced(true);
        z = findViewById( R.id.srch);
        abc = findViewById( R.id.title );
        l = (ListView)findViewById( R.id.listing );
        ad = new ArrayAdapter( this, android.R.layout.simple_expandable_list_item_1, al);
        l.setAdapter( ad );
        gk = findViewById ( R.id.profile22 );
        asd = (TextView) findViewById ( R.id.p23 );

        pDialog = new ProgressDialog(search1.this);
        pDialog.setMessage("Searching");

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar abn = getSupportActionBar();
        abn.hide();
        x = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        String[] SPINNERLIST = { "A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.bld77);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] SPINNERLIST1 = {"Dhaka", "Rajshahi", "Barisal", "Sylhet", "Comilla", "Cittagong", "Maymensingh", "Rangpur"};

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST1);
        materialDesignSpinner1 = (MaterialBetterSpinner)
                findViewById(R.id.lcl77);
        materialDesignSpinner1.setAdapter(arrayAdapter1);

        z.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.vibrate( 80 );
                m = materialDesignSpinner.getText().toString();
                n = materialDesignSpinner1.getText().toString();

                if(m.equals( "" )){
                    materialDesignSpinner.setError( "Please enter valid blood group" );
                    materialDesignSpinner.requestFocus();
                }
                else if(n.equals( "" )){
                    materialDesignSpinner1.setError( "Please enter valid location" );
                    materialDesignSpinner1.requestFocus();
                }
                else {
                    if (ad.getCount ( ) > 0){
                        ad.clear ( );
                    }
                    pDialog.show();
                    ref = iref.child(m);
                    ref.keepSynced(true);
                    ref.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists ())
                            {
                                ref.addChildEventListener( new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot1 , @Nullable String s) {
                                        if(dataSnapshot1.exists()) {
                                                Donor Value = dataSnapshot1.getValue ( Donor.class );
                                                String name = dataSnapshot1.child("name").getValue(String.class);
                                                String loc = dataSnapshot1.child("location").getValue(String.class);
                                                String status = dataSnapshot1.child("status").getValue(String.class);
                                                String var = dataSnapshot1.child("varified").getValue(String.class);
                                                String mail = dataSnapshot1.child("email").getValue(String.class);
                                                Value.setName(name);
                                                String idYouWant = dataSnapshot1.getKey ( );
                                                Value.setKey ( idYouWant );
                                                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                                                    if (loc != null && status != null && var != null && mail != null) {
                                                        if ((loc.equals(n) && status.equals("av") && var.equals("yes")) && !mail.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                            abc.setVisibility(View.VISIBLE);
                                                            pDialog.dismiss();
                                                            ad.add(Value);
                                                        }
                                                    }
                                                }
                                                else{
                                                    if (loc != null && status != null && var != null) {
                                                        if ((loc.equals(n) && status.equals("av") && var.equals("yes"))) {
                                                            abc.setVisibility(View.VISIBLE);
                                                            pDialog.dismiss();
                                                            ad.add(Value);
                                                        }
                                                    }
                                                }
                                            } else {
                                                pDialog.dismiss ( );
                                            }
                                            if (ad.getCount ( ) > 0) {
                                                abc.setText ( "Available donors" );
                                            } else {
                                                pDialog.dismiss ( );
                                                abc.setVisibility ( View.VISIBLE );
                                                abc.setText ( "No donor found for this blood group" );
                                            }

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot , @Nullable String s) {
                                        pDialog.dismiss ();
                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                                        pDialog.dismiss ();
                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot , @Nullable String s) {
                                        pDialog.dismiss ();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast toast = Toast.makeText(search1.this,"Unable to connect to internet", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                } );
                            }
                            else{
                                pDialog.dismiss ();
                                abc.setVisibility ( View.VISIBLE );
                                abc.setText("No donor found for this blood group");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(search1.this,"Unable to connect internet",Toast.LENGTH_SHORT).show();
                        }
                    } );
                }

            }
        } );

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Donor game = al.get(position);
                final String key = game.getKey();
                asf = game.getName();
                Dialog dialog2 = new Dialog (search1.this);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable ( Color.TRANSPARENT));
                dialog2.requestWindowFeature( Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.popup5);
                dialog2.show();

                final TextView name,location,Blood,age,phone;
                final Button notify,call;
                name = dialog2.findViewById ( R.id.namev );
                location = dialog2.findViewById ( R.id.locv );
                Blood = dialog2.findViewById ( R.id.bloodv );
                age = dialog2.findViewById ( R.id.agev );
                phone = dialog2.findViewById ( R.id.phonev );
                notify = dialog2.findViewById ( R.id.chatv );
                call = dialog2.findViewById ( R.id.callv );
                


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child(m).child(key);
                dbRefFirstTimeCheck.keepSynced(true);
                dbRefFirstTimeCheck.addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            String value = dataSnapshot.child ( "name" ).getValue ( String.class );
                            name.setText ( value );
                            String value2 = dataSnapshot.child ( "age" ).getValue ( String.class );
                            age.setText ( value2 );
                            String value3 = dataSnapshot.child ( "blood" ).getValue ( String.class );
                            Blood.setText ( value3 );

                            String value4 = dataSnapshot.child ( "location" ).getValue ( String.class );
                            location.setText ( value4 );

                            String value5 = dataSnapshot.child ( "type" ).getValue ( String.class );

                            if(value5.equals("Public")) {
                                String value6 = dataSnapshot.child("phone").getValue(String.class);
                                phone.setText(value6);
                            }
                            else{
                                phone.setText("Private Number");
                                call.setVisibility(View.GONE);
                            }
                        }
                        else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );


                notify.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        x.vibrate ( 80 );
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                            Intent i = new Intent(search1.this, Chat.class);
                            i.putExtra("s", key);
                            i.putExtra("s1" , asf);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Intent i = new Intent ( search1.this, login.class );
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity ( i );
                                Toast.makeText(search1.this,"Please log in first to chat",Toast.LENGTH_SHORT).show();
                                finish();
                        }
                    }
                } );
                call.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {
                        x.vibrate ( 80 );

                            Intent xy = new Intent(Intent.ACTION_CALL);
                            xy.setData(Uri.parse("tel:" + phone.getText().toString()));
                            if (ContextCompat.checkSelfPermission(search1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(search1.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                            } else {
                                startActivity(xy);
                            }

                    }
                } );

            }
        });

        gk.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                x.vibrate ( 80 );
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent i = new Intent ( search1.this, profile.class );
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity ( i );
                    finish();
                }
                else{
                    Intent i = new Intent ( search1.this, login.class );
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity ( i );
                    finish();
                }
            }
        } );
        asd.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                x.vibrate ( 80 );
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent i = new Intent ( search1.this, profile.class );
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity ( i );
                    finish();
                }
                else{
                    Intent i =  new Intent ( search1.this, login.class );
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity (i);
                    Toast.makeText(search1.this,"Please log in first",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } );
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
        Intent i = new Intent(search1.this, MainActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
