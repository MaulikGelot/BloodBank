package com.example.gbloodbank12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "@MainActivity";

    private EditText emailTextView;
    private EditText passwordTextView;
    private Button loginButton;
    private TextView registerTextView;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser user;


    public void findActivityToStart(){
        user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*boolean data = dataSnapshot.child("Client").child(uid).exists();
                Toast.makeText(MainActivity.this,Boolean.toString(data), Toast.LENGTH_LONG).show();*/
                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    if (dataSnapshot.child("Client").child(uid).exists()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), ClientPage.class));
                    } else {
                        finish();
                        startActivity(new Intent(getApplicationContext(),HospitalPage.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void userLogin(){
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }

        //if validation is ok first we show a progress dialog
        progressDialog.setMessage("login progress...");
        progressDialog.show();

        //login user
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                                findActivityToStart();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "please Verified your email!", Toast.LENGTH_SHORT).show();
                            }
                            //startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Error in login", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Please check Username/Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void loginButtonClicked(View view){
        userLogin();
    }


    public void registerViewClicked(View view){
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


     /* if(firebaseAuth.getCurrentUser()!=null) {
              findActivityToStart();//startActivity(new Intent(this, ProfileActivity.class));
          }*/



        progressDialog = new ProgressDialog(this);

        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

    }
}