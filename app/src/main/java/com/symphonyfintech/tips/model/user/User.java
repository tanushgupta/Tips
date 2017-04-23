package com.symphonyfintech.tips.model.user;

import android.util.Log;

import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseFirebaseConnectSingletonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanush on 3/29/2017.
 */

public class User{

    private static User uniqueuserInstance;

    public static int GUEST_USER = 0;
    public static int AUTH_USER = 1;

    public static int CONSUMER = 2;
    public static int ANALYST = 3;

    private String acessToken;
    private String userName;

    public int AuthType;
    public int userType;

    private List<String> favAnalysts;

    public static User getInstance(){
        if(uniqueuserInstance == null){
            uniqueuserInstance = new User();
        }
        return uniqueuserInstance;
    }

    public static User getInstance(String acessToken,String userName){
        if(uniqueuserInstance == null){
            uniqueuserInstance = new User(acessToken,userName);
        }
        return uniqueuserInstance;
    }

    private User(){
        this.AuthType = GUEST_USER;
        this.userType = CONSUMER;
        this.favAnalysts = new ArrayList<>();
    }

    private User(String acessToken,String userName) {
        this.acessToken = acessToken;
        this.userName = userName;
        this.AuthType = 1;
        this.userType = CONSUMER;
        this.favAnalysts = new ArrayList<>();
    }

    public void setUserType(String type) {
        if (type.equals("Consumer")) {
            this.userType = User.CONSUMER;
        }
        else
            this.userType = User.ANALYST;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public String getUserName(){
        return userName;
    }

    public void setLstFavAnalyst(List<String> lstAnalyst){
        this.favAnalysts = lstAnalyst;
    }

    public List<String> getList(){
        return favAnalysts;
    }

    public void destroyUser(){
        if(this.AuthType == AUTH_USER){
            BaseFirebaseConnectSingletonAdapter.getInstance(this.userName).destroy();
        }
        uniqueuserInstance = null;
    }
}
