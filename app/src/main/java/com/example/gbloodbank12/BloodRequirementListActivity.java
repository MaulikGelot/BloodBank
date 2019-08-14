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

public class BloodRequirementListActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private ListView listViewBloodRequirement;
    private List<BloodRequirement> requirementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_requirement_list);

        reference = FirebaseDatabase.getInstance().getReference("BloodRequirement");
        listViewBloodRequirement = findViewById(R.id.listViewRequirements);
        requirementList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requirementList.clear();
                for(DataSnapshot bloodSnapshot: dataSnapshot.getChildren()){
                    BloodRequirement info = bloodSnapshot.getValue(BloodRequirement.class);
                    requirementList.add(info);
                }
                BloodRequirementList adapter = new BloodRequirementList(BloodRequirementListActivity.this,requirementList);
                listViewBloodRequirement.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}