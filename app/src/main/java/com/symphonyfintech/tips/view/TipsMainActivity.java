package com.symphonyfintech.tips.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.User;

/**
 * Created by Tanush on 3/29/2017.
 */

public class TipsMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frag_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips);

        frag_layout = (FrameLayout) findViewById(R.id.fragment_layout);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigate_Bottom_Bar);

        if(savedInstanceState != null){
            return;
        }
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
                            case R.id.menu_item_tips:
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                                fragmentTransaction.addToBackStack(null);
                                // Commit the transaction
                                fragmentTransaction.commit();
                                break;
                            case R.id.menu_item_order:
                                FragmentTransaction adfragmentTransaction = getFragmentManager().beginTransaction();
                                adfragmentTransaction.replace(R.id.fragment_layout, ordfragment);
                                adfragmentTransaction.addToBackStack(null);
                                // Commit the transaction
                                adfragmentTransaction.commit();
                                break;
                            case R.id.menu_item_advisor:
                                FragmentTransaction ordfragmentTransaction = getFragmentManager().beginTransaction();
                                ordfragmentTransaction.replace(R.id.fragment_layout, advfragment);
                                ordfragmentTransaction.addToBackStack(null);
                                // Commit the transaction
                                ordfragmentTransaction.commit();
                                break;
                        }
                        return true;
                    }
                });
        //Intent i = getIntent();
        //User user = (User)i.getSerializableExtra("User");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
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
