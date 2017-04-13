package com.symphonyfintech.tips.view.orders;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.orderAdapter.OrderAdapter;

public class Orders extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initViews();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

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

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this,R.color.dot_dark_3));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.white));
    }


    RecyclerView.Adapter adapter;

    private void initViews(){

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
