package com.symphonyfintech.tips.view.tips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.tipsAdapter.TipAdapter;
import com.symphonyfintech.tips.model.tips.TipBean;

public class TipRow extends AppCompatActivity {
    TextView send_ordr__side,send_ordr_symbol,send_ordr_price,send_ordr_qnty,send_ordr_stopLoss,send_ordr_targetPrice;
    Button send_ordr_Execute;
    TipBean tip ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_row);
        tip = TipAdapter.delectedTip ;
        send_ordr__side = (TextView) findViewById(R.id.send_ordr__side);
        send_ordr__side.setText(tip.side+" At");
        send_ordr_symbol = (TextView) findViewById(R.id.send_ordr_symbol);
        send_ordr_symbol.setText(tip.symbol.toUpperCase());
        send_ordr_price = (TextView) findViewById(R.id.send_ordr_price);
        send_ordr_price.setText(tip.price);
        send_ordr_qnty = (TextView) findViewById(R.id.send_ordr_qnty);
        send_ordr_qnty.setText(tip.orderQuantity);
        send_ordr_stopLoss = (TextView) findViewById(R.id.send_ordr_stopLoss);
        send_ordr_stopLoss.setText(tip.stopLoss);
        send_ordr_targetPrice = (TextView) findViewById(R.id.send_ordr_targetPrice);
        send_ordr_targetPrice.setText(tip.targetPrice);
        send_ordr_Execute =(Button)findViewById(R.id.send_ordr_Execute);
        send_ordr_Execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
