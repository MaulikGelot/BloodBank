package com.example.gbloodbank12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalInfoActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextContactNo;
    private Button saveInfoButton;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    public void saveHospitalInfo(){
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String contactNo = editTextContactNo.getText().toString().trim();

        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Your name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!name.matches("^[a-zA-z ]*$")) {

            Toast.makeText(this, "Please Enter Your Correct Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(address.isEmpty()){
            Toast.makeText(this, "Please Enter Your address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!address.matches("^[a-zA-Z0-9/,'\" -.]*$")) {

            Toast.makeText(this, "Please Enter Your Correct address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(contactNo.isEmpty()){
            Toast.makeText(this, "Please Enter Your mobileNo.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!contactNo.matches("^([6-9]{1,2}+)[0-9]*$")&&(contactNo.length()!=10)) {

            Toast.makeText(this, "Please Enter Your Correct ContactNo.", Toast.LENGTH_SHORT).show();
            return;
        }


        HospitalInformation hospitalData = new HospitalInformation(name,address,contactNo);
        String user_id = firebaseAuth.getCurrentUser().getUid();
        //reference.push().setValue(hospitalData);
        reference.child(user_id).setValue(hospitalData);
        Toast.makeText(this, "Your Information is saved.", Toast.LENGTH_SHORT).show();
        if(firebaseAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(this,HospitalPage.class));
            finish();
        }else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    public void saveInfoButtonClicked(View view){
        saveHospitalInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info);

        editTextAddress = findViewById(R.id.editTextAddress);
        editTextName = findViewById(R.id.editTextName);
        editTextContactNo = findViewById(R.id.editTextContactNo);
        saveInfoButton = findViewById(R.id.saveInfoButton);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Hospital");
    }
}