package com.symphonyfintech.tips.model;

import java.io.Serializable;

/**
 * Created by Tanush on 3/29/2017.
 */

@SuppressWarnings("serial")
public class User implements Serializable{
    private String username;
    private String emailid;
    private String password;

    public User(String username, String emailid, String password){
        this.emailid = emailid;
        this.password = password;
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
