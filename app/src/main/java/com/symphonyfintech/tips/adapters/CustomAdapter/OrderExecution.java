package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import org.json.JSONObject;

/**
 * Created by Tanush on 4/21/2017.

 * Parameters for sending order
 "acessToken":"DC2EE5BB098F73FBE906253A37C704D4",
 "tipId":"-KfhYAF93CzarxO9gAJs",
 "tipSenderID":"QuantXpress",
 "user": "nikulCLI",
 "side": "BUY",
 "quantity": "50",
 "symbol": "RELIANCE",
 "securityID": "2885",
 "price": "1000",
 "account": "SFT04",
 "timeInForce": "DAY",
 "targetPrice":"1050",
 "stopPrice": "900",
 */

public class OrderExecution implements Response.Listener<JSONObject>,Response.ErrorListener {

    private RequestQueue queue;
    private final String url = "http://103.69.169.2:19003/login/";
    private String qty, prc, target, stop;
    private TipBean tip;
    private Context mContext;
    private ProgressDialog dialog;

    public OrderExecution(Context mContext, TipBean tip, String quantity, String price, String target, String stop){
        this.mContext = mContext;
        this.tip = tip;
        this.qty = quantity;
        this.prc = price;
        this.target = target;
        this.stop = stop;
        new performSendOrder().execute();
    }

    public void SendOrder(){
        Log.i("Order Exec ", "Sending Order");
        queue = Volley.newRequestQueue(mContext);
        JSONObject obj = new JSONObject();
        try{
            obj.put("acessToken",User.getInstance().getAcessToken());
            obj.put("user",User.getInstance().getUserName());
            obj.put("tipId", tip.tipId);
            obj.put("tipSenderID", tip.tipSenderID);
            obj.put("side", tip.side);
            obj.put("symbol", tip.symbol);
            obj.put("securityID", tip.instrumentID);
            obj.put("quantity", qty);
            obj.put("price",prc);
            obj.put("targetPrice",target);
            obj.put("stopPrice",stop);
            //obj.put("securityType","COMMON_STOCK");
            //obj.put("exchange", "NSECM");
            obj.put("account", "SFT04");
            obj.put("timeInForce", "DAY");
            //obj.put("orderType", "MARKET");
            //Log.i("Order ","####################################### "+ obj.toString() + "########################");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,obj, this, this);
        queue.add(jsObjRequest);
        Log.i("Order Exec ", "Done Execution");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("Sent Order ", "=======================SEND ORDER REPLY==============================="
                + response.toString());
    }

    public class performSendOrder extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(mContext,"Sending....",Toast.LENGTH_SHORT).show();
            dialog = new ProgressDialog(mContext);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setTitle("Sending Order..");
            dialog.setMessage("Please wait.");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SendOrder();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}
