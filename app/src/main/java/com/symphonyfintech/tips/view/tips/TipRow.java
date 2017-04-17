package com.symphonyfintech.tips.view.tips;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.tipsAdapter.BaseRecyclerViewAdapter;
import com.symphonyfintech.tips.adapters.tipsAdapter.TipAdapter;
import com.symphonyfintech.tips.model.tips.TipBean;

public class TipRow extends Fragment implements View.OnClickListener{
    TextView send_ordr__side,send_ordr_symbol,send_ordr_price,send_ordr_qnty,send_ordr_stopLoss,send_ordr_targetPrice;
    Button send_ordr_Execute;
    TipBean tip ;
    private View view;

    @Nullable
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
    }

    public void setUI(TipBean tip){
        this.tip = tip;
        send_ordr__side.setText(tip.side+" At");
        send_ordr_symbol.setText(tip.symbol.toUpperCase());
        send_ordr_price.setText(tip.price);
        send_ordr_qnty.setText(tip.orderQuantity);
        send_ordr_stopLoss.setText(tip.stopLoss);
        send_ordr_targetPrice.setText(tip.targetPrice);
    }

    @Override
    public void onClick(View v) {
        /*
                  "acessToken":"DC2EE5BB098F73FBE906253A37C704D4",
                  "tipId":"-KfhYAF93CzarxO9gAJs",
                  "tipSenderID":"QuantXpress",
                  "user": "himanshu",
                  "side": "BUY",
                  "quantity": "50",
                  "symbol": "RELIANCE",
                  "securityID": "2885",
                  "securityType": "COMMON_STOCK",
                  "exchange": "NSECM",
                  "price": "1000",
                  "maxPrice":"900",
                  "minPrice":"1110",
                  "account": "SFT04",
                  "timeInForce": "DAY",
                  "targetPrice":"1050",
                  "stopPrice": "900",
                  "orderType": "MARKET"
         */
        final String accessToken = ((TipsMainActivity)getActivity()).getUser().getAcessToken();

    }
}
