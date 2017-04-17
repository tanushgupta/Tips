package com.symphonyfintech.tips.adapters.FirebaseConnector;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.adapters.tipsAdapter.BaseRecyclerViewAdapter;

import java.util.HashMap;

/**
 * Created by Tanush on 4/16/2017.
 */
public class OrdersSingleton implements ValueEventListener{

    private DatabaseReference firebaseObj;

    private BaseRecyclerViewAdapter baseAdapter;

    public static boolean isBusy = false;

    public static boolean getTips = false;
    public static boolean getAdvisors = false;
    public static boolean getUsers = false;

    private HashMap<String, HashMap<String, Object>> tips;
    //private HashMap<String, HashMap<String, Object>> advisors;
    //private HashMap<String, HashMap<String, Object>> users;

    private static OrdersSingleton uniqInstance;

    private OrdersSingleton() {
        //Log.i("OrdersSingleton Initialized: ", "***************** Inside constructor *********************");
        firebaseObj = FirebaseDatabase.getInstance().getReference("/");
        firebaseObj.addValueEventListener(this);
    }

    public static OrdersSingleton getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new OrdersSingleton();
        }
        return uniqInstance;
    }

    public HashMap<String, HashMap<String, Object>> getTip() {
        return tips;
    }

    public void setBaseAdapter(BaseRecyclerViewAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
    }

    /*
    public HashMap<String, HashMap<String, Object>> getAdvisors() {
        return advisors;
    }

    public HashMap<String, HashMap<String, Object>> getOrders() {
        return users;
    }
    */

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //Log.i("", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! AllDATASnapShot: " + dataSnapshot.toString() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        HashMap<String, HashMap<String, Object>> value = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
        for (HashMap.Entry<?, ?> kvpair : value.entrySet()) {
            Log.i("Key: ", "************************** "+ kvpair.getKey().toString() + "**********************************");
            switch (kvpair.getKey().toString()){
                case "Tips":
                    tips = (HashMap<String, HashMap<String, Object>>)kvpair.getValue();
                    if(baseAdapter != null){
                        if(getTips){
                            baseAdapter.updateRows(tips);
                        }
                    }
                    //getAllTips((HashMap<String, HashMap<String, Object>>)kvpair.getValue());
                    break;
                case "Analysts":
                    //advisors = (HashMap<String, HashMap<String, Object>>)kvpair.getValue();
                    //getAllAdvisors((HashMap<String, HashMap<String, Object>>)kvpair.getValue());
                    break;
                case "Users":
                    //getAllUsers((HashMap<String, HashMap<String, Object>>)kvpair.getValue());

                    break;
                default:
                    Log.i("Unknown Data:", kvpair.getKey().toString());
                    break;
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("DATA", "Failed to read value.", databaseError.toException());
    }

    /*
    private void getAllTips(HashMap<String, HashMap<String, Object>> objTips){
        for (HashMap.Entry<?, ?> entry2 : objTips.entrySet()) {
            TipBean tipbean = new TipBean();
            for (HashMap.Entry<?, ?> entry : ((HashMap<String, Object>) entry2.getValue()).entrySet()) {
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
        }
    }

    private void getAllAdvisors(HashMap<String, HashMap<String, Object>> objAdvisors){
    }

    private void getAllUsers(HashMap<String, HashMap<String, Object>> objUsers){
    }
    */
}
