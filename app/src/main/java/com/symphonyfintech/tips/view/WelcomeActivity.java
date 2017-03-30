package com.symphonyfintech.tips.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomSwipeAdapter;
import com.symphonyfintech.tips.adapters.LoginAdapter;
import com.symphonyfintech.tips.model.User;

import static android.R.attr.value;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CustomSwipeAdapter custom_swipe_adapter;
    private ImageView[] dots;
    private LinearLayout dotLayout;
    private Button btnSignIn;
    private ProgressDialog progress;
    private TextView main_header_text, secondary_header_text;
    private LoginAdapter login_adap;
    private User user;
    private Intent intent;

    private void init(){
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        dotLayout = (LinearLayout)findViewById(R.id.pager_dots);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        main_header_text = (TextView) findViewById(R.id.txtView_main_head);
        secondary_header_text = (TextView) findViewById(R.id.txtView_welcome_data);
        custom_swipe_adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(custom_swipe_adapter);
    }

    @UiThread
    private void UIThread(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                viewPager.addOnPageChangeListener(viewListener);
                setUI();
                Log.d("UI thread", "To create image pane.");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        UIThread();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WelcomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_login, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.input_email);
                final EditText mPassword = (EditText) mView.findViewById(R.id.input_password);
                Button mLogin = (Button) mView.findViewById(R.id.btn_login);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = mEmail.getText().toString();
                        String password = mPassword.getText().toString();
                        if(email.equals("")){
                            mEmail.setError("Enter an Email");
                        }
                        else{
                            if(password.equals("")){
                                mPassword.setError("Enter a password!");
                            }
                            else{
                                /*
                                    -1 - Error
                                    1 - Error in email address
                                    2 - Error in password
                                    3 - login successful
                                */
                                int res = validateLogin(email,password);
                                switch (res){
                                    case 1:
                                        mEmail.setError("Incorrect Email");
                                        break;
                                    case 2:
                                        mPassword.setError("Invalid password!");
                                        break;
                                    case 3:
                                        Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                        user = new User("tanush1122","tanush@gmail.com","Tanush");
                                        intent = new Intent(WelcomeActivity.this, TipsMainActivity.class);
                                        intent.putExtra("User", user);
                                        WelcomeActivity.this.startActivity(intent);
                                        finish();
                                        break;
                                    default:
                                        Toast.makeText(getBaseContext(),"Login unsuccessful, Please try again",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    private int validateLogin(final String emailid, final String password){
        // Validate login
        login_adap = new LoginAdapter(emailid,password);
        int res = login_adap.validateUser();
        /*progress = ProgressDialog.show(this, "Validating",
                "Please Wait", true);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    if(!emailid.equals("tanush@gmail.com")){
                    }
                    else{
                        if(!password.equals("tanush")){

                        }
                    }
                    newActivity();
                }
                catch (Exception ex){
                    Log.d("Exception ","caught while validating login: " +ex);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        progress.dismiss();
                    }
                });
            }
        }).start();*/
        return res;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setUI(){
        int dot_count = custom_swipe_adapter.getCount();
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
        int dot_count = custom_swipe_adapter.getCount();
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
}
