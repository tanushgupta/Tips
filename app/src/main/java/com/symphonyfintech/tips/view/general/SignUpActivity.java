package com.symphonyfintech.tips.view.general;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.Mail;

/**
 * Created by Tanush on 4/3/2017.
 */

public class SignUpActivity extends AppCompatActivity implements OnClickListener {

    private Pattern pattern;
    private Matcher matcher;
    private ProgressDialog pDialog;

    private String subject;
    private String body;

    private Mail m;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.btn_sign_up).setOnClickListener(this);
        m = new Mail();
        String[] toArr = {"ajit.kumar@symphonyfintech.com"};
        m.set_to(toArr);
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    //Creating the mail with appropriate data.
    private String composeEmail() {
        m.set_subject(subject);
        m.set_body(body);
        try {
            if(m.send()) {
                return "Email was sent successfully.";
            } else {
                return "Email was not sent successfully.";
            }
        } catch(Exception e) {
            Log.i("MailApp", "Could not send email", e);
            return "Could not send email" + e;
        }
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.btn_sign_up)){
            Toast.makeText(this, "Sending mail... Please wait", Toast.LENGTH_SHORT).show();
            String consumer = "Analyst";
            if(((Switch)findViewById(R.id.switch_consumer)).isChecked()){
                consumer = "consumer";
            }
            subject = "New User sign up on 1Touch.";
            body = "Following are the details of a user who wants to connect to 1Touch"+
                    "name: " + ((EditText)findViewById(R.id.txt_name)).getText().toString() +"\n"+
                    "email: " + ((EditText)findViewById(R.id.sign_up_email)).getText().toString() +"\n"+
                    "phone_number: " + ((EditText)findViewById(R.id.user_phone_no)).getText().toString() +"\n"+
                    "broker: " + ((EditText)findViewById(R.id.user_broker)).getText().toString() +"\n"+
                    "client_account_id: " + ((EditText)findViewById(R.id.user_client_id)).getText().toString() +"\n"+
                    "user_type: " + consumer +"\n";
            new Connection().execute("");
        }
    }

    //Async thread to send mail to Ajit sir's email address for now.
    class Connection extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(SignUpActivity.this, "", "Attempting Sign up...", true);
        }

        protected String doInBackground(String... urls) {
            return composeEmail();
        }

        protected void onPostExecute(String param) {
            super.onPostExecute(param);
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
            Toast.makeText(SignUpActivity.this,""+ param,Toast.LENGTH_SHORT).show();
        }
    }
}

