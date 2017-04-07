package com.symphonyfintech.tips.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.value;
import static android.content.ContentValues.TAG;

/**
 * Created by Tanush on 4/5/2017.
 */

public class Tip {
    private String targetPrice;
    private String tipCreatedAtTime;
    private String tipExpiry;
    private String price;
    private long tipId;
    private String description;
    private String triggerPrice;
    private String instrumentID;
    private String side;
    private String stopLoss;
    private String orderQuantity;
    private String symbol;
    private String tipSenderID;

    /*
    private String mSymName;
    private String mSymSide;
    private String mSymType;
    private String mLiveprc;
    private String mTargetprc;
    private String mStploss;
    private String mPostAdv;
    private HashMap<String,Tip> mapList;
    */

    public Tip() {
        Log.d("Trace: ", "Tip default Constructor");
    }

    public Tip(String targetPrice, String tipCreatedAtTime, String tipExpiry, String price, long tipId, String description, String triggerPrice, String instrumentID, String side, String stopLoss, String orderQuantity, String symbol, String tipSenderID) {
        Log.d("Trace: ", "Inside Tips parameterized constructor");
        this.targetPrice = targetPrice;
        this.tipCreatedAtTime = tipCreatedAtTime;
        this.tipExpiry = tipExpiry;
        this.price = price;
        this.tipId = tipId;
        this.description = description;
        this.triggerPrice = triggerPrice;
        this.instrumentID = instrumentID;
        this.side = side;
        this.stopLoss = stopLoss;
        this.orderQuantity = orderQuantity;
        this.symbol = symbol;
        this.tipSenderID = tipSenderID;
    }

    public String getTargetPrice() {
        return targetPrice;
    }

    public String getTipCreatedAtTime() {
        return tipCreatedAtTime;
    }

    public String getTipExpiry() {
        return tipExpiry;
    }

    public String getPrice() {
        return price;
    }

    public long getTipId() {
        return tipId;
    }

    public String getDescription() {
        return description;
    }

    public String getTriggerPrice() {
        return triggerPrice;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public String getSide() {
        return side;
    }

    public String getStopLoss() {
        return stopLoss;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTipSenderID() {
        return tipSenderID;
    }

    /*
    public Tip(String mSymName, String mSymSide, String mSymType, String mLiveprc, String mTargetprc, String mStploss, String mPostAdv) {
        this.mSymName = mSymName;
        this.mSymSide = mSymSide;
        this.mSymType = mSymType;
        this.mLiveprc = mLiveprc;
        this.mTargetprc = mTargetprc;
        this.mStploss = mStploss;
        this.mPostAdv = mPostAdv;
    }
    */


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("targetPrice",targetPrice);
        result.put("tipCreatedAtTime",tipCreatedAtTime);
        result.put("tipExpiry",tipExpiry);
        result.put("price",price);
        result.put("tipId",tipId);
        result.put("description",description);
        result.put("triggerPrice",triggerPrice);
        result.put("instrumentID",instrumentID);
        result.put("side",side);
        result.put("stopLoss",stopLoss);
        result.put("orderQuantity",orderQuantity);
        result.put("symbol",symbol);
        result.put("tipSenderID",tipSenderID);
        return result;
    }

    /*
    public String getmSymName() {
        return mSymName;
    }

    public String getmSymSide() {
        return mSymSide;
    }

    public String getmSymType() {
        return mSymType;
    }

    public String getmLiveprc() {
        return mLiveprc;
    }

    public String getmTargetprc() {
        return mTargetprc;
    }

    public String getmStploss() {
        return mStploss;
    }

    public String getmPostAdv() {
        return mPostAdv;
    }

    */

    //private static int lastTipId = 0;

    /*public static ArrayList<Tip> createTipsList(int numTips) {
        ArrayList<Tip> tips = new ArrayList<Tip>();

        for (int i = 1; i <= numTips; i++) {
            tips.add(new Tip("Reliance " + ++lastTipId,"Buy @ 14.32","ACTIVE","14.23","24.32","33.23","Posted By: Tanush"));
        }
        return tips;
    }*/
}
