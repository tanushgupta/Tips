package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Tanush on 3/30/2017.
 */

public class LoginAdapter implements Response.Listener,Response.ErrorListener{
    private String errorMsg ="";
    private RequestQueue queue;
    private final String url = "http://103.69.169.2:19003/login/";
    //private Context context;

    public LoginAdapter(String email, String password,final Context context){
        queue = Volley.newRequestQueue(context);
        JSONObject obj = new JSONObject();
        try{
            obj.put("username",email);
            obj.put("password",password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,obj, this, this);
        queue.add(jsObjRequest);
    }

    @Override
    public void onResponse(Object response) {
        //Toast.makeText(,"Login "+ response.toString(),Toast.LENGTH_LONG).show();
        Log.i("Login: ", response.toString());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("Error ",error.toString());
        errorMsg = error.toString();
    }

    public String getResult() {
        return errorMsg;
    }
}