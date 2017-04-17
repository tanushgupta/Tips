package com.symphonyfintech.tips.adapters.orderAdapter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.order.OrderBean;
import com.symphonyfintech.tips.view.general.HomeActivity;
import com.symphonyfintech.tips.view.tips.TipsMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    DatabaseReference tipsFirebaseRef;
   public static OrderBean delectedTip;
    boolean firebaseUpdateWorking=false;
    private List<OrderBean> tipList ;
    final Handler myHandler;
    public OrderAdapter(String userName) {
        myHandler = new Handler();
        tipList = new ArrayList<>();
        tipsFirebaseRef = FirebaseDatabase.getInstance().getReference("Users/himanshu/orders");//Users/himanshu/orders");
//        handler.postDelayed(new SimpleTread(),30L);

        tipsFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseUpdateWorking = true;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.i("", "**************** OrdersSnapShot" + dataSnapshot.getValue().toString());
                Object dtata = dataSnapshot.getValue();
                ArrayList<HashMap<String, HashMap<String, Object>>> value1 = (ArrayList<HashMap<String, HashMap<String, Object>>>) dataSnapshot.getValue();
                //ArrayList<HashMap<String, Object>> value1 = (ArrayList<HashMap<String, Object>>) dataSnapshot.getValue();
                if (value1 != null) {
                    tipList = new ArrayList<OrderBean>();
                }
                    for (HashMap<?, ?> val : value1) {
                        if (val != null) {
                            OrderBean tipbean = new OrderBean();
                            for (HashMap.Entry<?, ?> entry : val.entrySet()) {
                                //print keys and values
                                //                    for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {
                                //                        System.out.println("**********\t " + entry.getKey() + " : " + entry.getValue());
                                if (entry.getKey().equals("OrderId"))
                                    tipbean.OrderId = entry.getValue().toString();
                                else if (entry.getKey().equals("Symbol"))
                                    tipbean.Symbol = entry.getValue().toString();
                                else if (entry.getKey().equals("price"))
                                    tipbean.price = entry.getValue().toString();
                                else if (entry.getKey().equals("side"))
                                    tipbean.side = entry.getValue().toString();
                                else if (entry.getKey().equals("Remark"))
                                    tipbean.Remark = entry.getValue().toString();
                                else if (entry.getKey().equals("QTY"))
                                    tipbean.QTY = entry.getValue().toString();
                                else if (entry.getKey().equals("status"))
                                    tipbean.status = entry.getValue().toString();
                                else if (entry.getKey().equals("TotalIncome"))
                                    tipbean.TotalIncome = entry.getValue().toString();
                                else if (entry.getKey().equals("instrumentID"))
                                    tipbean.instrumentID = entry.getValue().toString();


                                //                    }
                                //                    System.out.println(" \n\n\n new Entry ");
                                tipList.add(tipbean);
                                notifyItemInserted(tipList.size() == 0 ? 0 : tipList.size() - 1);
                                notifyDataSetChanged();
                            }
                        }
                    }

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_order_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.close_order_symbol.setText(tipList.get(i).Symbol);
        viewHolder.close_order_side.setText(tipList.get(i).side.equalsIgnoreCase("buy") ? "Bought at ":"Sold at ");
        viewHolder.close_order_pice.setText(""+tipList.get(i).price+"₹");

        String priceVal1 = viewHolder.livePrice.getText().toString().replace("Rs.","").replace("₹","").trim();
        String priceVal2 = tipList.get(i).livePrice == null ? null:tipList.get(i).livePrice+"";
        try {
            if (priceVal1 != null && priceVal2 != null && !priceVal2.equals("null")) {
                if (Double.parseDouble(priceVal2) < Double.parseDouble(priceVal1)) {
                    viewHolder.livePrice.setTextColor(Color.RED);
                } else if (Double.parseDouble(priceVal2) > Double.parseDouble(priceVal1)) {
                    viewHolder.livePrice.setTextColor(Color.rgb(0,100,0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        viewHolder.livePrice.setText(tipList.get(i).livePrice+ "₹");
        viewHolder.close_order_income.setText(tipList.get(i).TotalIncome+"₹");
        viewHolder.close_order_remark.setText(tipList.get(i).Remark.equalsIgnoreCase("target") ? "Target Price Hit":"Stop Loss Hit");
        viewHolder.close_order_status.setText("TOTAL "+tipList.get(i).status.toLowerCase());
    }
 
    @Override
    public int getItemCount() {
        return tipList.size();
    }
 
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView close_order_symbol,close_order_side,close_order_pice,livePrice,close_order_income,close_order_remark,close_order_status;
        public ViewHolder(View view) {
            super(view);
            close_order_symbol = (TextView)view.findViewById(R.id.close_order_symbol);
            close_order_side = (TextView)view.findViewById(R.id.close_order_side);
            close_order_pice = (TextView)view.findViewById(R.id.close_order_pice);

            livePrice = (TextView)view.findViewById(R.id.livePrice);
            close_order_income = (TextView)view.findViewById(R.id.close_order_income);
            close_order_remark = (TextView) view.findViewById(R.id.close_order_remark);
            close_order_status=(TextView) view.findViewById(R.id.close_order_status);
        }
    }
   private Timer timer ;
    protected  void startTimer() {
         timer = new Timer("MyTimer");
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {

                    if(firebaseUpdateWorking)
                        return;

                    for(OrderBean order :tipList){
                        Long key= Long.parseLong(order.instrumentID);
                        if(TipsMainActivity.marketData.containsKey(key)){
                            order.livePrice =  (TipsMainActivity.marketData.get(key)/100)+"";
                            createThread();
                        }
                    }
                }
        }, 0, 1000);
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