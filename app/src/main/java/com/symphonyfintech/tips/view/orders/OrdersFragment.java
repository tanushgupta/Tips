package com.symphonyfintech.tips.view.orders;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.CustomOrderPager;
import com.symphonyfintech.tips.adapters.orderAdapter.OrderAdapter;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

/**
 * Created by Tanush on 4/7/2017.
 */

public class OrdersFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        initViews();

        return view;
    }

    private void initViews(){
        if(User.getInstance().AuthType == User.GUEST_USER){
            view.setEnabled(false);
            view.setAlpha(.5f);
        }
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        final TabLayout.Tab tab_pending = tabLayout.newTab();
        final TabLayout.Tab tab_open = tabLayout.newTab();
        final TabLayout.Tab tab_close = tabLayout.newTab();
        final TabLayout.Tab tab_failed = tabLayout.newTab();

        tab_pending.setText("PENDING");
        tab_open.setText("OPEN");
        tab_close.setText("CLOSED");
        tab_failed.setText("FAILED");

        tabLayout.addTab(tab_pending,0);
        tabLayout.addTab(tab_open,1);
        tabLayout.addTab(tab_close,2);
        tabLayout.addTab(tab_failed,3);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity(),R.color.dot_dark_3));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(),R.color.white));
    }
}
