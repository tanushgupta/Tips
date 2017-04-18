package com.symphonyfintech.tips.view.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.dataAdapter.simpleDataFech;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.advisors.AdvisorList;
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
    private User userdetails;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private TipRowDetails tipRowDetails;
    private OrdersFragment ordfragment;
    private TipsListFragment tipsListFragment;
    private AdvisorList advisorList;
    private ExecuteTip executeTip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);

        //Get User Details
        userdetails =(User) getIntent().getSerializableExtra("User");
        Log.i("User Access Token: ", "****************** "+ userdetails.getAcessToken() + "************************");

        //OrdersSingleton Initialization
        //mainObjFire = mainObjFire.getInstance(userdetails.getUserName());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);// in on Create()

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigate_Bottom_Bar);

        if(savedInstanceState != null){
            return;
        }
        executeTip = new ExecuteTip();
        tipRowDetails = new TipRowDetails();
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
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout, tipRowDetails)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,executeTip)
                .commit();
        showhideFragments("TipsFragment");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_tips:
                                showhideFragments("TipsFragment");
                                break;
                            case R.id.menu_item_order:
                                if(userdetails.userType == User.AUTH_USER){
                                    showhideFragments("OrderFragment");
                                }
                                else{
                                    Toast.makeText(getBaseContext(),"Please Log In to see your Orders",Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.menu_item_advisor:
                                showhideFragments("AdviserFragment");
                                break;
                        }
                        return true;
                    }
                });

        new Thread(new simpleDataFech("")).start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showhideFragments("TipsFragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDetailTipFragment(TipBean tip){
        tipRowDetails.setUI(tip);
        showhideFragments("TipRowDetails");
    }

    public void openExecuteTipFragment(TipBean tip){
        executeTip.setUI(tip);
        showhideFragments("ExecuteTip");
    }

    private void showhideFragments(String fragment){
        getFragmentManager().beginTransaction().hide(advisorList).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        getFragmentManager().beginTransaction().hide(tipsListFragment).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        getFragmentManager().beginTransaction().hide(tipRowDetails).commit();
        getFragmentManager().beginTransaction().hide(executeTip).commit();
        switch(fragment){
            case "TipsFragment":
                getFragmentManager().beginTransaction().show(tipsListFragment).commit();
                break;
            case "OrderFragment":
                getFragmentManager().beginTransaction().show(ordfragment).commit();
                break;
            case "AdviserFragment":
                getFragmentManager().beginTransaction().show(advisorList).commit();
                break;
            case "TipRowDetails":
                getFragmentManager().beginTransaction().show(tipRowDetails).commit();
                break;
            case "ExecuteTip":
                getFragmentManager().beginTransaction().show(executeTip).commit();
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
