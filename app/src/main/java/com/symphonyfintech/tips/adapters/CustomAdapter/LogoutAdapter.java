package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import org.json.JSONObject;

/**
 * Created by Tanush on 4/20/2017.
 */

public class LogoutAdapter implements Response.Listener<JSONObject>,Response.ErrorListener{

    private RequestQueue queue;
    private final String url = "http://103.69.169.2:19003/logout/";
    private Context mContext;
    private String token;

    public LogoutAdapter(Context mContext){
        this.mContext = mContext;
        this.token = User.getInstance().getAcessToken();
        checkLogout();
    }

    private void checkLogout(){
        queue = Volley.newRequestQueue(mContext);
        JSONObject obj = new JSONObject();
        try{
            obj.put("acessToken",token);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,obj, this, this);
        queue.add(jsObjRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Log.e("Volley EXception:", "While performing logout: " + error.getMessage().toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            if (response.getBoolean("isLogout")) {
                Toast.makeText(mContext, "Logged out successfully.", Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(mContext, "Issues Logging out.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            //Toast.makeText(mContext,ex.toString(),Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}
