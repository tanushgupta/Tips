package com.symphonyfintech.tips.view.general;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.AppConnectionStatus;
import com.symphonyfintech.tips.adapters.CustomAdapter.CustomSwipeAdapter;
import com.symphonyfintech.tips.adapters.CustomAdapter.LoginAdapter;
import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseFirebaseConnectSingletonAdapter;
import com.symphonyfintech.tips.model.user.User;

public class WelcomeActivity extends AppCompatActivity implements OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ViewPager viewPager;
    private ImageView[] dots;
    private LinearLayout dotLayout;

    private TextView main_header_text, secondary_header_text;

    private static int img_count;

    private void init(){
        dotLayout = (LinearLayout)findViewById(R.id.pager_dots);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        main_header_text = (TextView) findViewById(R.id.txtView_main_head);
        secondary_header_text = (TextView) findViewById(R.id.txtView_welcome_data);
        CustomSwipeAdapter cust_adap = new CustomSwipeAdapter(this);
        img_count = cust_adap.getCount();
        viewPager.setAdapter(cust_adap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        setUI();
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_guest_login).setOnClickListener(this);
        findViewById(R.id.btn_sign_up).setOnClickListener(this);
        viewPager.addOnPageChangeListener(viewListener);
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
    public void onClick(View v) {
        if(v == findViewById(R.id.btn_sign_in)){
            if (!AppConnectionStatus.getInstance(this).isOnline()) {
                Toast.makeText(getBaseContext(),"Not Connected to Internet",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(WelcomeActivity.this, LoginAdapter.class);
                WelcomeActivity.this.startActivity(intent);
            }
        }
        else if(v == findViewById(R.id.btn_guest_login)){
            if (!AppConnectionStatus.getInstance(this).isOnline()) {
                Toast.makeText(getBaseContext(),"Not Connected to Internet",Toast.LENGTH_SHORT).show();
            }
            else{
                anonymousLogin();
            }
        }
        else if(v == findViewById(R.id.btn_sign_up)){
            if (!AppConnectionStatus.getInstance(this).isOnline()) {
                Toast.makeText(getBaseContext(),"Not Connected to Internet",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                WelcomeActivity.this.startActivity(intent);
            }
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void setUI(){
        int dot_count = img_count;
        dots = new ImageView[dot_count];

        for (int i=0; i<dot_count;i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4,0,4,0);
            dotLayout.addView(dots[i],params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    private void addBottomDots(final int position) {
        int dot_count = img_count;//custom_swipe_adapter.getCount();
        for(int i = 0;i<dot_count;i++){
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        changeText(position);
    }

    private void changeText(int position){
        switch(position){
            case 0:
                main_header_text.setText("Welcome to 1touch");
                secondary_header_text.setText("Your new age one stop trading solution.");
                break;
            case 1:
                main_header_text.setText("Smart Tips from Advisors");
                secondary_header_text.setText("Receive Stock Tips from the best traders in India.");
                break;
            case 2:
                main_header_text.setText("One Touch Trading");
                secondary_header_text.setText("Execute these stock tips by a single touch.");
                break;
            default:
                main_header_text.setText("Please add the text accordingly");
                secondary_header_text.setText("Please add the text accordingly");
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
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

    private void anonymousLogin(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Guest Login", "signInAnonymously:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Guest Login Failed: ", "signInAnonymously", task.getException());
                            Toast.makeText(getBaseContext(), R.string.auth_login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User.getInstance();
                            //BaseFirebaseConnectSingletonAdapter.getInstance();
                            Intent intent = new Intent(WelcomeActivity.this, OneTouchMainActivity.class);
                            WelcomeActivity.this.startActivity(intent);
                            finish();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private class BackgroundActivity extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            return "Done";
        }
        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.i("Res: ","" + loginAdapter.getResult());
            Toast.makeText(getBaseContext(),"Login Done",Toast.LENGTH_SHORT).show();
                /*
                Intent intent = new Intent(WelcomeActivity.this, TipsListFragment.class);
                WelcomeActivity.this.startActivity(intent);
                finish();
                */
        }
    }
}