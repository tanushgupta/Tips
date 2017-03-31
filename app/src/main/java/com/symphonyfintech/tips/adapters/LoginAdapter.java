package com.symphonyfintech.tips.adapters;

import android.util.Log;
import android.widget.EditText;

/**
 * Created by Tanush on 3/30/2017.
 */

public class LoginAdapter {
    private String email;
    private String password;
    private int errorCode;

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
            if(!email.equals("tanush")){this.errorCode = 1;}
            else if(!password.equals("tanush")){this.errorCode = 2;}
            else{ this.errorCode = -1;}
            return invalidLogin();
        }
    }
    private int invalidLogin(){
        return errorCode;
    }
}
