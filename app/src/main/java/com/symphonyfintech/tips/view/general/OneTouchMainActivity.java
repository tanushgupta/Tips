package com.symphonyfintech.tips.view.general;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.LogoutAdapter;
import com.symphonyfintech.tips.adapters.dataAdapter.simpleDataFech;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.advisors.AdvisorList;
import com.symphonyfintech.tips.view.orders.LockedOrders;
import com.symphonyfintech.tips.view.orders.OrdersFragment;
import com.symphonyfintech.tips.view.tips.ExecuteTip;
import com.symphonyfintech.tips.view.tips.TipRowDetails;
import com.symphonyfintech.tips.view.tips.TipsListFragment;

import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanush on 3/29/2017.
 */

public class OneTouchMainActivity extends AppCompatActivity {

    public static Map<Long ,Double> marketData = new HashMap<>();
    public static  ZMQ.Context ctx = ZMQ.context(1);
    //public static OrdersSingleton mainObjFire;
    public static User userdetails;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private OrdersFragment ordfragment;
    private TipsListFragment tipsListFragment;
    private AdvisorList advisorList;
    private Toolbar toolbar;
    private Thread bgData;
    public static boolean isLogin;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(userdetails.userType == User.GUEST_USER){
            menu.findItem(R.id.menu_log_out).setVisible(false);
            menu.findItem(R.id.menu_usrprofile).setVisible(false);
        }
        else{
            if(userdetails.userType == User.AUTH_USER){
                menu.findItem(R.id.menu_welcome).setVisible(false);
            }
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);

        isLogin = true;

        //Get User Details
        userdetails =(User) getIntent().getSerializableExtra("User");
        if(userdetails.userType == User.AUTH_USER)
        Log.i("User Access Token: ", "****************** "+ userdetails.getAcessToken() + "************************");

        //OrdersSingleton Initialization
        //mainObjFire = mainObjFire.getInstance(userdetails.getUserName());

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);// in on Create()
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigate_Bottom_Bar);

        if(savedInstanceState != null){
            return;
        }
        //executeTip = new ExecuteTip();
        //tipRowDetails = new TipRowDetails();

        ordfragment = new OrdersFragment();
        tipsListFragment = new TipsListFragment();
        advisorList = new AdvisorList();

        //android.app.FragmentManager fragmentManager = getFragmentManager();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout, tipsListFragment)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,advisorList)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,ordfragment)
                .commit();

        showhideFragments("FragmentTips");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_tips:
                                showhideFragments("FragmentTips");
                                break;
                            case R.id.menu_item_order:
                                if(userdetails.userType == User.GUEST_USER){
                                    Toast.makeText(getBaseContext(),"Please log In to see your Orders",Toast.LENGTH_SHORT).show();
                                }
                                showhideFragments("OrderFragment");
                                break;
                            case R.id.menu_item_advisor:
                                showhideFragments("AdviserFragment");
                                break;
                        }
                        return true;
                    }
                });

        bgData = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isLogin)
                new simpleDataFech("");
            }
        });
        bgData.start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                showhideFragments("FragmentTips");
                return true;*/
            case R.id.menu_close:
                DialogInterface.OnClickListener dialogexit= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(userdetails.userType == User.AUTH_USER){
                                    new LogoutAdapter(getBaseContext());
                                    isLogin = false;
                                    finish();
                                }
                                break;
                        }
                    }
                };

                AlertDialog.Builder builderexit = new AlertDialog.Builder(this);
                builderexit.setMessage("Are you sure you want to exit?").setPositiveButton("Yes", dialogexit)
                        .setNegativeButton("No", dialogexit).show();
                return true;

            case R.id.menu_log_out:
                DialogInterface.OnClickListener  dialoglogout = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new LogoutAdapter(getBaseContext());
                                Intent intent = new Intent(getBaseContext(),WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                isLogin = false;
                                getBaseContext().startActivity(intent);
                                finish();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builderlogout = new AlertDialog.Builder(this);
                builderlogout.setMessage("Log out. Are you sure?").setPositiveButton("Yes", dialoglogout)
                        .setNegativeButton("No", dialoglogout).show();
                return true;

            case R.id.menu_welcome:
                Intent intent = new Intent(getBaseContext(),WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                isLogin = false;
                getBaseContext().startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showhideFragments(String fragment){
        getFragmentManager().beginTransaction().hide(advisorList).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        getFragmentManager().beginTransaction().hide(tipsListFragment).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        switch(fragment){
            case "FragmentTips":
                getFragmentManager().beginTransaction().show(tipsListFragment).commit();
                break;
            case "OrderFragment":
                getFragmentManager().beginTransaction().show(ordfragment).commit();
                break;
            case "AdviserFragment":
                getFragmentManager().beginTransaction().show(advisorList).commit();
                break;
            default:
                getFragmentManager().beginTransaction().show(tipsListFragment).commit();
                break;
        }
    }

    public User getUser(){
        return userdetails;
    }

}
