package com.symphonyfintech.tips.view.tips;

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
import com.symphonyfintech.tips.adapters.FirebaseConnector.OrdersSingleton;
import com.symphonyfintech.tips.adapters.dataAdapter.simpleDataFech;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.advisors.AdvisorList;
import com.symphonyfintech.tips.view.general.HomeActivity;
import com.symphonyfintech.tips.view.orders.OrdersFragment;

import org.zeromq.ZMQ;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanush on 3/29/2017.
 */

public class TipsMainActivity extends AppCompatActivity {

    public static Map<Long ,Double> marketData = new HashMap<>();
    public static  ZMQ.Context ctx = ZMQ.context(1);
    //public static OrdersSingleton mainObjFire;
    private User userdetails;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private TipDetailFragment tipDetailFragment;
    private OrdersFragment ordfragment;
    private HomeActivity homeActivity;
    private AdvisorList advisorList;
    private TipRow executeTip;

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
        executeTip = new TipRow();
        tipDetailFragment = new TipDetailFragment();
        ordfragment = new OrdersFragment();
        homeActivity = new HomeActivity();
        advisorList = new AdvisorList();

        //android.app.FragmentManager fragmentManager = getFragmentManager();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,homeActivity)
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
                .add(R.id.fragment_layout,tipDetailFragment)
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

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String uid = user.getUid();
            Log.d("Username: ", uid);
            if(email != null){
                Log.d("EmailID: ", email);
            }
        }*/

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
        tipDetailFragment.setUI(tip);
        showhideFragments("TipDetailFragment");
    }

    public void openExecuteTipFragment(TipBean tip){
        executeTip.setUI(tip);
        showhideFragments("ExecuteTip");
    }

    private void showhideFragments(String fragment){
        getFragmentManager().beginTransaction().hide(advisorList).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        getFragmentManager().beginTransaction().hide(homeActivity).commit();
        getFragmentManager().beginTransaction().hide(ordfragment).commit();
        getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
        getFragmentManager().beginTransaction().hide(executeTip).commit();
        switch(fragment){
            case "TipsFragment":
                getFragmentManager().beginTransaction().show(homeActivity).commit();
                break;
            case "OrderFragment":
                getFragmentManager().beginTransaction().show(ordfragment).commit();
                break;
            case "AdviserFragment":
                getFragmentManager().beginTransaction().show(advisorList).commit();
                break;
            case "TipDetailFragment":
                getFragmentManager().beginTransaction().show(tipDetailFragment).commit();
                break;
            case "ExecuteTip":
                getFragmentManager().beginTransaction().show(executeTip).commit();
                break;
            default:
                getFragmentManager().beginTransaction().show(homeActivity).commit();
                break;
        }
    }

    public User getUser(){
        return userdetails;
    }
}
