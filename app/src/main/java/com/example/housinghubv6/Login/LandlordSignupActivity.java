package com.example.housinghubv6.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.housinghubv6.R;
import com.example.housinghubv6.Utils.FireBaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LandlordSignupActivity extends AppCompatActivity {

    private static final String TAG = "LandlordSignupActivity";

    private Context context = LandlordSignupActivity.this;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextSurname;
    private EditText editTextPhoneNumber;

    private String email, firstname, lastname, password;


    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FireBaseMethods firebaseMethods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        Log.d(TAG, "onCreate: started.");

        // Initializes the Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseMethods = new FireBaseMethods(context);
        setupWidgets();
        setupFirebaseAuth();

        init();
    }

    private void init(){

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                firstname = editTextFirstName.getText().toString();
                lastname = editTextSurname.getText().toString();
                password = editTextPassword.getText().toString();

                if(checkInputs(email, firstname,lastname, password)){
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewEmail(email, password, firstname,lastname);
                }
            }
        });

    }


    private boolean checkInputs(String email, String firstname, String lastname, String password){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || lastname.equals("") || password.equals("")|| firstname.equals("")){
            Toast.makeText(this, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void setupWidgets() {
        Log.d(TAG, "setUpWidgets: Initializing Widgets.");

        // Initialize the progress bar
        progressBar = (ProgressBar) findViewById(R.id.signuplandlord_progressBar);
        progressBar.setVisibility(View.GONE);

        // set all the text boxes and buttons on the page
        buttonRegister = (Button) findViewById(R.id.btLandlordVerifyAccount);
        editTextEmail = (EditText) findViewById(R.id.etEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        editTextFirstName = findViewById(R.id.etFirstname);
        editTextSurname = findViewById(R.id.etSurname);
        editTextPhoneNumber = findViewById(R.id.etPhoneNumber);


        //Sets background imageview to the background image within the drawable folder
        ImageView background = findViewById(R.id.imBackgroundLandlordActivity);
        background.setImageResource(R.drawable.backgroundhouse);

    }


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
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