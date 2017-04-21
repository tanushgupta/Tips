package com.symphonyfintech.tips.view.advisors;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.ImageLoadTask;
import com.symphonyfintech.tips.adapters.advisorAdapter.AdvisorAdapter;
import com.symphonyfintech.tips.adapters.advisorAdapter.AdvisorTipsAdapter;
import com.symphonyfintech.tips.adapters.tipsAdapter.BaseRecyclerViewAdapter;
import com.symphonyfintech.tips.model.advisor.AdvisorBean;
import com.symphonyfintech.tips.model.tips.TipList;

import java.util.ArrayList;

public class Advisor extends AppCompatActivity {
    AdvisorBean selected ;
    ImageView profilepicImg;
    TextView show_adv_tipcount,show_adv_tipExecuted,show_adv_tip_description,tippostedBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor);

        selected = AdvisorAdapter.selectedTip;
        //Advisor.this.setTitle(selected.name);
        profilepicImg = (ImageView) findViewById(R.id.profilepicImg);
        ((TextView)findViewById(R.id.advisor_display_title)).setText(selected.name);
        show_adv_tipcount = (TextView)findViewById(R.id.show_adv_tipcount);
        show_adv_tipcount.setText(selected.tipCont);
        show_adv_tipExecuted = (TextView)findViewById(R.id.show_adv_tipExecuted);
        show_adv_tipExecuted.setText(selected.ExecutedTipCount);
        show_adv_tip_description = (TextView)findViewById(R.id.show_adv_tip_description);
        show_adv_tip_description.setText(selected.about);
        tippostedBy = (TextView)findViewById(R.id.tippostedBy);
        tippostedBy.setText("TIP POSTED BY "+selected.name.toUpperCase());
        Glide.with(getBaseContext()).load(selected.profileIcon).into(profilepicImg);
        //new ImageLoadTask(selected.profileIcon,profilepicImg).execute();
        initViews();
    }
    RecyclerView.Adapter adapter;
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.tipsbyUser);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new AdvisorTipsAdapter(selected.userId);
        //recyclerView.setAdapter(adapter);
        adapter = new BaseRecyclerViewAdapter(new ArrayList<TipList>(),selected.userId,1,getApplication());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),
                    new GestureDetector.SimpleOnGestureListener() {
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
