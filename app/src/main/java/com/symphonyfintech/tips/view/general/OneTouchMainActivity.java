package com.symphonyfintech.tips.view.general;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.LogoutAdapter;
import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseFirebaseConnectSingletonAdapter;
import com.symphonyfintech.tips.adapters.dataAdapter.simpleDataFech;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.advisors.AdvisorList;
import com.symphonyfintech.tips.view.orders.OrdersFragment;
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
    private User userdetails;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private OrdersFragment ordfragment;
    private TipsListFragment tipsListFragment;
    private AdvisorList advisorList;
    private Toolbar toolbar;
    private Thread bgData;
    public static boolean isLogin;
    static boolean isPersistenceEnabled =false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Get User Details
        userdetails =User.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);
        isLogin = true;
        if(isPersistenceEnabled=false) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled=true;
        }
        ActionBar actionBar = getSupportActionBar();

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigate_Bottom_Bar);

        if(savedInstanceState != null){
            return;
        }

        ordfragment = new OrdersFragment();
        tipsListFragment = new TipsListFragment();
        advisorList = new AdvisorList();

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
                                if(userdetails.AuthType == User.GUEST_USER){
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(userdetails == null){
            userdetails =User.getInstance();
        }

        if(userdetails.AuthType == User.GUEST_USER){
            menu.findItem(R.id.menu_log_out).setVisible(false);
            menu.findItem(R.id.menu_usrprofile).setVisible(false);
            menu.findItem(R.id.menu_add_tip).setVisible(false);
        }
        else{
            if(userdetails.AuthType == User.AUTH_USER){
                menu.findItem(R.id.menu_welcome).setVisible(false);
                if(userdetails.userType == User.CONSUMER){
                    menu.findItem(R.id.menu_add_tip).setVisible(false);
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close:
                DialogInterface.OnClickListener dialogexit= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(userdetails.AuthType == User.AUTH_USER){
                                    new LogoutAdapter(getBaseContext());
                                }
                                isLogin = false;
                                userdetails.destroyUser();
                                finish();
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
                                userdetails.destroyUser();
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
                builderlogout.setMessage("Are you sure?").setPositiveButton("Logout", dialoglogout)
                        .setNegativeButton("Cancel", dialoglogout).show();
                return true;

            case R.id.menu_welcome:
                userdetails.destroyUser();
                Intent intent = new Intent(getBaseContext(),WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                isLogin = false;
                getBaseContext().startActivity(intent);
                finish();
                return true;

            case R.id.menu_add_tip:
                Toast.makeText(this,"Add tip work under progress.",Toast.LENGTH_SHORT).show();
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
