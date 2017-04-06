package com.symphonyfintech.tips.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.symphonyfintech.tips.R;

/**
 * Created by Tanush on 4/3/2017.
 */

public class SignUpActivity extends AppCompatActivity implements OnClickListener {

    private Pattern pattern;
    private Matcher matcher;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.btn_sign_up).setOnClickListener(this);
        pattern = Pattern.compile(EMAIL_PATTERN);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.btn_sign_up)){
            final String email = ((EditText) findViewById(R.id.sign_up_email)).getText().toString();
            final String password = ((EditText)findViewById(R.id.sign_up_password)).getText().toString();
            final String confirm_password = ((EditText)findViewById(R.id.sign_up_confirm_password)).getText().toString();
            if(!email.equals("") && !password.equals("") && !confirm_password.equals("")){
                if(password.equals(confirm_password)){
                    matcher = pattern.matcher(email);
                    if(matcher.matches()){
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d("Sign Up: ", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getBaseContext(), R.string.sign_up_failed, Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getBaseContext(), R.string.sign_up_successful, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        // ...
                                    }
                                });
                    }
                    else{
                        ((EditText) findViewById(R.id.sign_up_email)).setError("Enter a valid Email address.");
                    }
                }
                else{
                    ((EditText)findViewById(R.id.sign_up_confirm_password)).setError("Same as password");
                }
            }
            else{
                Toast.makeText(getBaseContext(), R.string.empty_field, Toast.LENGTH_SHORT).show();
            }
            //Log.d("Email: ",email.getText().toString());
        }
    }
}
