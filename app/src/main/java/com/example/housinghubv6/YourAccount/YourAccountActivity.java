package com.example.housinghubv6.YourAccount;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.housinghubv6.R;
import com.example.housinghubv6.Utils.GridImageAdapter;
import com.example.housinghubv6.Utils.UniversalImageLoader;

import java.util.ArrayList;

public class YourAccountActivity extends AppCompatActivity {

    private static final String TAG = "YourAccountActivity";
    private static final int NUM_GRID_COLUMNS = 3;
    private Context context = YourAccountActivity.this;
    //private static final int ACTIVITY_NUM = 0;

    private Button bEditProfile;
    private ImageView bBackArrow;
    private ImageView profilePhoto;


    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_youraccount);

        Log.d(TAG,"Creating activity");
        setUpActivityWidgets();
        setProfileImage();
        tempGridSetup();
    }

    private void tempGridSetup(){
        ArrayList<String> imgURLS = new ArrayList<>();
        imgURLS.add("https://consent.trustarc.com/get?name=transparent.png");
        imgURLS.add("https://consent.trustarc.com/get?name=transparent.pnghttps://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60https://consent.trustarc.com/get?name=transparent.png");

        setUpImageGrid(imgURLS);
    }

    private void setUpImageGrid(ArrayList<String> imgURLS) {
        GridView gridView = (GridView) findViewById(R.id.gridview);

        int gridwidth = getResources().getDisplayMetrics().widthPixels;
        int imagewidth = gridwidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imagewidth);
        GridImageAdapter adapter = new GridImageAdapter(context,R.layout.layout_gridimageview,"",imgURLS);
        gridView.setAdapter(adapter);
    }

    /**
     *
     */
    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, profilePhoto, progressBar, "https://");
    }

    /**
     *
     */
    private void setUpActivityWidgets(){
        //Set up back arrow
        bBackArrow = (ImageView)findViewById(R.id.backArrow);
        bBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Going back to home page");
                finish();
            }
        });

        //set up edit profile button
        bEditProfile = (Button)findViewById(R.id.editProfileButton);
        bEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                EditAccountFragment newFragment = new EditAccountFragment();
                fm.beginTransaction().replace(R.id.fragment_container, newFragment).commit();
            }
        });

        //FOR THE PROFILE PHOTO
        profilePhoto = (ImageView)findViewById(R.id.profile_picture);

        //set up the progress bar
          progressBar = (ProgressBar)findViewById(R.id.profileProgressBar);
         // progressBar.setVisibility(View.GONE);

    }

}

