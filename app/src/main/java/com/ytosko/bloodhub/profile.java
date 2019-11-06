package com.ytosko.bloodhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class profile extends AppCompatActivity {
    Vibrator x;
    TextView name,email,age,blood,loc,contact,contact1;
    private ProgressDialog pDialog;
    String uid;
    Button upd;
    MaterialBetterSpinner materialDesignSpinner1,materialDesignSpinner,materialDesignSpinner2;
    public String a1,b1,c1,d1,blood11,Token;
    FirebaseFirestore noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                profile.class));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();

        pDialog = new ProgressDialog(profile.this);
        pDialog.setMessage("Please wait");

        x = (Vibrator)this.getSystemService( Context.VIBRATOR_SERVICE);
        name = findViewById( R.id.name1 );
        email = findViewById( R.id.email1 );
        age = findViewById( R.id.age1 );
        blood = findViewById( R.id.blood1 );
        loc = findViewById( R.id.local1 );
        contact = findViewById( R.id.mumber1 );
        contact1 = findViewById( R.id.typee );
        upd = findViewById( R.id.upd1 );
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noti = FirebaseFirestore.getInstance();

        Token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map3 = new HashMap<>();
        map3.put("Token" , Token);
        noti.collection("Users").document(uid).set(map3).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        if(name.getText().toString().equals( "" )){
            pDialog.show();
        }




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O-").child(uid);
        dbRefFirstTimeCheck.keepSynced(true);
        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child("name").getValue(String.class);
                    name.setText( value );
                    String value1 = dataSnapshot.child("email").getValue(String.class);
                    email.setText( value1 );
                    String value2 = dataSnapshot.child("age").getValue(String.class);
                    age.setText( value2 );
                    String value3 = dataSnapshot.child("blood").getValue(String.class);
                    blood.setText( value3 );

                    String value4 = dataSnapshot.child("location").getValue(String.class);
                    loc.setText( value4 );
                    String value5 = dataSnapshot.child("phone").getValue(String.class);
                    contact.setText( value5 );
                    String value6 = dataSnapshot.child("type").getValue(String.class);
                    contact1.setText( value6 );
                    DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                    DatabaseReference ref1 = mrref1.child(uid);
                    Map<String, Object> updates1 = new HashMap<String,Object>();
                    updates1.put("varified", "yes");
                    ref1.updateChildren(updates1);
                    pDialog.dismiss();
                }
                else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O+").child(uid);
                    dbRefFirstTimeCheck.keepSynced(true);
                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                String value = dataSnapshot.child("name").getValue(String.class);
                                name.setText( value );
                                String value1 = dataSnapshot.child("email").getValue(String.class);
                                email.setText( value1 );
                                String value2 = dataSnapshot.child("age").getValue(String.class);
                                age.setText( value2 );
                                String value3 = dataSnapshot.child("blood").getValue(String.class);
                                blood.setText( value3 );

                                String value4 = dataSnapshot.child("location").getValue(String.class);
                                loc.setText( value4 );
                                String value5 = dataSnapshot.child("phone").getValue(String.class);
                                contact.setText( value5 );
                                String value6 = dataSnapshot.child("type").getValue(String.class);
                                contact1.setText( value6 );
                                DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                DatabaseReference ref1 = mrref1.child(uid);
                                Map<String, Object> updates1 = new HashMap<String,Object>();
                                updates1.put("varified", "yes");
                                ref1.updateChildren(updates1);
                                pDialog.dismiss();
                            }
                            else{
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A-").child(uid);
                                dbRefFirstTimeCheck.keepSynced(true);
                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()) {
                                            String value = dataSnapshot.child("name").getValue(String.class);
                                            name.setText( value );
                                            String value1 = dataSnapshot.child("email").getValue(String.class);
                                            email.setText( value1 );
                                            String value2 = dataSnapshot.child("age").getValue(String.class);
                                            age.setText( value2 );
                                            String value3 = dataSnapshot.child("blood").getValue(String.class);
                                            blood.setText( value3 );
                                            String value6 = dataSnapshot.child("type").getValue(String.class);
                                            contact1.setText( value6 );
                                            String value4 = dataSnapshot.child("location").getValue(String.class);
                                            loc.setText( value4 );
                                            String value5 = dataSnapshot.child("phone").getValue(String.class);
                                            contact.setText( value5 );
                                            DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                            DatabaseReference ref1 = mrref1.child(uid);
                                            Map<String, Object> updates1 = new HashMap<String,Object>();
                                            updates1.put("varified", "yes");
                                            ref1.updateChildren(updates1);
                                            pDialog.dismiss();
                                        }
                                        else{
                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A+").child(uid);
                                            dbRefFirstTimeCheck.keepSynced(true);
                                            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {
                                                        String value = dataSnapshot.child("name").getValue(String.class);
                                                        name.setText( value );
                                                        String value1 = dataSnapshot.child("email").getValue(String.class);
                                                        email.setText( value1 );
                                                        String value2 = dataSnapshot.child("age").getValue(String.class);
                                                        age.setText( value2 );
                                                        String value3 = dataSnapshot.child("blood").getValue(String.class);
                                                        blood.setText( value3 );
                                                        String value6 = dataSnapshot.child("type").getValue(String.class);
                                                        contact1.setText( value6 );
                                                        String value4 = dataSnapshot.child("location").getValue(String.class);
                                                        loc.setText( value4 );
                                                        String value5 = dataSnapshot.child("phone").getValue(String.class);
                                                        contact.setText( value5 );
                                                        DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                                        DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                                        DatabaseReference ref1 = mrref1.child(uid);
                                                        Map<String, Object> updates1 = new HashMap<String,Object>();
                                                        updates1.put("varified", "yes");
                                                        ref1.updateChildren(updates1);
                                                        pDialog.dismiss();
                                                    }
                                                    else{
                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB-").child(uid);
                                                        dbRefFirstTimeCheck.keepSynced(true);
                                                        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if(dataSnapshot.exists()) {
                                                                    String value = dataSnapshot.child("name").getValue(String.class);
                                                                    name.setText( value );
                                                                    String value1 = dataSnapshot.child("email").getValue(String.class);
                                                                    email.setText( value1 );
                                                                    String value2 = dataSnapshot.child("age").getValue(String.class);
                                                                    age.setText( value2 );
                                                                    String value3 = dataSnapshot.child("blood").getValue(String.class);
                                                                    blood.setText( value3 );
                                                                    String value6 = dataSnapshot.child("type").getValue(String.class);
                                                                    contact1.setText( value6 );
                                                                    String value4 = dataSnapshot.child("location").getValue(String.class);
                                                                    loc.setText( value4 );
                                                                    String value5 = dataSnapshot.child("phone").getValue(String.class);
                                                                    contact.setText( value5 );
                                                                    DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                                                    DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                                                    DatabaseReference ref1 = mrref1.child(uid);
                                                                    Map<String, Object> updates1 = new HashMap<String,Object>();
                                                                    updates1.put("varified", "yes");
                                                                    ref1.updateChildren(updates1);
                                                                    pDialog.dismiss();
                                                                }
                                                                else{
                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB+").child(uid);
                                                                    dbRefFirstTimeCheck.keepSynced(true);
                                                                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if(dataSnapshot.exists()) {
                                                                                String value = dataSnapshot.child("name").getValue(String.class);
                                                                                name.setText( value );
                                                                                String value1 = dataSnapshot.child("email").getValue(String.class);
                                                                                email.setText( value1 );
                                                                                String value2 = dataSnapshot.child("age").getValue(String.class);
                                                                                age.setText( value2 );
                                                                                String value3 = dataSnapshot.child("blood").getValue(String.class);
                                                                                blood.setText( value3 );
                                                                                String value6 = dataSnapshot.child("type").getValue(String.class);
                                                                                contact1.setText( value6 );
                                                                                String value4 = dataSnapshot.child("location").getValue(String.class);
                                                                                loc.setText( value4 );
                                                                                String value5 = dataSnapshot.child("phone").getValue(String.class);
                                                                                contact.setText( value5 );
                                                                                DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                                                                DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                                                                DatabaseReference ref1 = mrref1.child(uid);
                                                                                Map<String, Object> updates1 = new HashMap<String,Object>();
                                                                                updates1.put("varified", "yes");
                                                                                ref1.updateChildren(updates1);
                                                                                pDialog.dismiss();
                                                                            }
                                                                            else{
                                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B-").child(uid);
                                                                                dbRefFirstTimeCheck.keepSynced(true);
                                                                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                        if(dataSnapshot.exists()) {
                                                                                            String value = dataSnapshot.child("name").getValue(String.class);
                                                                                            name.setText( value );
                                                                                            String value1 = dataSnapshot.child("email").getValue(String.class);
                                                                                            email.setText( value1 );
                                                                                            String value2 = dataSnapshot.child("age").getValue(String.class);
                                                                                            age.setText( value2 );
                                                                                            String value3 = dataSnapshot.child("blood").getValue(String.class);
                                                                                            blood.setText( value3 );
                                                                                            String value6 = dataSnapshot.child("type").getValue(String.class);
                                                                                            contact1.setText( value6 );
                                                                                            String value4 = dataSnapshot.child("location").getValue(String.class);
                                                                                            loc.setText( value4 );
                                                                                            String value5 = dataSnapshot.child("phone").getValue(String.class);
                                                                                            contact.setText( value5 );
                                                                                            DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                                                                            DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                                                                            DatabaseReference ref1 = mrref1.child(uid);
                                                                                            Map<String, Object> updates1 = new HashMap<String,Object>();
                                                                                            updates1.put("varified", "yes");
                                                                                            ref1.updateChildren(updates1);
                                                                                            pDialog.dismiss();
                                                                                        }
                                                                                        else{
                                                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B+").child(uid);
                                                                                            dbRefFirstTimeCheck.keepSynced(true);
                                                                                            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                    if(dataSnapshot.exists()) {
                                                                                                        String value = dataSnapshot.child("name").getValue(String.class);
                                                                                                        name.setText( value );
                                                                                                        String value1 = dataSnapshot.child("email").getValue(String.class);
                                                                                                        email.setText( value1 );
                                                                                                        String value2 = dataSnapshot.child("age").getValue(String.class);
                                                                                                        age.setText( value2 );
                                                                                                        String value3 = dataSnapshot.child("blood").getValue(String.class);
                                                                                                        blood.setText( value3 );
                                                                                                        String value6 = dataSnapshot.child("type").getValue(String.class);
                                                                                                        contact1.setText( value6 );
                                                                                                        String value4 = dataSnapshot.child("location").getValue(String.class);
                                                                                                        loc.setText( value4 );
                                                                                                        String value5 = dataSnapshot.child("phone").getValue(String.class);
                                                                                                        contact.setText( value5 );
                                                                                                        DatabaseReference iref1= FirebaseDatabase.getInstance().getReference();
                                                                                                        DatabaseReference mrref1 = iref1.child("Donor").child(dataSnapshot.child("blood").getValue(String.class));
                                                                                                        DatabaseReference ref1 = mrref1.child(uid);
                                                                                                        Map<String, Object> updates1 = new HashMap<String,Object>();
                                                                                                        updates1.put("varified", "yes");
                                                                                                        ref1.updateChildren(updates1);
                                                                                                        pDialog.dismiss();
                                                                                                    }
                                                                                                    else{

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




        upd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.vibrate( 80 );
                final Dialog dialog2 = new Dialog(profile.this);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));
                dialog2.requestWindowFeature( Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.popup4);
                dialog2.show();

                String[] SPINNERLIST1 = {"Dhaka", "Rajshahi", "Barisal", "Sylhet", "Comilla", "Cittagong", "Maymensingh", "Rangpur"};

                ArrayAdapter<String> arrayAdapter1;
                arrayAdapter1 = new ArrayAdapter<String>( profile.this, android.R.layout.simple_dropdown_item_1line , SPINNERLIST1);
                materialDesignSpinner1 = (MaterialBetterSpinner) dialog2.findViewById(R.id.lcl2);
                materialDesignSpinner1.setAdapter(arrayAdapter1);

                String[] SPINNERLIST11 = {"Public" , "Private"};

                ArrayAdapter<String> arrayAdapter11;
                arrayAdapter11 = new ArrayAdapter<String>( profile.this, android.R.layout.simple_dropdown_item_1line , SPINNERLIST11);
                materialDesignSpinner2 = (MaterialBetterSpinner) dialog2.findViewById(R.id.typee);
                materialDesignSpinner2.setAdapter(arrayAdapter11);


                String[] SPINNERLIST = { "A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(profile.this,
                        android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
                materialDesignSpinner = (MaterialBetterSpinner) dialog2.findViewById(R.id.bld2);
                materialDesignSpinner.setAdapter(arrayAdapter2);

                final EditText b = dialog2.findViewById( R.id.ed2 );
                Button x1 = dialog2.findViewById( R.id.upd2 );
                final EditText c= dialog2.findViewById( R.id.age2 );




                x1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a1 = materialDesignSpinner1.getText().toString();
                        String b1 = b.getText().toString();
                        String c1 = c.getText().toString();
                        String d1 = materialDesignSpinner.getText().toString();
                        String d2 = materialDesignSpinner2.getText().toString();

                            pDialog.show();

                            if(a1.equals( "" )){
                                a1 = loc.getText().toString();
                            }
                            if(b1.equals( "" )){
                                b1 = contact.getText().toString();
                            }
                            if(c1.equals( "" )  || d1.equals( "" )){
                                c1 = age.getText().toString();
                            }
                            if(d1.equals( "" )){
                                d1 = blood.getText().toString();
                            }
                            if(d2.equals("")){
                                d2 = contact1.getText().toString();
                            }
                        if(d2.equals("")){
                            d2 = contact1.getText().toString();
                        }
                            if( b1.length() != 11 ||  c1.length() > 2){
                                b.setError("Please enter valid contact number");
                                b.requestFocus();
                                c.setError( "Enter valid age" );
                                c.requestFocus();

                            }
                                        final Donor user = new Donor(
                                                name.getText().toString(),
                                                email.getText().toString(),
                                                b1,
                                                d1,
                                                c1,
                                                a1,
                                                "av",
                                                "0",
                                                "yes",
                                                d2
                                        );



                            FirebaseDatabase.getInstance().getReference("Donor")
                                    .child(materialDesignSpinner.getText().toString()).child(uid).child("name")
                                    .setValue(user.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("email").setValue(user.getEmail());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("phone").setValue(user.getPhone());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("blood").setValue(user.getBlood());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("age").setValue(user.getAge());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("location").setValue(user.getLocation());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("status").setValue(user.getStatus());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("par").setValue(user.getPar());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("varified").setValue(user.getVarified());
                                        FirebaseDatabase.getInstance().getReference("Donor")
                                                .child(materialDesignSpinner.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child("type").setValue(user.getType());
                                        String blood1 = blood.getText().toString();
                                        DatabaseReference myref1 = FirebaseDatabase.getInstance().getReference("Donor");
                                        DatabaseReference uref = myref1.child(blood1);
                                        DatabaseReference myref = uref.child(uid);

                                        myref.removeValue();
                                        Toast.makeText(profile.this,"Values updated!",Toast.LENGTH_SHORT).show();
                                        dialog2.dismiss();
                                        Intent i = new Intent(profile.this, profile.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(i);
                                        pDialog.dismiss();
                                        finish();

                                    } else {
                                        pDialog.dismiss();
                                        Toast.makeText(profile.this,"Try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    }
                } );
            }
        } );
        SimpleDateFormat sdf121 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
        String timelast = sdf121.format(new Date ());
        DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
        DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
        io.setValue (timelast);

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
        Intent i = new Intent(profile.this, MainActivity2.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }

}
