package com.symphonyfintech.tips.adapters.tipsAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.view.general.HomeActivity;
import com.symphonyfintech.tips.view.general.ScrollingActivity;
import com.symphonyfintech.tips.view.tips.TipRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    DatabaseReference tipsFirebaseRef;
   public static TipBean delectedTip;
    boolean firebaseUpdateWorking=false;
    private List<TipBean> tipList ;
    final Handler myHandler;
    public TipAdapter() {
        myHandler = new Handler();
        tipList = new ArrayList<>();
        tipsFirebaseRef = FirebaseDatabase.getInstance().getReference("Tips/");
//        handler.postDelayed(new SimpleTread(),30L);

        tipsFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUpdateWorking=true;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.i("", "**************** SnapShot" + dataSnapshot.toString());


                Object dtata =  dataSnapshot.getValue();
                HashMap<String, HashMap<String, Object>> value1 =
                        (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                        if(value1!=null){
                            tipList = new ArrayList<TipBean>();
                        }
                for (HashMap.Entry<?, ?> entry2 : value1.entrySet()) {
                    //print keys and values
                    TipBean tipbean = new TipBean();
                    for (HashMap.Entry<?, ?> entry :( (HashMap<String,Object>)entry2.getValue()).entrySet()) {
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
                    tipList.add(tipbean);
                }
                notifyItemInserted(tipList.size()==0 ? 0 : tipList.size()-1);
                notifyDataSetChanged();
//                Collections.sort(tipList);
                firebaseUpdateWorking=false;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DATA", "Failed to read value.", error.toException());
            }
        });
        startTimer();
    }
 
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_tip, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.txt_Symbol.setText(tipList.get(i).symbol);

        viewHolder.txt_Symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectedTip=tipList.get(i);

                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.txt_price.setText("₹"+tipList.get(i).price);
        viewHolder.txt_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectedTip=tipList.get(i);

                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.txt_side_at.setText(tipList.get(i).side+" At ");
        viewHolder.txt_side_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectedTip=tipList.get(i);

                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.txt_target_Price.setText("₹"+tipList.get(i).targetPrice);
        viewHolder.txt_target_Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectedTip=tipList.get(i);

                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        String priceVal1 = viewHolder.txt_live_price.getText().toString().replace("Rs.","").replace("₹","").trim();
        String priceVal2 = tipList.get(i).livePrice == null ? null:tipList.get(i).livePrice+"";
       try {
           if (priceVal1 != null && priceVal2 != null && !priceVal2.equals("null")) {
               if (Double.parseDouble(priceVal2) < Double.parseDouble(priceVal1)) {
                   viewHolder.txt_live_price.setTextColor(Color.RED);
               } else if (Double.parseDouble(priceVal2) > Double.parseDouble(priceVal1)) {
                   viewHolder.txt_live_price.setTextColor(Color.rgb(0,100,0));
               }
           }
       }catch (Exception e){}
        viewHolder.txt_live_price.setText("₹"+tipList.get(i).livePrice);
        viewHolder.txt_live_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delectedTip=tipList.get(i);

                Intent intent = new Intent(v.getContext(), ScrollingActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        if(viewHolder.txt_live_price.getText().toString().contains("null")){
            viewHolder.txt_live_price.setText("NA");
        }
        viewHolder.txt_stopLoss.setText("₹"+tipList.get(i).stopLoss);
        viewHolder.btn_exe_tip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delectedTip=tipList.get(i);
                        Intent intent = new Intent(v.getContext(), TipRow.class);
                        v.getContext().startActivity(intent);

                    }
                });
    if(tipList.get(i).AnalystName == null) {
        DatabaseReference advisor = FirebaseDatabase.getInstance().getReference("Analyst/" + tipList.get(i).tipSenderID + "/name");
//        handler.postDelayed(new SimpleTread(),30L);

        advisor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String advisorName = (String) dataSnapshot.getValue();
                viewHolder.analyst_name.setText(advisorName);
                tipList.get(i).AnalystName = advisorName;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DATA", "Failed to read value.", error.toException());
            }
        });
    }
     else{
        viewHolder.analyst_name.setText( tipList.get(i).AnalystName );
    }
    }
 
    @Override
    public int getItemCount() {
        return tipList.size();
    }
 
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Symbol,txt_price,txt_side_at,txt_target_Price,txt_live_price,txt_stopLoss,analyst_name;
        Button btn_exe_tip;
        public ViewHolder(View view) {
            super(view);
            txt_Symbol = (TextView)view.findViewById(R.id.txt_Symbol);
            txt_price = (TextView)view.findViewById(R.id.txt_price);
            txt_side_at = (TextView)view.findViewById(R.id.txt_side_at);
            txt_target_Price = (TextView)view.findViewById(R.id.txt_target_Price);
            txt_live_price= (TextView)view.findViewById(R.id.txt_live_price);
            txt_stopLoss = (TextView)view.findViewById(R.id.txt_stopLoss);
            btn_exe_tip = (Button) view.findViewById(R.id.btn_exe_tip);
            analyst_name= (TextView)view.findViewById(R.id.analyst_name);        }
    }
   public Timer timer ;
    protected  void startTimer() {
         timer = new Timer("MyTimer");
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {

                    if(firebaseUpdateWorking)
                        return;

                    for(TipBean tip :tipList){
                        Long key= Long.parseLong(tip.instrumentID);
                        if(HomeActivity.marketData.containsKey(key)){
                            tip.livePrice =  HomeActivity.marketData.get(key)/100;
                            createThread();
                        }
                    }
                }
        }, 0, 5000);
    }
    public void createThread(){
        myHandler.post(new Runnable() {
            @Override
            public void run() {
//                notifyItemInserted(tipList.size()==0 ? 0 : tipList.size()-1);
                notifyDataSetChanged();
            }
        });
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };




}