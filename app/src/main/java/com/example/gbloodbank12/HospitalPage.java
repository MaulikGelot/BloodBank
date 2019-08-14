package  com.example.gbloodbank12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalPage extends AppCompatActivity {

    private Button addBloodCampButton;
    private Button signoutButton;
    private ViewFlipper imageFlipper;
    int images[] = {R.drawable.poster1,R.drawable.poster2,R.drawable.poster3};

    public void addBloodCampButtonClicked(View view){
        startActivity(new Intent(this,BloodCampActivity.class));
    }

    public void recentBloodCamp(View view){
        startActivity(new Intent(this,RecentBloodcampActivity.class));
    }

    public void addBloodRequirement(View view){
        startActivity(new Intent(this,BloodRequirementActivity.class));
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
        setContentView(R.layout.activity_hospital_page);

        addBloodCampButton = findViewById(R.id.addBloodCampButton);
        signoutButton = findViewById(R.id.signoutButton);
        imageFlipper = findViewById(R.id.imageFlipper);
        for(int image: images){
            flipperImages(image);
        }
    }
}