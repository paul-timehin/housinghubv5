package com.example.housinghubv6.Favourites;

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
import android.widget.Toast;

import com.example.housinghubv6.Group.GroupActivity;
import com.example.housinghubv6.Home.HomeActivity;
import com.example.housinghubv6.R;
import com.example.housinghubv6.Settings.SettingsActivity;
import com.example.housinghubv6.YourAccount.YourAccountActivity;


public class FavouritesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "FavouritesActivity";
    private Context context = FavouritesActivity.this;
    private static final int ACTIVITY_NUM = 0;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_favourites);
        Log.d(TAG,"Creating activity");
        Toolbar toolbar = findViewById(R.id.widget_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

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
                // Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
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

}
