package com.example.housinghubv6.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.housinghubv6.R;


public class AccountChoiceActivity extends AppCompatActivity {
    private boolean isStudent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_usertype);

        //Sets background imageview to the background image within the drawable folder
        ImageView background = findViewById(R.id.imAccountActivityBackground);
        background.setImageResource(R.drawable.backgroundhouse);

        //On press the student button will execute the code contained within the onClick function.
        Button student = findViewById(R.id.btIsStudent);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates an intent and starts a new activity
                Intent studentact = new Intent(AccountChoiceActivity.this, StudentSignupActivity.class);
                //if the user is a student then pass the value "isStudent"
                isStudent = true;
                studentact.putExtra("isStudent",isStudent);
                startActivity(studentact);

                finish();
            }
        });


        //On press the student button will execute the code contained within the onClick function.
        Button landlord = findViewById(R.id.btIsLandlord);
        landlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates an intent and starts a new activity
                Intent landlordact = new Intent(AccountChoiceActivity.this, LandlordSignupActivity.class);
                //if the user is not a student then pass the value "isStudent" is false
                isStudent = false;
                landlordact.putExtra("isStudent",isStudent);
                startActivity(landlordact);

                finish();
            }
        });
    }


}
