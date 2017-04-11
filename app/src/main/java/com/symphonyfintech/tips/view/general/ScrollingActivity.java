package com.symphonyfintech.tips.view.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.ImageLoadTask;
import com.symphonyfintech.tips.adapters.tipsAdapter.TipAdapter;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.view.tips.TipRow;


public class ScrollingActivity extends AppCompatActivity {
    TextView tip_view_symbol,tip_view_side, tip_view_price,tip_view_liveprice,tip_view_targetprice,advisorname,tip_view_stoploss,description;
    ImageView analyst_image;
    TipBean tip ;
    Button execute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        execute =(Button)findViewById(R.id.tip_view_execute);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TipRow.class);
                v.getContext().startActivity(intent);
            }
        });
        tip = TipAdapter.delectedTip ;
        ScrollingActivity.this.setTitle(tip.symbol);

        advisorname= (TextView) findViewById(R.id.advisorname);
        tip_view_symbol = (TextView) findViewById(R.id.tip_view_symbol);
        tip_view_symbol.setText(tip.symbol);
        tip_view_side = (TextView)findViewById(R.id.tip_view_side);
        tip_view_side.setText(tip.side+" At ");
        tip_view_price = (TextView)findViewById(R.id.tip_view_price);
        tip_view_price.setText(tip.price);
        tip_view_liveprice = (TextView)findViewById(R.id.tip_view_liveprice);
        tip_view_liveprice.setText(tip.livePrice+"");
        tip_view_targetprice = (TextView)findViewById(R.id.tip_view_targetprice);
        tip_view_targetprice.setText(tip.targetPrice);
        tip_view_stoploss = (TextView)findViewById(R.id.tip_view_stoploss);
        tip_view_stoploss.setText(tip.stopLoss);
        analyst_image =(ImageView)findViewById(R.id.analyst_image);
        description=(TextView)findViewById(R.id.description);
        description.setText(tip.description);

        if(tip.AnalystName == null) {

            DatabaseReference advisor = FirebaseDatabase.getInstance().getReference("Users/" + tip.tipSenderID + "/name");
//        handler.postDelayed(new SimpleTread(),30L);
            advisor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String advisorName = (String) dataSnapshot.getValue();
                    advisorname.setText(advisorName);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("DATA", "Failed to read value.", error.toException());
                }
            });
        }
        else{
            advisorname.setText(tip.AnalystName);
        }
        if(tip.profile == null) {

            DatabaseReference alystImage = FirebaseDatabase.getInstance().getReference("Users/" + tip.tipSenderID + "/profileImage");

            alystImage.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String advisorName = (String) dataSnapshot.getValue();
                    new ImageLoadTask(advisorName, analyst_image).execute();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("DATA", "Failed to read value.", error.toException());
                }
            });
            tip.profile =   analyst_image;
        }else{
            analyst_image = tip.profile;
        }
    }
}
