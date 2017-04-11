package com.symphonyfintech.tips.view.tips;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.CustomListAdapter;
import com.symphonyfintech.tips.model.tips.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanush on 4/7/2017.
 */

public class TipsFragment extends Fragment implements ValueEventListener {
    private RecyclerView mRecyclerView;
    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_tips);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ref = FirebaseDatabase.getInstance().getReference("Tips");
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(this);
        return view;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Tip> tips = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            Tip tip = child.getValue(Tip.class);
            tips.add(tip);
        }
        mRecyclerView.setAdapter(new CustomListAdapter(getActivity(),tips));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }
}
