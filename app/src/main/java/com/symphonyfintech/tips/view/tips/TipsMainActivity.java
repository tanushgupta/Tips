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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.Tip;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.view.advisors.AdvisersFragment;
import com.symphonyfintech.tips.view.advisors.AdvisorList;
import com.symphonyfintech.tips.view.general.HomeActivity;
import com.symphonyfintech.tips.view.orders.OrdersFragment;

/**
 * Created by Tanush on 3/29/2017.
 */

public class TipsMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private TipRow tipDetailFragment;
    private OrdersFragment ordfragment;
    private HomeActivity homeActivity;
    private AdvisorList advisorList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);// in on Create()

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigate_Bottom_Bar);

        if(savedInstanceState != null){
            return;
        }

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
                                showhideFragments("OrderFragment");
                                break;
                            case R.id.menu_item_advisor:
                                showhideFragments("AdviserFragment");
                                break;
                        }
                        return true;
                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String uid = user.getUid();
            Log.d("Username: ", uid);
            if(email != null){
                Log.d("EmailID: ", email);
            }
        }
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

    public void openDetailTipFragment(){
        tipDetailFragment = new TipRow();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,tipDetailFragment)
                .commit();
        showhideFragments("TipDetailFragment");
    }

    private void showhideFragments(String fragment){
        switch(fragment){
            case "TipsFragment":
                getFragmentManager().beginTransaction().hide(advisorList).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().show(homeActivity).commit();
                break;
            case "OrderFragment":
                getFragmentManager().beginTransaction().hide(advisorList).commit();
                getFragmentManager().beginTransaction().show(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(homeActivity).commit();
                break;
            case "AdviserFragment":
                getFragmentManager().beginTransaction().show(advisorList).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(homeActivity).commit();
                break;
            case "TipDetailFragment":
                getFragmentManager().beginTransaction().show(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advisorList).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(homeActivity).commit();
                break;
            default:
                getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advisorList).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().show(homeActivity).commit();
                break;
        }
    }
}
