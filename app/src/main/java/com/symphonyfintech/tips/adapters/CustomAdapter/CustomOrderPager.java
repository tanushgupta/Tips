package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.orderAdapter.OrderAdapter;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

/**
 * Created by Tanush on 4/17/2017.
 */

public class CustomOrderPager extends PagerAdapter{
    private Context ctx;
    private LayoutInflater layoutInflater;
    private RecyclerView recycleView;

    public CustomOrderPager(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.order_tab_pages,container,false);
        recycleView = (RecyclerView) item_view.findViewById(R.id.order_pager_recycle);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(ctx));

        RecyclerView.Adapter adapter = new OrderAdapter(((OneTouchMainActivity)ctx).getUser().getUserName());
        recycleView.setAdapter(adapter);

        recycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
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
        //ImageView imageview = (ImageView)item_view.findViewById(R.id.image_view);
        //imageview.setImageResource(image_res[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
