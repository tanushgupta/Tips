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
import com.symphonyfintech.tips.view.advisors.AdvisersFragment;
import com.symphonyfintech.tips.view.orders.OrdersFragment;

/**
 * Created by Tanush on 3/29/2017.
 */

public class TipsMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    private TipDetailFragment tipDetailFragment;
    private TipsFragment tipsfragment;
    private OrdersFragment ordfragment;
    private AdvisersFragment advfragment;

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

        advfragment = new AdvisersFragment();
        ordfragment = new OrdersFragment();
        tipsfragment = new TipsFragment();
        tipDetailFragment = new TipDetailFragment();

        //android.app.FragmentManager fragmentManager = getFragmentManager();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,tipsfragment)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,advfragment)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,ordfragment)
                .commit();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout,tipDetailFragment)
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

    public void openDetailTipFragment(Tip tip){
        tipDetailFragment.setUI(tip);
        showhideFragments("TipDetailFragment");
    }

    private void showhideFragments(String fragment){
        switch(fragment){
            case "TipsFragment":
                getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advfragment).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().show(tipsfragment).commit();
                break;
            case "OrderFragment":
                getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advfragment).commit();
                getFragmentManager().beginTransaction().show(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(tipsfragment).commit();
                break;
            case "AdviserFragment":
                getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().show(advfragment).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(tipsfragment).commit();
                break;
            case "TipDetailFragment":
                getFragmentManager().beginTransaction().show(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advfragment).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().hide(tipsfragment).commit();
                break;
            default:
                getFragmentManager().beginTransaction().hide(tipDetailFragment).commit();
                getFragmentManager().beginTransaction().hide(advfragment).commit();
                getFragmentManager().beginTransaction().hide(ordfragment).commit();
                getFragmentManager().beginTransaction().show(tipsfragment).commit();
                break;
        }
    }
}
