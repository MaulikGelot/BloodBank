package  com.example.gbloodbank12;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ClientPage extends AppCompatActivity {

    private Button signoutButton;
    private Button addBloodCampButton;
    private ViewFlipper imageFlipper;
    int images[] = {R.drawable.poster1,R.drawable.poster2,R.drawable.poster3};

    public void showBloodRequirement(View view){
        startActivity(new Intent(this,BloodRequirementListActivity.class));
    }

    public void recentBloodCamp(View view){
        startActivity(new Intent(this,RecentBloodcampActivity.class));
    }

    public void addBloodCampButtonClicked(View view){
        startActivity(new Intent(this,BloodCampActivity.class));
    }

    public void hospitalListView(View view){
        startActivity(new Intent(this,HospitalListActivity.class));
    }

    public void signoutButtonClicked(View view){
        FirebaseAuth.getInstance().signOut();
        finishAffinity();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        imageFlipper.addView(imageView);
        imageFlipper.setFlipInterval(5000);
        imageFlipper.setAutoStart(true);

        //animation
        imageFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        imageFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);

        signoutButton = findViewById(R.id.signoutButton);
        addBloodCampButton = findViewById(R.id.addBloodCampButton);
        imageFlipper = findViewById(R.id.imageFlipper);
        for(int image: images){
            flipperImages(image);
        }
    }
}