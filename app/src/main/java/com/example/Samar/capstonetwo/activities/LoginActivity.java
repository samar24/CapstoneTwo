package com.example.Samar.capstonetwo.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.Samar.capstonetwo.R;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;


import android.widget.Toast;





import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;





public class LoginActivity extends AppCompatActivity implements

        View.OnClickListener {




    private EditText mEmailField;

    private EditText mPasswordField;



    // [START declare_auth]

    private FirebaseAuth mAuth;

    // [END declare_auth]



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        mEmailField = (EditText) findViewById(R.id.gmail);

        mPasswordField = (EditText) findViewById(R.id.password);



        // Buttons

        findViewById(R.id.login).setOnClickListener(this);

        findViewById(R.id.SignUp).setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();


    }



    // [START on_start_check_user]

    @Override

    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);

    }

    // [END on_start_check_user]



    private void createAccount(String email, String password) {


        if (!validateForm()) {

            return;

        }


        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.


                            updateUI(null);

                        }


                    }

                });


    }



    private void signIn(String email, String password) {


        if (!validateForm()) {

            return;

        }


        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.



                            updateUI(null);

                        }



                        // [START_EXCLUDE]

                        if (!task.isSuccessful()) {


                        }


                        // [END_EXCLUDE]

                    }

                });

        // [END sign_in_with_email]

    }





    private boolean validateForm() {

        boolean valid = true;



        String email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(email)) {

            mEmailField.setError(getApplicationContext().getResources().getString(R.string.require));


            valid = false;

        } else {

            mEmailField.setError(null);

        }



        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(password)) {

            mPasswordField.setError(getApplicationContext().getResources().getString(R.string.require));

            valid = false;

        } else {

            mPasswordField.setError(null);

        }



        return valid;

    }



    private void updateUI(FirebaseUser user) {


        if (user != null) {



            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.welcome)+" "+user.getEmail().toString(),
                    Toast.LENGTH_SHORT).show();

        }


    }



    @Override

    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.SignUp) {

            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

        } else if (i == R.id.login) {

            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

        }
    }

}