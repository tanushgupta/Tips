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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomListAdapter;
import com.symphonyfintech.tips.model.Tip;
import com.symphonyfintech.tips.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private List<Tip> tips;
        private FirebaseDatabase database;
        private DatabaseReference ref;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tips, container, false);
            // Inflate the layout for this fragment
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_tips);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            //mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Test: ","-----------TEST");
                    Toast.makeText(getActivity(),"Testingggg.....",Toast.LENGTH_LONG).show();
                }
            });
            tips = new ArrayList<>();
            database = FirebaseDatabase.getInstance();
            ref = database.getReference("Tips");
            // Attach a listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //String value = dataSnapshot.getChildren();
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    //Tip tip = dataSnapshot.getValue(Tip.class);
                    //tips.add(tip);
                    //Toast.makeText(getActivity(),dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                    //Log.d("Database: ", dataSnapshot.toString());
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Tip tip = child.getValue(Tip.class);
                        tips.add(tip);
                        //Log.d("Value: ", "------------------------------"+tips.size());
                    }
                    mAdapter = new CustomListAdapter(getActivity(),tips);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
            //String[] mydata = {"test","test1","test2","test3","test4"};
            //tips = new Tip().createTipsList(10);
            // specify an adapter (see also next example)
            Log.d("Tips: ", "----------------------"+ tips.size());
            return view;
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
