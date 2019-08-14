package com.example.gbloodbank12;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.UUID;

public class BloodRequirementActivity extends AppCompatActivity {

    private EditText editTextClientName;
    private EditText editTextBloodGroup;
    private EditText editTextContactNo;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uId;
    private String h_name,h_contactNo;


    public void saveInfo(){
        String c_name = editTextClientName.getText().toString().trim();
        String bloodGroup = editTextBloodGroup.getText().toString().trim();
        String c_contactNo = editTextContactNo.getText().toString().trim();

        if(c_name.isEmpty()){
            Toast.makeText(this, "Please Enter your clientName.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!c_name.matches("^[a-zA-z ]*$")) {
            Toast.makeText(this, "Please Enter Correct Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bloodGroup.isEmpty()){
            Toast.makeText(this, "Please Enter your required BloodGroup.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bloodGroup.matches("^[aboABO+-]*$")&& (bloodGroup.length()==3 || bloodGroup.length()==4)) {

            Toast.makeText(this, "Please Enter Your Correct BloodGroup.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(c_contactNo.isEmpty()){
            Toast.makeText(this, "Please Enter your client contactNo.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!c_contactNo.matches("^([6-9]{1,2}+)[0-9]*$")&&(c_contactNo.length()!=10)) {

            Toast.makeText(this, "Please Enter Your Correct ContactNo.", Toast.LENGTH_SHORT).show();
            return;
        }


        BloodRequirement bloodData = new BloodRequirement(h_name, c_name, bloodGroup, h_contactNo, c_contactNo);
        databaseReference.setValue(bloodData);
    }

    public void addRequirement(View view){
        saveInfo();
    }

    public void saveHospitalValue(){
        //get Hospital value
        DatabaseReference h_ref = FirebaseDatabase.getInstance().getReference().child("Hospital").child(uId);
        h_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                h_name = dataSnapshot.child("name").getValue(String.class);
                Log.d("TAG",h_name);
                h_contactNo = dataSnapshot.child("contactNo").getValue(String.class);
                Log.d("TAG",h_contactNo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(this,"hospital name "+h_name, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_requirement);
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        saveHospitalValue();
        editTextClientName = findViewById(R.id.beditTextClientName);
        editTextBloodGroup = findViewById(R.id.beditTextBloodGroup);
        editTextContactNo  = findViewById(R.id.beditTextContactNo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BloodRequirement").child(UUID.randomUUID().toString());

    }
}