package com.ytosko.bloodhub;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.Map;


public class logup extends AppCompatActivity {

    public String name,email,age,number,blood,local,type;
    EditText nm,mail,ag,nmbr;
    Vibrator x;
    private FirebaseAuth mAuth;
    MaterialBetterSpinner materialDesignSpinner;
    MaterialBetterSpinner materialDesignSpinner1;
    MaterialBetterSpinner materialDesignSpinner2;
    private ProgressDialog pDialog;
    private Dialog dialog3;
    public static FirebaseDatabase mFirebaseDatabase;
    EditText a;
    String p;
    private  Button x2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                logup.class));
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        name = null;
        email = null;
        age = null;
        number = null;
        blood = null;
        local = null;

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a1 = getSupportActionBar();
        a1.hide();

        nm = findViewById(R.id.nm);
        mail = findViewById(R.id.ml);
        ag = findViewById(R.id.age);
        nmbr = findViewById(R.id.num);
        x2 = findViewById( R.id.bad );
        x = (Vibrator)this.getSystemService( Context.VIBRATOR_SERVICE);
        mAuth = FirebaseAuth.getInstance();

        pDialog = new ProgressDialog(logup.this);
        pDialog.setMessage("Adding as a donor");
        String[] SPINNERLIST = { "A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.bld1);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] SPINNERLIST1 = {"Dhaka", "Rajshahi", "Barisal", "Sylhet", "Comilla", "Cittagong", "Maymensingh", "Rangpur"};

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST1);
        materialDesignSpinner1 = (MaterialBetterSpinner) findViewById(R.id.lcl1);
        materialDesignSpinner1.setAdapter(arrayAdapter1);

        String[] SPINNERLIST11 = {"Public" , "Private"};

        ArrayAdapter<String> arrayAdapter11 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST11);
        materialDesignSpinner2 = (MaterialBetterSpinner) findViewById(R.id.typee1);
        materialDesignSpinner2.setAdapter(arrayAdapter11);

        x2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.vibrate(80);
                name = nm.getText().toString().trim();
                email = mail.getText().toString().trim();
                age = ag.getText().toString().trim();
                number = nmbr.getText().toString().trim();
                blood = materialDesignSpinner.getText().toString();
                local = materialDesignSpinner1.getText().toString();
                type = materialDesignSpinner2.getText().toString();

                if(name.equals("")){
                    nm.setError("Please enter your name");
                    nm.requestFocus();
                }
                else if(email.equals("")){
                    mail.setError("Please enter your e-mail");
                    mail.requestFocus();
                }
                else if(age.equals("") || age.length() > 2){
                    ag.setError("Please enter your valid age");
                    ag.requestFocus();
                }
                else if(number.equals("") || number.length() > 11 || number.length() <11){
                    nmbr.setError("Please enter your valid contact number");
                    nmbr.requestFocus();
                }
                else if(blood.equals("")){
                    materialDesignSpinner.setError("Please choose your blood group");
                    materialDesignSpinner.requestFocus();
                }
                else if(local.equals("")){
                    materialDesignSpinner1.setError("Please choose your location");
                    materialDesignSpinner1.requestFocus();
                }
                else if(type.equals("")){
                    materialDesignSpinner2.setError("Please choose your Privicy");
                    materialDesignSpinner2.requestFocus();
                }
                else{
                    dialog3 = new Dialog(logup.this);
                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.popup2);
                    dialog3.show();

                    a = dialog3.findViewById(R.id.pin);
                    Button b = dialog3.findViewById( R.id.cnf );

                    b.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            p = null;
                            p = a.getText().toString().trim();
                            if(p.equals("") || p.length() > 6 || p.length()<6){
                                a.setError("Enter a 6 digit pin");
                                a.requestFocus();
                            }

                            else{
                                pDialog.show();
                                mAuth.createUserWithEmailAndPassword(email, p).addOnCompleteListener(logup.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        pDialog.dismiss();
                                        if (task.isSuccessful()) {

                                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                     @Override
                                                                                                                     public void onComplete(@NonNull Task<Void> task) {

                                                                                                                         if (task.isSuccessful()) {

                                                                                                                             final Donor user = new Donor(
                                                                                                                                     name,
                                                                                                                                     email,
                                                                                                                                     number,
                                                                                                                                     blood,
                                                                                                                                     age,
                                                                                                                                     local,
                                                                                                                                     "av",
                                                                                                                                     "0",
                                                                                                                                     "no",
                                                                                                                                     type
                                                                                                                             );

                                                                                                                             FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                     .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                     .child("name").setValue(user.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                 @Override
                                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                     if (task.isSuccessful()) {
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("email").setValue(user.getEmail());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("phone").setValue(user.getPhone());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("blood").setValue(user.getBlood());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("age").setValue(user.getAge());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("location").setValue(user.getLocation());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("status").setValue(user.getStatus());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("par").setValue(user.getPar());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("varified").setValue(user.getVarified());
                                                                                                                                         FirebaseDatabase.getInstance().getReference("Donor")
                                                                                                                                                 .child(blood).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                                 .child("type").setValue(user.getType());
                                                                                                                                         FirebaseAuth.getInstance().signOut();
                                                                                                                                         Toast.makeText(logup.this,"Please log in after email varification",Toast.LENGTH_SHORT).show();
                                                                                                                                         Intent intToHome = new Intent(logup.this,login.class);
                                                                                                                                         intToHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                                                                                                         startActivity(intToHome);
                                                                                                                                         pDialog.dismiss();
                                                                                                                                         finish();
                                                                                                                                     } else {
                                                                                                                                         pDialog.dismiss();
                                                                                                                                         Toast.makeText(logup.this,"Try again",Toast.LENGTH_SHORT).show();
                                                                                                                                     }
                                                                                                                                 }
                                                                                                                             });

                                                                                                                         } else {
                                                                                                                             Toast.makeText( logup.this , task.getException().getMessage() , Toast.LENGTH_SHORT ).show();
                                                                                                                         }
                                                                                                                     }
                                                                                                                 });


                                        } else {
                                            overridePendingTransition(0, 0);
                                            finish();
                                            overridePendingTransition(0, 0);
                                            startActivity(getIntent());

                                            pDialog.dismiss();
                                            Toast.makeText(logup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    } );

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
        Intent i = new Intent(logup.this, MainActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
