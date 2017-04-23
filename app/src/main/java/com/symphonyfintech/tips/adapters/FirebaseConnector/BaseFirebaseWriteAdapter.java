package com.symphonyfintech.tips.adapters.FirebaseConnector;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanush on 4/22/2017.
 */

public class BaseFirebaseWriteAdapter {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public BaseFirebaseWriteAdapter(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/");
    }

    public void addTips(String advisor,int subscribed){
    }


}
