package com.symphonyfintech.tips.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.User;

/**
 * Created by Tanush on 3/29/2017.
 */

public class TipsMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;
    //private FragmentManager fragmentManager;
    //private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navBottomBar);

        if(savedInstanceState != null){
            return;
        }

        //final CreateFragment fragment = new CreateFragment();
        //getSupportFragmentManager().beginTransaction().add(frag_layout,fragment).commit();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final AdvisersFragment advfragment = new AdvisersFragment();
        final OrdersFragment ordfragment = new OrdersFragment();
        final TipsFragment fragment = new TipsFragment();
        fragmentTransaction.add(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tips_Menu:
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack so the user can navigate back
                                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                                fragmentTransaction.addToBackStack(null);

                                // Commit the transaction
                                fragmentTransaction.commit();
                                break;
                            case R.id.order_Menu:
                                FragmentTransaction adfragmentTransaction = getFragmentManager().beginTransaction();
                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack so the user can navigate back
                                adfragmentTransaction.replace(R.id.fragment_layout, ordfragment);
                                adfragmentTransaction.addToBackStack(null);
                                // Commit the transaction
                                adfragmentTransaction.commit();
                                break;
                            case R.id.adviser_Menu:
                                FragmentTransaction ordfragmentTransaction = getFragmentManager().beginTransaction();
                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack so the user can navigate back
                                ordfragmentTransaction.replace(R.id.fragment_layout, advfragment);
                                ordfragmentTransaction.addToBackStack(null);
                                // Commit the transaction
                                ordfragmentTransaction.commit();
                                break;
                        }
                        return true;
                    }
                });
        Intent i = getIntent();
        User user = (User)i.getSerializableExtra("User");

        Log.d("Username: ", user.getUsername());
        Log.d("EmailID: ", user.getEmailid());
        Log.d("Password: ", user.getPassword());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public static class TipsFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_tips, container, false);
        }
    }

    public static class AdvisersFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_advisor, container, false);
        }
    }

    public static class OrdersFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_orders, container, false);
        }
    }
}
