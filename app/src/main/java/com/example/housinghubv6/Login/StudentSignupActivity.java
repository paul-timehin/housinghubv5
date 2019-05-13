package com.example.housinghubv6.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.housinghubv6.R;
import com.example.housinghubv6.Utils.FireBaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentSignupActivity extends AppCompatActivity {

    private static final String TAG = "StudentSignupactivity";

    private Context context = StudentSignupActivity.this;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextSurname;
    private EditText editTextUsername;


    private String email, firstname, lastname, phonenumber, password,username;


    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FireBaseMethods firebaseMethods;
    private Boolean isStudent;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        Log.d(TAG, "onCreate: started.");
        //get the intent that was passed
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                isStudent= false;
            } else {
                isStudent= extras.getBoolean("isStudent");
            }
        } else {
            isStudent= (Boolean) savedInstanceState.getSerializable("isStudent");
        }


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

                if(checkInputs(email, firstname,lastname, password,username)){
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewEmail(email, password, firstname,lastname,phonenumber,isStudent,username);

                    finish();
                }
            }
        });
    }


    private boolean checkInputs(String email, String firstname, String lastname, String password,String username){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || lastname.equals("") || password.equals("")|| firstname.equals("")||username.equals("")){
            Toast.makeText(this, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void setupWidgets() {
        Log.d(TAG, "setUpWidgets: Initializing Widgets.");

        // Initialize the progress bar
        progressBar = (ProgressBar) findViewById(R.id.signupstudent_progressBar);
        progressBar.setVisibility(View.GONE);

        // set all the text boxes and buttons on the page
        buttonRegister = (Button) findViewById(R.id.btVerifyYourAccountStudent);
        editTextEmail = (EditText) findViewById(R.id.etEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        editTextFirstName = findViewById(R.id.etFirstname);
        editTextSurname = findViewById(R.id.etSurname);
        editTextUsername = findViewById(R.id.studentUsername);

        //Sets background imageview to the background image within the drawable folder
        //ImageView background = findViewById(R.id.imStudentActivityBackground);
        //background.setImageResource(R.drawable.backgroundhouse);
    }


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check if the username is not already in use

                            //add new user to the database

                            //add user settings to the database
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
