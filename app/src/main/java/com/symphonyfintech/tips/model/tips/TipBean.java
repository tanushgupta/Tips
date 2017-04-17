package com.symphonyfintech.tips.model.tips;

import android.widget.ImageView;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhru1 on 1/30/2017.
 */
@IgnoreExtraProperties
public class TipBean implements Comparable<TipBean> {

    public TipBean() {

    }

    public void  fetchDataForThisTip() {
    /*
        t1= new Thread(new simpleDataFech(instrumentID));
        t1.start();
    */
    }
    //For System
    public Thread t1;
    public ImageView profile =null;
    public String AnalystName=null;


    public String tipId;
    public String tipSenderID;
    public String symbol;
    public String price;
    public Double livePrice;
    public String instrumentID;
    public String orderQuantity;
    public String productType;
    public String triggerPrice;
    public String side;
    public String targetPrice;
    public String stopLoss;
    public String tipCreatedAtTime;
    public String tipExpiry;
    public String description;
    public String executed;

    public TipBean(
            String tipId,
            String tipSenderID,
            String symbol,
            String price,
            String instrumentID,
            String orderQuantity,
            String productType,
            String triggerPrice,
            String side,
            String targetPrice,
            String stopLoss,
            String tipCreatedAtTime,
            String tipExpiry,
            String description
    ) {
        this.tipId = tipId;
        this.tipSenderID = tipSenderID;
        this.symbol = symbol;
        this.price = price;
        this.instrumentID = instrumentID;
        this.orderQuantity = orderQuantity;
        this.productType = productType;
        this.triggerPrice = triggerPrice;
        this.side = side;
        this.targetPrice = targetPrice;
        this.stopLoss = stopLoss;
        this.tipCreatedAtTime = tipCreatedAtTime;
        this.tipExpiry = tipExpiry;
        this.description = description;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tipId", System.currentTimeMillis());
        result.put("tipSenderID", tipSenderID);
        result.put("symbol", symbol);
        result.put("price", price);
        result.put("instrumentID", instrumentID);
        result.put("orderQuantity", orderQuantity);
        result.put("productType", productType);
        result.put("triggerPrice", triggerPrice);
        result.put("side", side);
        result.put("targetPrice", targetPrice);
        result.put("stopLoss", stopLoss);
        result.put("tipExpiry", tipExpiry);
        result.put("tipCreatedAtTime", tipCreatedAtTime);
        result.put("description", description);
        return result;
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(TipBean o) {
        if(Long.parseLong(tipCreatedAtTime)==Long.parseLong(o.tipCreatedAtTime))
            return 0;
        else if(Long.parseLong(tipCreatedAtTime)>Long.parseLong(o.tipCreatedAtTime))
            return 1;
        else
            return -1;
    }
}