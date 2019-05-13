package com.example.housinghubv6.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.housinghubv6.Home.HomeActivity;
import com.example.housinghubv6.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Context context = LoginActivity.this;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //set up the widgets
        setUpWidgets();

        setupFirebaseAuth();

        init();
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }


    private void init(){
        //initialize the button for logging in
        btnLogin = (Button) findViewById(R.id.btLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in.");

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(isStringNull(email) && isStringNull(password)){
                    Toast.makeText(context, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());

                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Log.d(TAG, "signInWithEmail: successful login");
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_success),
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                        startActivity(intent);

                                        finish();
                                    }

                                    // ...
                                }
                            });
                }

            }
        });

        //On press the account textview will execute the code contained within the onClick function.
        TextView NoAccount = findViewById(R.id.tvSignup);
        NoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates an intent and starts a new activity
                Log.d(TAG,"Navigating to register");
                Intent accountAct = new Intent(LoginActivity.this, AccountChoiceActivity.class);
                startActivity(accountAct);
            }
        });

        /**
         *if there is a user logged in, then navigate to HomeActivity
         */
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * This method sets up the widgets
     */
    private void setUpWidgets() {

        editTextEmail = (EditText) findViewById(R.id.etLoginEmail);
        editTextPassword = (EditText) findViewById(R.id.etLoginPassword);

        progressBar = (ProgressBar)findViewById(R.id.loginRequestProgressBar);
        progressBar.setVisibility(View.GONE);


    }


    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


}
