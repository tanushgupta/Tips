package com.symphonyfintech.tips.view.tips;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.Tip;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.tips.TipItem;
import com.symphonyfintech.tips.model.tips.TipList;
import com.symphonyfintech.tips.model.user.User;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tanush on 4/7/2017.
 */

public class TipDetailFragment extends Fragment {

    private View mView;
    private Timer timer ;
    private Handler myHandler;
    private String curr_price="NA",symbol="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_tips_details, container, false);
        myHandler = new Handler();
        return mView;
    }

    public void setUI(final TipBean tip){
        this.symbol = tip.instrumentID;
        ((TextView) mView.findViewById(R.id.txt_tip_name)).setText(tip.symbol);
        ((TextView) mView.findViewById(R.id.txt_tip_side)).setText(tip.side + " at ");
        ((TextView) mView.findViewById(R.id.txt_status_tip)).setText("ACTIVE");
        ((TextView) mView.findViewById(R.id.txt_target_Price)).setText("₹" + tip.targetPrice);
        ((TextView) mView.findViewById(R.id.txt_stplss)).setText("₹" + tip.stopLoss);
        ((TextView) mView.findViewById(R.id.txt_tip_description)).setText(tip.description);
        ((TextView) mView.findViewById(R.id.tip_side_price)).setText("₹" + tip.price);
        GraphView graph = (GraphView) mView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1),
                new DataPoint(2, 2),
                new DataPoint(3, 1),
                new DataPoint(4, 6),
                new DataPoint(5, 9),
                new DataPoint(6, 2),
                new DataPoint(8, 6),
                new DataPoint(12, 1),
                new DataPoint(16, 4),
                new DataPoint(18, 6),

        });
        series.setColor(Color.WHITE);
        series.setThickness(5);
        graph.addSeries(series);
        final User user = ((TipsMainActivity)getActivity()).getUser();
        if(user.userType == User.GUEST_USER){
            ((Button) mView.findViewById(R.id.btn_Execute_tip)).setEnabled(false);
            ((Button) mView.findViewById(R.id.btn_Execute_tip)).setBackgroundColor(Color.GRAY);
            //Toast.makeText(getActivity(),"Please Log In to execute a tip",Toast.LENGTH_SHORT).show();
        }
        else{
            ((Button) mView.findViewById(R.id.btn_Execute_tip)).setEnabled(true);
            ((Button) mView.findViewById(R.id.btn_Execute_tip)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(),"User Token: "+ user.getAcessToken() + ". Working on Execute Tip.",Toast.LENGTH_SHORT).show();
                    ((TipsMainActivity)getActivity()).openExecuteTipFragment(tip);
                }
            });
        }
        startTimer();
    }

    protected  void startTimer() {
        timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Long key= Long.parseLong(symbol);
                Log.i("Live Price: ","===================================== "+ TipsMainActivity.marketData.get(key) + "===============================");
                if(TipsMainActivity.marketData.containsKey(key)){
                    curr_price =  Double.toString(TipsMainActivity.marketData.get(key)/100);
                    createThread();
                }
            }
        }, 0, 5000);
    }
    public void createThread(){
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) mView.findViewById(R.id.txt_live)).setText("₹" + curr_price);
            }
        });
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

}
