package com.example.housinghubv6.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.housinghubv6.Favourites.FavouritesActivity;
import com.example.housinghubv6.Group.GroupActivity;
import com.example.housinghubv6.Login.LoginActivity;
import com.example.housinghubv6.R;
import com.example.housinghubv6.Settings.SettingsActivity;
import com.example.housinghubv6.Utils.UniversalImageLoader;
import com.example.housinghubv6.YourAccount.YourAccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Home page that includes th navigation.
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";
    private Context context = HomeActivity.this;
    private static final int ACTIVITY_NUM = 0;

    ImageView mprofilePhoto;
    private DrawerLayout drawer;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ProgressDialog progressDialog;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_home);
        Log.d(TAG,"Creating activity");

        initImageLoader();
        //Inflates the drawer and toolbar
        Toolbar toolbar = findViewById(R.id.widget_toolbar);
        setSupportActionBar(toolbar);

        setupFirebaseAuth();

        setUpNavigationBar(toolbar);

        // initialize the progress dialog
        progressDialog = new ProgressDialog(this);
    }

    private void setupImageGrid(ArrayList<String> imgURLS){
        GridView gridView = (GridView) findViewById(R.id.gridview);
    }

    private void setUpNavigationBar(Toolbar toolbar){
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        mprofilePhoto = (ImageView) hView.findViewById(R.id.profile_picture);
        setProfileImage();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, mprofilePhoto, null, "https://");
    }


    /**
     * For every item in the drawer,the corresponding activity is opened.
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_account:
                Toast.makeText(context,"Clicked account",Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(context, YourAccountActivity.class); //ACTIVITY_NUM=3
                context.startActivity(intent4);

                break;

            case R.id.nav_favourite:
                Toast.makeText(context,"Clicked favourite",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(context, FavouritesActivity.class);//ACTIVITY_NUM=2
                context.startActivity(intent3);

                break;
            case R.id.nav_group:
                Toast.makeText(context,"Clicked group",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, GroupActivity.class);//ACTIVITY_NUM=1
                context.startActivity(intent2);

                break;
            case R.id.nav_home:
                Toast.makeText(context,"Clicked home",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, HomeActivity.class); //ACTIVITY_NUM=0
                context.startActivity(intent1);
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "Clicked settings", Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Clicked settings",Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(context, SettingsActivity.class);//ACTIVITY_NUM=4
                context.startActivity(intent5);

                break;

            case R.id.nav_signout_:
                progressDialog.setMessage("Signing Out User...");
                progressDialog.show();

                Toast.makeText(this,"Sign out", Toast.LENGTH_SHORT).show();

                firebaseAuth.signOut();
                progressDialog.hide();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.nav_help:
                // Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        checkCurrentUser(firebaseAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}

