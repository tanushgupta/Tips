package com.symphonyfintech.tips.adapters.FirebaseConnector;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tanush on 4/16/2017.
 */
public class BaseFirebaseConnectSingletonAdapter implements ValueEventListener{

    private DatabaseReference UserFBObj;

    public static boolean isBusy = false;

    private HashMap<String, HashMap<String, Object>> advisors;
    private HashMap<String, HashMap<String, Object>> users;

    private static BaseFirebaseConnectSingletonAdapter uniqInstance;

    public void destroy(){
        uniqInstance = null;
    }

    private BaseFirebaseConnectSingletonAdapter(String user) {
        UserFBObj = FirebaseDatabase.getInstance().getReference("Users/" + user + "/");
        UserFBObj.addValueEventListener(this);
    }

    public static BaseFirebaseConnectSingletonAdapter getInstance(String user) {
        if (uniqInstance == null) {
            uniqInstance = new BaseFirebaseConnectSingletonAdapter(user);
        }
        return uniqInstance;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getRef().equals(UserFBObj)){
            if(dataSnapshot.getValue() != null) {
                getUserData((HashMap<String, Object>) dataSnapshot.getValue());
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.w("DATA", "Failed to read value.", databaseError.toException());
    }

    private void getUserData(HashMap<String, Object> objUsers){
        for (HashMap.Entry<?,?> kvPair: objUsers.entrySet()){
            switch (kvPair.getKey().toString()){
                case "FavAnalyst":
                    getFavAdvisors((HashMap<String, List<String>>)kvPair.getValue());
                    break;
                case "UserType":
                    User.getInstance().setUserType(kvPair.getValue().toString());
                    break;
            }
        }
    }
    private void getFavAdvisors(HashMap<String, List<String>> objAdvisors){
        for (HashMap.Entry<?,?> kvPair: objAdvisors.entrySet()){
            User.getInstance().setLstFavAnalyst((List<String>)kvPair.getValue());
        }
    }
}
