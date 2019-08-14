package com.example.gbloodbank12;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.regex.Pattern;



public class ClientInfoActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextMobileNo;
    private EditText editTextBlood;
    private Button saveInfoButton;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    public void saveClientInfo() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String mobileNo = editTextMobileNo.getText().toString().trim();
        String bloodGroup = editTextBlood.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Your name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!name.matches("^[a-zA-z ]*$")) {
            Toast.makeText(this, "Please Enter Your correct name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Please Enter Your address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!address.matches("^[a-zA-Z0-9/,'\"\\- ]*$")) {

            Toast.makeText(this, "Please Enter Your Correct address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobileNo.isEmpty()) {
            Toast.makeText(this, "Please Enter Your mobileNo.", Toast.LENGTH_SHORT).show();
            return;
        }
        if((!mobileNo.matches("^([6-9]{1,2}+)[0-9]*$")) && mobileNo.length()==10) {
            Toast.makeText(this, "Please Enter Your Correct mobileNo.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bloodGroup.isEmpty()) {
            Toast.makeText(this, "Please Enter Your BloodGroup", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bloodGroup.matches("^[aboABO+-]*$")&& (bloodGroup.length()==3 || bloodGroup.length()==4)) {

            Toast.makeText(this, "Please Enter Your Correct BloodGroup.", Toast.LENGTH_SHORT).show();
            return;
        }
        String user_id = firebaseAuth.getCurrentUser().getUid();
        ClientInformation clientData = new ClientInformation(name, address, mobileNo, bloodGroup);
        //reference.push().setValue(clientData);
        reference.child(user_id).setValue(clientData);

        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(ClientInfoActivity.this, ClientPage.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
           finish();
        }
    }

    public void saveInfoButtonClicked(View view){
        saveClientInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        editTextAddress = findViewById(R.id.editTextAddress);
        editTextName = findViewById(R.id.editTextName);
        editTextMobileNo = findViewById(R.id.editTextMobileNo);
        editTextBlood = findViewById(R.id.editTextBlood);
        saveInfoButton = findViewById(R.id.saveInfoButton);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Client");

    }


}