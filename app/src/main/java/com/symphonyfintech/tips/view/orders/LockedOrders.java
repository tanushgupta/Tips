package com.symphonyfintech.tips.view.orders;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.symphonyfintech.tips.R;

/**
 * Created by Tanush on 4/20/2017.
 */

public class LockedOrders extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.locked_orders_page, container, false);
    }
}
