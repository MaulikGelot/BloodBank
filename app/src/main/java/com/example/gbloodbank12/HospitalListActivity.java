package com.example.gbloodbank12;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HospitalListActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private ListView listViewHospitals;
    private List<HospitalInformation> hospitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        reference = FirebaseDatabase.getInstance().getReference("Hospital");
        listViewHospitals = findViewById(R.id.listViewHospitals);
        hospitalList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitalList.clear();
                for(DataSnapshot hospitalSnapshot: dataSnapshot.getChildren()){
                    HospitalInformation hospital = hospitalSnapshot.getValue(HospitalInformation.class);
                    hospitalList.add(hospital);
                }
                HospitalList adapter = new HospitalList(HospitalListActivity.this,hospitalList);
                listViewHospitals.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
