package com.ytosko.bloodhub;

import androidx.annotation.NonNull;
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
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {
    Button search,chat,noti,call,globo;
    Vibrator x;
    private Dialog dialog1,dialog2,dialog3;
    private ProgressDialog pDialog;
    protected FirebaseUser user;
    FirebaseFirestore noti1;
    public final int PICK_CONTACT1 = 2015;
    public final int PICK_CONTACT2 = 2014;
    public final int PICK_CONTACT3 = 2013;
    EditText con1,con2,con3;
    Button pcon1,pcon2,pcon3,set;
    String num;
    FirebaseFirestore notific;
    String value1,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                MainActivity2.class));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity2.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });

        x = (Vibrator)this.getSystemService( Context.VIBRATOR_SERVICE);
        Button donor = findViewById(R.id.dnr);
        search = findViewById(R.id.sbl);
        pDialog = new ProgressDialog(MainActivity2.this);
        pDialog.setMessage("Singing out");
        chat = findViewById(R.id.chatting);
        noti = findViewById(R.id.noti);
        call = findViewById(R.id.calling);
        noti1 = FirebaseFirestore.getInstance();
        globo = findViewById(R.id.gc);
        notific = FirebaseFirestore.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){

            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O-").child(uid);
            dbRefFirstTimeCheck.keepSynced(true);

            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        value1 = dataSnapshot.child("name").getValue(String.class);
                    } else {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("O+").child(uid);
                        dbRefFirstTimeCheck.keepSynced(true);
                        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    value1 = dataSnapshot.child("name").getValue(String.class);
                                } else {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A-").child(uid);
                                    dbRefFirstTimeCheck.keepSynced(true);
                                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                value1 = dataSnapshot.child("name").getValue(String.class);
                                            } else {
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("A+").child(uid);
                                                dbRefFirstTimeCheck.keepSynced(true);
                                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            value1 = dataSnapshot.child("name").getValue(String.class);
                                                        } else {
                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                            DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB-").child(uid);
                                                            dbRefFirstTimeCheck.keepSynced(true);
                                                            dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                    if (dataSnapshot.exists()) {
                                                                        value1 = dataSnapshot.child("name").getValue(String.class);
                                                                    } else {
                                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                        DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("AB+").child(uid);
                                                                        dbRefFirstTimeCheck.keepSynced(true);
                                                                        dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.exists()) {
                                                                                    value1 = dataSnapshot.child("name").getValue(String.class);
                                                                                } else {
                                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                    DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B-").child(uid);
                                                                                    dbRefFirstTimeCheck.keepSynced(true);
                                                                                    dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                            if (dataSnapshot.exists()) {
                                                                                                value1 = dataSnapshot.child("name").getValue(String.class);

                                                                                            } else {
                                                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                                                                DatabaseReference dbRefFirstTimeCheck = databaseReference.child("Donor").child("B+").child(uid);
                                                                                                dbRefFirstTimeCheck.keepSynced(true);
                                                                                                dbRefFirstTimeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                        if (dataSnapshot.exists()) {
                                                                                                            value1 = dataSnapshot.child("name").getValue(String.class);
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

            String Token = FirebaseInstanceId.getInstance().getToken();
            Map<String, Object> map3 = new HashMap<>();
            map3.put("Token" , Token);
            notific.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(map3).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

            SimpleDateFormat sdf121 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
            String timelast = sdf121.format(new Date ());
            DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
            DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
            io.setValue (timelast);

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, chatl.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });
            noti.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, notify.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });

            globo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("Names" , value1 + " ");
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, globochat.class);
                    i.putExtra("value1" , value1 + "");
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();

                }
            });

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    DatabaseReference em = FirebaseDatabase.getInstance().getReference("Contact");
                    final DatabaseReference calls = em.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    calls.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                dialog3 = new Dialog(MainActivity2.this);
                                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog3.setContentView(R.layout.popupget);
                                dialog3.show();
                                final TextView con1,con2,con3;
                                Button call1,call2,call3,sms;

                                con1 = dialog3.findViewById(R.id.con1);
                                con2 = dialog3.findViewById(R.id.con2);
                                con3 = dialog3.findViewById(R.id.con3);

                                call1 = dialog3.findViewById(R.id.call1);
                                call2 = dialog3.findViewById(R.id.call2);
                                call3 = dialog3.findViewById(R.id.call3);

                                sms = dialog3.findViewById(R.id.callcon);

                                final String contact1 = dataSnapshot.child("con1").getValue(String.class);
                                con1.setText(contact1);
                                final String contact2 = dataSnapshot.child("con2").getValue(String.class);
                                con2.setText(contact2);
                                final String contact3 = dataSnapshot.child("con3").getValue(String.class);
                                con3.setText(contact3);

                                call1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        x.vibrate(80);
                                        Intent xy = new Intent(Intent.ACTION_CALL);
                                        xy.setData(Uri.parse("tel:" + con1.getText().toString()));
                                        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                        } else {
                                            startActivity(xy);
                                        }
                                    }
                                });
                                call2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        x.vibrate(80);
                                        Intent xy = new Intent(Intent.ACTION_CALL);
                                        xy.setData(Uri.parse("tel:" + con2.getText().toString()));
                                        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                        } else {
                                            startActivity(xy);
                                        }
                                    }
                                });
                                call3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        x.vibrate(80);
                                        Intent xy = new Intent(Intent.ACTION_CALL);
                                        xy.setData(Uri.parse("tel:" + con3.getText().toString()));
                                        if (ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                                        } else {
                                            startActivity(xy);
                                        }
                                    }
                                });
                                sms.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        intent.setData(Uri.parse("smsto:" + Uri.encode(contact1 + "; " + contact2 + "; " + contact3)));
                                        startActivity(intent);
                                    }
                                });

                            }
                            else{
                                dialog3 = new Dialog(MainActivity2.this);
                                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog3.setContentView(R.layout.popupset);
                                dialog3.show();

                                con1 = dialog3.findViewById(R.id.con11);
                                con2 = dialog3.findViewById(R.id.con21);
                                con3 = dialog3.findViewById(R.id.con31);

                                pcon1 = dialog3.findViewById(R.id.pcon1);
                                pcon2 = dialog3.findViewById(R.id.pcon2);
                                pcon3 = dialog3.findViewById(R.id.pcon3);
                                set = dialog3.findViewById(R.id.callcon1);


                                pcon1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                                        startActivityForResult(i, PICK_CONTACT1);
                                        //con1.setText(num);
                                    }
                                });
                                pcon2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                                        startActivityForResult(i, PICK_CONTACT2);
                                    }
                                });
                                pcon3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                                        startActivityForResult(i, PICK_CONTACT3);
                                    }
                                });

                                set.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (con1.getText().toString().equals("")){
                                            con1.setError("Please enter a contact number");
                                            con1.requestFocus();
                                        }
                                        else if(con2.getText().toString().equals("")){
                                            con2.setError("Please enter a contact number");
                                            con2.requestFocus();
                                        }
                                        else if(con3.getText().toString().equals("")){
                                            con3.setError("Please enter a contact number");
                                            con3.requestFocus();
                                        }
                                        else{
                                            calls.child("con1").setValue(con1.getText().toString());
                                        calls.child("con2").setValue(con2.getText().toString());
                                        calls.child("con3").setValue(con3.getText().toString());
                                        Toast.makeText(MainActivity2.this, "Emergency contact information has been updated", Toast.LENGTH_SHORT).show();
                                        dialog3.dismiss();
                                    }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        else{
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, login.class);
                    Toast.makeText(getApplicationContext(), "Please log in first" , Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });
            globo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, login.class);
                    Toast.makeText(getApplicationContext(), "Please log in first" , Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });

            noti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, login.class);
                    Toast.makeText(getApplicationContext(), "Please log in first" , Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x.vibrate(80);
                    Intent i = new Intent(MainActivity2.this, login.class);
                    Toast.makeText(getApplicationContext(), "Please log in first" , Toast.LENGTH_SHORT).show();
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    finish();
                }
            });
        }

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                x.vibrate(80);

                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    dialog1 = new Dialog(MainActivity2.this);
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.popup3);
                    dialog1.show();

                    Button old1 = dialog1.findViewById(R.id.prf);
                    Button new11 = dialog1.findViewById(R.id.out);

                    SimpleDateFormat sdf121 = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
                    String timelast = sdf121.format(new Date ());
                    DatabaseReference status = FirebaseDatabase.getInstance ().getReference ("Status");
                    DatabaseReference io = status.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ());
                    io.setValue (timelast);

                    old1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x.vibrate(80);
                            Intent i = new Intent(MainActivity2.this, profile.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            finish();
                        }
                    });

                    new11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> map3 = new HashMap<>();
                            map3.put("Token" , "");
                            noti1.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(map3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                            pDialog.show();
                            x.vibrate(80);
                                AuthUI.getInstance()
                                        .signOut(MainActivity2.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
                                                pDialog.dismiss();
                                                dialog1.dismiss();
                                                Toast.makeText(MainActivity2.this,"You are now Singed out",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                        }
                    });

                }
                else {
                    dialog2 = new Dialog(MainActivity2.this);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.popup);
                    dialog2.show();

                    Button old = dialog2.findViewById(R.id.old);
                    Button new1 = dialog2.findViewById(R.id.cnf1);

                    old.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x.vibrate(80);
                            Intent i = new Intent(MainActivity2.this, login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            finish();
                        }
                    });

                    new1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            x.vibrate(80);
                            Intent i = new Intent(MainActivity2.this, logup.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                            finish();
                        }
                    });
                }

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                x.vibrate(80);
                Intent i = new Intent(MainActivity2.this , search1.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT1 && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Log.e("phone number", cursor.getString(column));
            con1.setText(cursor.getString(column));
        }
        if (requestCode == PICK_CONTACT2 && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Log.e("phone number", cursor.getString(column));
            con2.setText(cursor.getString(column));
        }
        if (requestCode == PICK_CONTACT3 && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Log.e("phone number", cursor.getString(column));
            con3.setText(cursor.getString(column));
        }
    }

}
