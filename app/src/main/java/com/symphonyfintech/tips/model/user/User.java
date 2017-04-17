package com.symphonyfintech.tips.model.user;

import java.io.Serializable;

/**
 * Created by Tanush on 3/29/2017.
 */

@SuppressWarnings("serial")
public class User implements Serializable{
    public static int GUEST_USER = 0;
    public static int AUTH_USER = 1;

    private String acessToken;
    private String userName;
    public int userType;

    public User(){
        this.userType = GUEST_USER;
    }

    public User(String acessToken,String userName) {
        this.acessToken = acessToken;
        this.userName = userName;
        this.userType = 1;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public String getUserName(){
        return userName;
    }
}
