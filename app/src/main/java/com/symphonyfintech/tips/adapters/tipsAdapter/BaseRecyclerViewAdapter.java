package com.symphonyfintech.tips.adapters.tipsAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.HeaderItem;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.tips.TipItem;
import com.symphonyfintech.tips.model.tips.TipList;
import com.symphonyfintech.tips.view.general.HomeActivity;
import com.symphonyfintech.tips.view.general.ScrollingActivity;
import com.symphonyfintech.tips.view.tips.TipRow;
import com.symphonyfintech.tips.view.tips.TipsMainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tanush on 4/14/2017.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int CONTEXT_TIPS = 0;
    private final int CONTEXT_ADVISOR = 1;
    private int curr_page;
    DatabaseReference tipsFirebaseRef;
    final Handler myHandler;
    boolean firebaseUpdateWorking=false;
    public static TipBean delectedTip;
    private boolean monthly,today;
    private String userID;
    private Context mContext;

    @NonNull
    private List<TipList> items = Collections.emptyList();

    public BaseRecyclerViewAdapter(@NonNull List<TipList> items, String userID, int curr_page, Context mContext){
        this.mContext = mContext;
        this.items = items;
        this.curr_page = curr_page;
        if(curr_page == CONTEXT_ADVISOR){
            this.userID = userID;
        }
        monthly = false;
        today = false;
        myHandler = new Handler();
        tipsFirebaseRef = FirebaseDatabase.getInstance().getReference("Tips/");
        getListofTipsfromFirebase();
        startTimer();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = (TextView) itemView.findViewById(R.id.txt_header);
        }

    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Symbol,txt_price,txt_side_at,txt_target_Price,txt_live_price,txt_stopLoss,analyst_name;
        //Button btn_exe_tip;
        public ItemViewHolder(View view) {
            super(view);
            Log.i("Count: ", "------------------------------------- Inside Item Constructor -----------------------------------");
            txt_Symbol = (TextView)view.findViewById(R.id.txt_Symbol);
            txt_price = (TextView)view.findViewById(R.id.txt_price);
            txt_side_at = (TextView)view.findViewById(R.id.txt_side_at);
            txt_target_Price = (TextView)view.findViewById(R.id.txt_target_Price);
            txt_live_price= (TextView)view.findViewById(R.id.txt_live_price);
            txt_stopLoss = (TextView)view.findViewById(R.id.txt_stopLoss);
            //btn_exe_tip = (Button) view.findViewById(R.id.btn_exe_tip);
            analyst_name= (TextView)view.findViewById(R.id.analyst_name);        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Count: ", "------------------------------------- " + viewType + "-----------------------------------");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TipList.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.tips_list_item_header, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case TipList.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.fragment_list_items, parent, false);
                return new ItemViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i("Count: ", "------------------------------------- " + position + "-----------------------------------");
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TipList.TYPE_HEADER: {
                HeaderItem header = (HeaderItem)items.get(position);
                HeaderViewHolder viewhold = (HeaderViewHolder) holder;
                // your logic here
                viewhold.txt_header.setText(header.getHeader());
                break;
            }
            case TipList.TYPE_EVENT: {
                final TipItem tip = (TipItem) items.get(position);
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                createItemRow(viewHolder,tip.getTip());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Working on the execute page.",Toast.LENGTH_SHORT).show();
                        ((TipsMainActivity) mContext).openDetailTipFragment(tip.getTip());
                    }
                });
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public Timer timer ;

    protected  void startTimer() {
        timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                if(firebaseUpdateWorking)
                    return;

                for(TipList tiptype :items){
                    if(tiptype.getType() == TipList.TYPE_EVENT){
                        TipBean tip = ((TipItem)tiptype).getTip();
                        Long key= Long.parseLong(tip.instrumentID);
                        Log.i("Live Price: ","===================================== "+ TipsMainActivity.marketData.get(key) + "===============================");
                        if(TipsMainActivity.marketData.containsKey(key)){
                            tip.livePrice =  TipsMainActivity.marketData.get(key)/100;
                            createThread();
                        }
                    }
                }
            }
        }, 0, 5000);
    }
    public void createThread(){
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                //notifyItemInserted(items.size()==0 ? 0 : items.size()-1);
                Log.i("Count: ","===================================== "+ items.size() + "===============================");
                notifyDataSetChanged();
                //notifyItemInserted(items.size() - 1);
            }
        });
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    private void createItemRow(final ItemViewHolder viewHolder, TipBean tip){
        viewHolder.txt_Symbol.setText(tip.symbol);
        viewHolder.txt_price.setText("₹" + tip.price);
        viewHolder.txt_side_at.setText(tip.side + " At ");
        viewHolder.txt_target_Price.setText("₹" + tip.targetPrice);
        String priceVal1 = viewHolder.txt_live_price.getText().toString().replace("Rs.", "").replace("₹", "").trim();
        String priceVal2 = tip.livePrice == null ? null : tip.livePrice + "";
        try {
            if (priceVal1 != null && priceVal2 != null && !priceVal2.equals("null")) {
                if (Double.parseDouble(priceVal2) < Double.parseDouble(priceVal1)) {
                    viewHolder.txt_live_price.setTextColor(Color.RED);
                } else if (Double.parseDouble(priceVal2) > Double.parseDouble(priceVal1)) {
                    viewHolder.txt_live_price.setTextColor(Color.rgb(0, 100, 0));
                }
            }
        } catch (Exception e) {
        }
        viewHolder.txt_live_price.setText("₹" + tip.livePrice);

        if (viewHolder.txt_live_price.getText().toString().contains("null")) {
            viewHolder.txt_live_price.setText("NA");
        }
        viewHolder.txt_stopLoss.setText("₹" + tip.stopLoss);
        if (tip.AnalystName == null) {
            DatabaseReference advisor = FirebaseDatabase.getInstance().getReference("Analyst/" + tip.tipSenderID + "/name");
//        handler.postDelayed(new SimpleTread(),30L);

            advisor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String advisorName = (String) dataSnapshot.getValue();
                    viewHolder.analyst_name.setText(advisorName);
                    //tip.AnalystName = advisorName;
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("DATA", "Failed to read value.", error.toException());
                }
            });
        } else {
            viewHolder.analyst_name.setText(tip.AnalystName);
        }
    }

    private void getListofTipsfromFirebase(){
        tipsFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUpdateWorking = true;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.i("", "**************** SnapShot" + dataSnapshot.toString());
                Object dtata = dataSnapshot.getValue();
                HashMap<String, HashMap<String, Object>> value1 =
                        (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

                if(items.size()>0 && value1 != null){
                    items.clear();
                    monthly = false;
                    today = false;
                    //notifyDataSetChanged();
                }

                for (HashMap.Entry<?, ?> entry2 : value1.entrySet()) {
                    //print keys and values
                    TipBean tipbean = new TipBean();
                    for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {
//                        System.out.println("**********\t " + entry.getKey() + " : " + entry.getValue());
                        if (entry.getKey().equals("stopLoss"))
                            tipbean.stopLoss = entry.getValue().toString();
                        else if (entry.getKey().equals("tipId"))
                            tipbean.tipId = entry.getValue().toString();
                        else if (entry.getKey().equals("productType"))
                            tipbean.productType = entry.getValue().toString();
                        else if (entry.getKey().equals("triggerPrice"))
                            tipbean.triggerPrice = entry.getValue().toString();
                        else if (entry.getKey().equals("targetPrice"))
                            tipbean.targetPrice = entry.getValue().toString();
                        else if (entry.getKey().equals("tipCreatedAtTime"))
                            tipbean.tipCreatedAtTime = entry.getValue().toString();
                        else if (entry.getKey().equals("instrumentID"))
                            tipbean.instrumentID = entry.getValue().toString();
                        else if (entry.getKey().equals("orderQuantity"))
                            tipbean.orderQuantity = entry.getValue().toString();
                        else if (entry.getKey().equals("tipExpiry"))
                            tipbean.tipExpiry = entry.getValue().toString();
                        else if (entry.getKey().equals("side"))
                            tipbean.side = entry.getValue().toString();
                        else if (entry.getKey().equals("symbol"))
                            tipbean.symbol = entry.getValue().toString();
                        else if (entry.getKey().equals("tipSenderID"))
                            tipbean.tipSenderID = entry.getValue().toString();
                        else if (entry.getKey().equals("description"))
                            tipbean.description = entry.getValue().toString();
                        else if (entry.getKey().equals("price"))
                            tipbean.price = entry.getValue().toString();
                    }
//                    System.out.println(" \n\n\n new Entry ");
                    tipbean.fetchDataForThisTip();
                    if(curr_page == CONTEXT_ADVISOR && userID.equals(tipbean.tipSenderID)){
                        updateItems(tipbean);
                    }
                    else{
                        if(curr_page == CONTEXT_TIPS){
                            updateItems(tipbean);
                        }
                    }
                    //items.add(item);
                    //notifyItemInserted(items.size() == 0 ? 0 : items.size() - 1);
                    //notifyDataSetChanged();
                    //Collections.sort(tipList);
                    firebaseUpdateWorking = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DATA", "Failed to read value.", error.toException());
            }
        });
    }

    private void updateItems(TipBean tip){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tipByDates = "";
        String todaysDate = sdf.format(new Date());
        if (tip.tipCreatedAtTime.contains(" ")) {
            tipByDates = tip.tipCreatedAtTime.substring(0, tip.tipCreatedAtTime.indexOf(" "));
            Log.i("TipDate: ", "****************************************** " + tipByDates);
            Log.i("Todays date: ", "****************************************** " + todaysDate);
        }
        if(tipByDates == todaysDate){
            if(!today){
                items.add(new HeaderItem("Today's Tips"));
                today = true;
            }
        }
        else{
            if(!monthly){
                items.add(new HeaderItem("This Month"));
                monthly = true;
            }
        }
        items.add(new TipItem(tip));

        notifyItemInserted(items.size() - 1);
        //notifyDataSetChanged();
    }

    public void updateRows(HashMap<String, HashMap<String, Object>> objTips){
        firebaseUpdateWorking = true;
        if(items.size()>0 && objTips != null){
            items.clear();
            monthly = false;
            today = false;
            //notifyDataSetChanged();
        }

        for (HashMap.Entry<?, ?> entry2 : objTips.entrySet()) {
            //print keys and values
            TipBean tipbean = new TipBean();
            for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {
//                        System.out.println("**********\t " + entry.getKey() + " : " + entry.getValue());
                if (entry.getKey().equals("stopLoss"))
                    tipbean.stopLoss = entry.getValue().toString();
                else if (entry.getKey().equals("tipId"))
                    tipbean.tipId = entry.getValue().toString();
                else if (entry.getKey().equals("productType"))
                    tipbean.productType = entry.getValue().toString();
                else if (entry.getKey().equals("triggerPrice"))
                    tipbean.triggerPrice = entry.getValue().toString();
                else if (entry.getKey().equals("targetPrice"))
                    tipbean.targetPrice = entry.getValue().toString();
                else if (entry.getKey().equals("tipCreatedAtTime"))
                    tipbean.tipCreatedAtTime = entry.getValue().toString();
                else if (entry.getKey().equals("instrumentID"))
                    tipbean.instrumentID = entry.getValue().toString();
                else if (entry.getKey().equals("orderQuantity"))
                    tipbean.orderQuantity = entry.getValue().toString();
                else if (entry.getKey().equals("tipExpiry"))
                    tipbean.tipExpiry = entry.getValue().toString();
                else if (entry.getKey().equals("side"))
                    tipbean.side = entry.getValue().toString();
                else if (entry.getKey().equals("symbol"))
                    tipbean.symbol = entry.getValue().toString();
                else if (entry.getKey().equals("tipSenderID"))
                    tipbean.tipSenderID = entry.getValue().toString();
                else if (entry.getKey().equals("description"))
                    tipbean.description = entry.getValue().toString();
                else if (entry.getKey().equals("price"))
                    tipbean.price = entry.getValue().toString();
            }
//                    System.out.println(" \n\n\n new Entry ");
            tipbean.fetchDataForThisTip();
            if(curr_page == CONTEXT_ADVISOR && userID.equals(tipbean.tipSenderID)){
                updateItems(tipbean);
            }
            else{
                if(curr_page == CONTEXT_TIPS){
                    updateItems(tipbean);
                }
            }
            //items.add(item);
            //notifyItemInserted(items.size() == 0 ? 0 : items.size() - 1);
            //notifyDataSetChanged();
            //Collections.sort(tipList);
            firebaseUpdateWorking = false;
        }
    }
}
