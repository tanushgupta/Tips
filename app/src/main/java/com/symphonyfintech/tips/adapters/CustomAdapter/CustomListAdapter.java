package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.Tip;
import com.symphonyfintech.tips.view.tips.TipsMainActivity;

import java.util.List;

/**
 * Created by Tanush on 4/5/2017.
 */

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {
    private List<Tip> mTip;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView symbol_name;
        public TextView order_side;
        public TextView tip_type;
        public TextView tip_live;
        public TextView tip_target;
        public TextView tip_stploss;
        public TextView tip_adv;
        public View mView;
        // Store the context for easy access
        public Context mContext;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            mContext = context;
            mView = itemView;
            symbol_name = (TextView) itemView.findViewById(R.id.txt_tip_name);
            order_side = (TextView) itemView.findViewById(R.id.txt_tip_side);
            tip_type = (TextView) itemView.findViewById(R.id.txt_status_tip);
            tip_live = (TextView) itemView.findViewById(R.id.txt_live);
            tip_target = (TextView) itemView.findViewById(R.id.txt_target);
            tip_stploss = (TextView) itemView.findViewById(R.id.txt_stplss);
            tip_adv = (TextView) itemView.findViewById(R.id.txt_post_adv);
        }
    }

    private Context mContext;

    // Pass in the contact array into the constructor
    public CustomListAdapter(Context context, List<Tip> tips) {
        Log.d("Trace: ", "Inside Adapter constructor");
        mTip = tips;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // Inflate the custom layout
        View contactView = LayoutInflater.from(context).inflate(R.layout.fragment_list_items, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(mContext, contactView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tip tip = mTip.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentJump(tip);
                //new DialogTipDetail(mContext,tip);
                ((TipsMainActivity) mContext).openDetailTipFragment(tip);
            }
        });
        // Set item views based on your views and data model
        ((TextView) holder.symbol_name).setText(tip.getSymbol());
        ((TextView) holder.order_side).setText(tip.getSide());
        ((TextView) holder.tip_type).setText("ACTIVE");
        ((TextView) holder.tip_live).setText(tip.getPrice());
        ((TextView) holder.tip_target).setText(tip.getTargetPrice());
        ((TextView) holder.tip_stploss).setText(tip.getStopLoss());
        ((TextView) holder.tip_adv).setText(tip.getTipSenderID());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTip.size();
    }
}