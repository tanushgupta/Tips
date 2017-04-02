package com.symphonyfintech.tips.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symphonyfintech.tips.R;

/**
 * Created by Tanush on 3/30/2017.
 */

public class LoginAdapter extends AppCompatActivity{

    private String email;
    private String password;
    private int errorCode;

    /*

    public LoginAdapter(String email, String password){
        this.email = email;
        this.password = password;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Already Logged In", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("No User Signed In", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public int validateUser(){
        if(email.equals("tanush") && password.equals("tanush")){
            Log.d("Login: ", "Success");
            return 3;
        }
        else{
            if(!email.equals("tanush")){this.errorCode = 1;}
            else if(!password.equals("tanush")){this.errorCode = 2;}
            else{ this.errorCode = -1;}
            return invalidLogin();
        }
    }

    private int invalidLogin(){
        return errorCode;
    }

    private void signUpUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Sign Up: ", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), R.string.auth_signup_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void signInUser(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Login: ", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Login: ", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getBaseContext(), R.string.auth_login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    */
}
