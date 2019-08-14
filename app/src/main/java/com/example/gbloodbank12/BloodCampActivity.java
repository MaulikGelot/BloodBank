package com.example.gbloodbank12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.UUID;
import java.util.Calendar;

public class BloodCampActivity extends AppCompatActivity {

    private ImageButton addImageButton;
    private EditText editTextName;
    private EditText editTextPlace;
    private EditText editTextDate;
    private Button saveBloodCampButton;
    private ProgressDialog progressDialog;
    private Calendar calendar;
    private String currentdate;
    int Date1,year1,month1;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    //private String uID;
    private String downloadUrl;

    private static final int GALLERY_INTENT = 2;

    public void addImage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);*/
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
        //startActivityForResult(Intent.createChooser(intent,"Select Picture"),GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData()!=null){
            Uri uri = data.getData();
            addImageButton.setImageURI(uri);
            /*try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                addImageButton.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }*/
            progressDialog.setMessage("Photo Uploading...");
            progressDialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    downloadUrl = urlTask.getResult().toString();
                    //downloadUrl = storageReference.getDownloadUrl();
                    Toast.makeText(BloodCampActivity.this, "Upload Successful..", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(BloodCampActivity.this, "Upload Fail..", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                }
            });
        }else{
            Toast.makeText(this, "Error in image Selection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveInfo(View view){
        String name = editTextName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String place = editTextPlace.getText().toString().trim();
        //String imagePath = storageReference.toString();
        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Your organization name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!name.matches("^[a-zA-z ]*$")) {

            Toast.makeText(this, "Please Enter Your Correct Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(date.isEmpty()){
            Toast.makeText(this, "Please Enter Date ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!date.matches("^[0-9/]*$")) {
            Toast.makeText(this, "Please Enter Correct Date.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(date.length()==10 && date.charAt(2) == '/' && date.charAt(5)=='/')){
            Toast.makeText(this, "Enter date format: 01/01/2000", Toast.LENGTH_SHORT).show();
            return;
        }
        if(place.isEmpty()){
            Toast.makeText(this, "Please Enter Your place.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!place.matches("^[a-zA-Z0-9/,'\" ]*$")) {

            Toast.makeText(this, "Please Enter Your Correct place", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!validdate(date)){
            Toast.makeText(this, "Please Enter Correct Date ", Toast.LENGTH_SHORT).show();
            return;
        }

        BloodCampInformation bloodCamp = new BloodCampInformation(downloadUrl,name,date,place);
        databaseReference.setValue(bloodCamp);
        Toast.makeText(this, "your Information is Saved", Toast.LENGTH_SHORT).show();
    }
    public boolean validdate(String date) {

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
        if(month<month1 && year<=year1) {
            return false;
        }
        if(date1<Date1 && month<=month1) {
            return false;
        }
        if(month>12){return false;}
        if(date1>31){return false;}
        else
            return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_camp);

        addImageButton = findViewById(R.id.addImageButton);
        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextPlace = findViewById(R.id.editTextPlace);
        saveBloodCampButton = findViewById(R.id.saveInfoButton);
        progressDialog = new ProgressDialog(this);

        // get current date,month,year

        calendar=Calendar.getInstance();
        Date1=calendar.get(Calendar.DATE);
        year1=calendar.get(Calendar.YEAR);
        month1=calendar.get(Calendar.MONTH)+1;



        firebaseDatabase = FirebaseDatabase.getInstance();
        //uID = FirebaseAuth.getInstance().getUid();
        //databaseReference = firebaseDatabase.getReference().child("BloodCamp").child(uID).child(UUID.randomUUID().toString());
        String nUId = UUID.randomUUID().toString();
        databaseReference = firebaseDatabase.getReference().child("BloodCamp").child(nUId);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("BloodCampImages").child(nUId);
    }
}