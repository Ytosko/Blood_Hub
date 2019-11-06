package com.ytosko.bloodhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    Vibrator x;
    EditText em,pn;
    Button y,z;
    String uid;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog pDialog;
    public String blood;
    String Token;
    FirebaseFirestore noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                login.class));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar a = getSupportActionBar();
        a.hide();
        em = findViewById(R.id.mail);
        pn = findViewById(R.id.pin);
        y = findViewById(R.id.lin);


        pDialog = new ProgressDialog(login.this);
        pDialog.setMessage("Authenticating");

        x = (Vibrator)this.getSystemService( Context.VIBRATOR_SERVICE);

        mFirebaseAuth = FirebaseAuth.getInstance();

        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.vibrate(80);
                String maill = em.getText().toString().trim();
                String pinn = pn.getText().toString().trim();

                if(maill.isEmpty()){
                    em.setError("Please enter email id");
                    em.requestFocus();
                }
                else  if(pinn.isEmpty()){
                    pn.setError("Please enter your pin");
                    pn.requestFocus();
                }
                else  if(maill.isEmpty() && pinn.isEmpty()){
                    Toast.makeText(login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(maill.isEmpty() && pinn.isEmpty())){
                    pDialog.show();
                    mFirebaseAuth.signInWithEmailAndPassword(maill, pinn).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                pDialog.dismiss();
                                Toast.makeText(login.this,"Authentication error",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                boolean emailVerified = user.isEmailVerified();
                                if(emailVerified){
                                    Intent intToHome = new Intent(login.this, profile.class);
                                    intToHome.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intToHome);
                                    finish();
                                }
                                else if(!emailVerified)
                                {
                                    pDialog.dismiss();
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(login.this,"E - mail is not vatified",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                else{

                    pDialog.dismiss();
                    Toast.makeText(login.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
