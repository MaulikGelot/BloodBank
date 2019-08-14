package com.example.gbloodbank12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.WithHint;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText emailTextView;
    private EditText passwordTextView;
    private Button signupButton;
    private Button sendemail;
    private TextView loginTextView;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private String userChoice;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private void registerUser() {
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!email.matches("^([a-zA-Z0-9_.!#$%&'*+-/=?^_`{|}~;]+)@([a-zA-Z0-9_.]+)\\.([a-zA-Z]{2,5})$")){
            Toast.makeText(this, "Please Enter Your Correct Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<8){
            Toast.makeText(this, "Password should be minimum 8 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        //if validation is ok we will first shows a progress Dialog
        progressDialog.setMessage("Registering user");
        progressDialog.show();

        //creating user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //checking if success
                        if (task.isSuccessful()) {
                            //User is successfully registered and logged in
                            //we will start the profile activity here*/
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Verification email sent to your email", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to sent Verification email to your email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });




                            //startActivity(new Intent(getApplicationContext(),ProfileActivity.class))
                           // if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                if (userChoice.equals("Hospital")) {
                                    startActivity(new Intent(getApplicationContext(),HospitalInfoActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(),ClientInfoActivity.class));
                                    finish();

                                }

                                // }
                                //else {
                                //Toast.makeText(RegisterActivity.this, "please Verified your email!", Toast.LENGTH_SHORT).show();
                                //}
                            }
                    }
                });
    }


    public void signupButtonClicked(View view){
        registerUser();
    }

    public void loginViewClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        signupButton = findViewById(R.id.signupButton);
        //sendemail=findViewById(R.id.sendemail);
        emailTextView = findViewById(R.id.emailTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        loginTextView = findViewById(R.id.loginTextView);

        spinnerChoice = findViewById(R.id.spinnerChoice);
        adapter = ArrayAdapter.createFromResource(this,R.array.userChoice,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);
        spinnerChoice.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userChoice = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}