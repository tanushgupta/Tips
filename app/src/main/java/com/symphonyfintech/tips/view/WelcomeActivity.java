package com.symphonyfintech.tips.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.View.OnClickListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomSwipeAdapter;
import com.symphonyfintech.tips.adapters.LoginAdapter;
import com.symphonyfintech.tips.model.User;
import static android.R.attr.value;

public class WelcomeActivity extends AppCompatActivity implements OnClickListener{

    private ViewPager viewPager;
    private ImageView[] dots;
    private LinearLayout dotLayout;
    private ProgressDialog progress;
    private TextView main_header_text, secondary_header_text;
    private AlertDialog dialog;
    private static int img_count;

    private void init(){
        //btnSignIn = (Button) findViewById(R.id.btn_sign_in);
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
        viewPager.addOnPageChangeListener(viewListener);
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.btn_sign_in)){
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WelcomeActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.activity_login, null);
            final EditText mEmail = (EditText) mView.findViewById(R.id.input_email);
            final EditText mPassword = (EditText) mView.findViewById(R.id.input_password);
            Button mLogin = (Button) mView.findViewById(R.id.btn_login);
            mLogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    if(email.equals("")){mEmail.setError("Enter an Email");}
                    else{
                        if(password.equals("")){mPassword.setError("Enter a password!");}
                        else{Login(email,password);}
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
            dialog = mBuilder.create();
            dialog.show();
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

    /*@UiThread
    private void UIThread(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                Log.d("UI thread", "To create image pane.");
            }
        });
    }*/

    private void Login(final String emailid, final String password){
        // Validate login
        Intent intent = new Intent(WelcomeActivity.this, TipsMainActivity.class);
        intent.putExtra("User", new User("tanush","tanush","tanush"));
        WelcomeActivity.this.startActivity(intent);
        //finish();
        //LoginAdapter login_adap = new LoginAdapter(emailid,password);
        //progress = ProgressDialog.show(getBaseContext(), "Validating", "Please Wait", true);
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final int res = login_adap.validateUser();
                progress.post(new Runnable() {
                    @Override
                    public void run() {
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
                        progress.setProgress(value);
                    }
                });
            }
        };
        new Thread(runnable).start();*/
    }

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
        if (dialog !=null && dialog.isShowing() ){
            dialog.cancel();
        }
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

        }
    }
}