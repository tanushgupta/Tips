package com.symphonyfintech.tips.adapters.CustomAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseFirebaseConnectSingletonAdapter;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import org.json.JSONObject;

/**
 * Created by Tanush on 3/30/2017.
 */

public class LoginAdapter extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener,View.OnClickListener{
    private RequestQueue queue;
    private final String url = "http://103.69.169.2:19003/login/";
    private String userName;
    private Button mLogin;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(AppConnectionStatus.getInstance(this).isOnline()){
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setTitle("Logging In..");
            dialog.setMessage("Please wait.");
            dialog.show();
            mLogin.setEnabled(false);
            userName = ((EditText) findViewById(R.id.input_userName)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.input_password)).getText().toString().trim();
            checkLogin(password);
        }
        else{
            Toast.makeText(this,"Not connected to network.",Toast.LENGTH_SHORT).show();
        }
    }

    public void checkLogin(String password){
        queue = Volley.newRequestQueue(this);
        JSONObject obj = new JSONObject();
        try{
            obj.put("username",userName);
            obj.put("password",password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,obj, this, this);
        queue.add(jsObjRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        dialog.dismiss();
        mLogin.setEnabled(true);
        try{
            Log.i("Login: ", response.toString());
            if((boolean)response.get("err")){
                Toast.makeText(this,response.get("errorMessage").toString(),Toast.LENGTH_SHORT).show();
            }
            else{
                User.getInstance(response.get("acessToken").toString(),userName);
                BaseFirebaseConnectSingletonAdapter.getInstance(userName);
                Intent intent = new Intent(LoginAdapter.this, OneTouchMainActivity.class);
                //intent.putExtra("User",user);
                LoginAdapter.this.startActivity(intent);
                finish();
            }
        }
        catch(Exception ex){
            Toast.makeText(this,"Some issues while logging in.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.dismiss();
        mLogin.setEnabled(true);
        error.printStackTrace();
        try{
            Log.i("Error ",error.getMessage().toString());
            Toast.makeText(this,error.getMessage().toString().toUpperCase(),Toast.LENGTH_SHORT).show();
        }
        catch (NullPointerException ex){
            Toast.makeText(this,"Server Down, Try again later.",Toast.LENGTH_SHORT).show();
            //ex.printStackTrace();
        }
        catch (Exception ex){
            Toast.makeText(this,"Error caused in server.",Toast.LENGTH_SHORT).show();
            //ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}