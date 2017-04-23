package com.symphonyfintech.tips.view.tips;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.CustomAdapter.OrderExecution;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

public class ExecuteTip extends AppCompatActivity implements View.OnClickListener{
    TextView send_ordr__side,send_ordr_symbol,send_ordr_price,send_ordr_qnty,send_ordr_stopLoss,send_ordr_targetPrice;
    Button send_ordr_Execute;
    TipBean tip ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_row);
        tip = TipRowDetails.tip;
        send_ordr__side = (TextView)findViewById(R.id.send_ordr__side);
        send_ordr_symbol = (TextView)findViewById(R.id.send_ordr_symbol);
        send_ordr_price = (TextView)findViewById(R.id.send_ordr_price);
        send_ordr_qnty = (TextView)findViewById(R.id.send_ordr_qnty);
        send_ordr_stopLoss = (TextView)findViewById(R.id.send_ordr_stopLoss);
        send_ordr_targetPrice = (TextView)findViewById(R.id.send_ordr_targetPrice);
        send_ordr_Execute =(Button)findViewById(R.id.send_ordr_Execute);
        send_ordr__side.setText(tip.side+" At");
        send_ordr_symbol.setText(tip.symbol.toUpperCase());
        send_ordr_price.setText(tip.price);
        send_ordr_qnty.setText(tip.orderQuantity);
        send_ordr_stopLoss.setText(tip.stopLoss);
        send_ordr_targetPrice.setText(tip.targetPrice);
        send_ordr_Execute.setOnClickListener(this);
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_tip_row, container, false);
        send_ordr__side = (TextView) view.findViewById(R.id.send_ordr__side);
        send_ordr_symbol = (TextView) view.findViewById(R.id.send_ordr_symbol);
        send_ordr_price = (TextView) view.findViewById(R.id.send_ordr_price);
        send_ordr_qnty = (TextView) view.findViewById(R.id.send_ordr_qnty);
        send_ordr_stopLoss = (TextView) view.findViewById(R.id.send_ordr_stopLoss);
        send_ordr_targetPrice = (TextView) view.findViewById(R.id.send_ordr_targetPrice);
        send_ordr_Execute =(Button)view.findViewById(R.id.send_ordr_Execute);
        send_ordr_Execute.setOnClickListener(this);
        return view;
    }*/

    @Override
    public void onClick(View v) {
        //Toast.makeText(this,"Working on execution",Toast.LENGTH_SHORT).show();
        new OrderExecution(this,tip,send_ordr_qnty.getText().toString(),send_ordr_price.getText().toString(),
                send_ordr_targetPrice.getText().toString(),send_ordr_stopLoss.getText().toString());
        finish();

    }
}
