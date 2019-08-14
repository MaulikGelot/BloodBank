package com.example.gbloodbank12;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecentBloodcampActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //private List dateList;
    private Calendar calendar;
    private String currentdate;
    int Date1,year1,month1;



    public boolean validDate(String date) {
        char d1 = date.charAt(0);
        char d2 = date.charAt(1);
        char m1 = date.charAt(3);
        char m2 = date.charAt(4);
        char y1 = date.charAt(6);
        char y2 = date.charAt(7);
        char y3 = date.charAt(8);
        char y4 = date.charAt(9);

        String d11 = String.valueOf(d1);
        String d12 = String.valueOf(d2);
        String d = d11 + d12;
        int date1 = Integer.parseInt(d);

        String m11 = String.valueOf(m1);
        String m12 = String.valueOf(m2);
        String m = m11 + m12;
        int month = Integer.parseInt(m);

        String y11 = String.valueOf(y1);
        String y12 = String.valueOf(y2);
        String y13 = String.valueOf(y3);
        String y14 = String.valueOf(y4);
        String y = y11 + y12 + y13 + y14;
        int year = Integer.parseInt(y);

        if (year<year1) {
            return false;
        }
        if ((month<month1)&& (year<=year1)) {
            return false;
        }
        if ((date1<Date1)&&(month<=month1)) {
            return false;
        }
        if(month>12){return false;}
        if(date1>31){return false;}
        else
            return true;
    }

    public void deleteDataFile(String uId){
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("BloodCamp").child(uId);
        dr.removeValue();
        StorageReference sr = FirebaseStorage.getInstance().getReference().child("BloodCampImages").child(uId);
        sr.delete();
    }

    public void reCreateDatabase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("BloodCamp");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String date = ds.child("date").getValue().toString();
                    if(!validDate(date)){
                        String uId = ds.getKey();
                        deleteDataFile(uId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String date = ds.child("date").getValue().toString();
                    if(!validDate(date)){
                        String uId = ds.getKey();
                        deleteDataFile(uId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_bloodcamp);

        //action bar
        ActionBar actionBar = getSupportActionBar();
        //setTitle
        actionBar.setTitle("BloodCamp Lists");
        //Recycler View
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //set Layout as linear layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send query to firebase database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BloodCamp");
        //dateList = new ArrayList<String>();
        // get current date,month,year
        calendar=calendar.getInstance();
        Date1=calendar.get(calendar.DATE);
        year1=calendar.get(calendar.YEAR);
        month1=calendar.get(calendar.MONTH)+1;

        //reCreateDatabase();
    }
    //load data into recycler view onStart

    @Override
    protected void onStart() {
        super.onStart();
        reCreateDatabase();
        FirebaseRecyclerAdapter<BloodCampInformation,ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BloodCampInformation, ViewHolder>(
                        BloodCampInformation.class,
                        R.layout.recyclerview,
                        ViewHolder.class,
                        databaseReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, BloodCampInformation model, int position) {
                        viewHolder.setDetails(getApplicationContext(),model.getDownloadUrl(),model.getOrganizerName(),model.getDate(),model.getPlace());
                    }
                };

        //set adapter to recyclerView
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}