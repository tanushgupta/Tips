package com.symphonyfintech.tips.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.symphonyfintech.tips.R;

/**
 * Created by Tanush on 4/7/2017.
 */

public class AdvisersFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advisor, container, false);
    }
}
