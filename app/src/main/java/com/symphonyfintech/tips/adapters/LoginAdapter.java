package com.symphonyfintech.tips.adapters;

import android.util.Log;

/**
 * Created by Tanush on 3/30/2017.
 */

public class LoginAdapter {
    private String email;
    private String password;

    public LoginAdapter(String email, String password){
        this.email = email;
        this.password = password;
    }

    public int validateUser(){
        if(email.equals("tanush") && password.equals("tanush")){
            Log.d("Login: ", "Success");
            return 3;
        }
        else{
            if(!email.equals("tanush")){return 1;}
            else if(!password.equals("tanush")){return 2;}
            else{ return -1;}
        }
    }
}
