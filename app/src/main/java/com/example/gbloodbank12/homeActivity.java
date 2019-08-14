package com.example.gbloodbank12;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private AlertDialog.Builder builder1,builder2;
    private AlertDialog alert1,alert2;

    public boolean checkNetwork(){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private class ScreenLauncher extends Thread{
        @Override
        public void run() {
            try {
                sleep(4000);
            } catch (InterruptedException ae) {
                ae.printStackTrace();
            }
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void getUserData(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        if (firebaseAuth.getCurrentUser() != null) {
            final String uid = firebaseAuth.getCurrentUser().getUid();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                        if (dataSnapshot.child("Client").child(uid).exists()) {
                            startActivity(new Intent(getApplicationContext(), ClientPage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            homeActivity.this.finish();
                        } else {
                            startActivity(new Intent(getApplicationContext(), HospitalPage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            homeActivity.this.finish();
                        }

                    }
                    else{
                        startActivity(new Intent(homeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else{
            Thread threadScreen = new ScreenLauncher();
            threadScreen.start();
            /*startActivity(new Intent(home.this, MainActivity.class));
            home.this.finish();*/
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        Boolean connected = checkNetwork();

        //2nd dialog box
        builder2 = new AlertDialog.Builder(this);
        builder2.setCancelable(false);
        builder2.setTitle("No Connection!")
                .setMessage("This app requires an internet connection!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean isConnected = checkNetwork();
                        if(isConnected){
                            getUserData();
                            //threadScreen.start();
                        }else{
                            alert1.show();
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        alert2 = builder2.create();
        alert2.setCanceledOnTouchOutside(false);

        //1st dialog box
        builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setTitle("No Connection!")
                .setMessage("This app requires an internet connection!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean isConnected = checkNetwork();
                        if(isConnected){
                            getUserData();
                            //threadScreen.start();
                        }else{
                            alert2.show();
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        alert1 = builder1.create();
        alert1.setCanceledOnTouchOutside(false);

        if(connected){
            getUserData();
        }else{
            alert1.show();
        }
    }
}

/*
*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
*   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 */