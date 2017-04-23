package com.symphonyfintech.tips.view.tips;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.adapters.FirebaseConnector.BaseRecyclerViewAdapter;
import com.symphonyfintech.tips.model.tips.TipBean;
import com.symphonyfintech.tips.model.user.User;
import com.symphonyfintech.tips.view.general.OneTouchMainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tanush on 4/7/2017.
 */

public class TipRowDetails extends AppCompatActivity {
    //private View mView;
    public static TipBean tip;
    private Timer timer ;
    private Handler myHandler;
    private String curr_price="NA",symbol="";

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_tips_details, container, false);
        myHandler = new Handler();
        return mView;
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tips_details);
        setUI();
    }

    public void setUI(){
        tip = BaseRecyclerViewAdapter.selectedTip;
        this.symbol = tip.instrumentID;
        ((TextView) findViewById(R.id.txt_tip_name)).setText(tip.symbol);
        ((TextView) findViewById(R.id.txt_tip_side)).setText(tip.side + " at ");
        ((TextView) findViewById(R.id.txt_status_tip)).setText("ACTIVE");
        ((TextView) findViewById(R.id.txt_target_Price)).setText("₹" + tip.targetPrice);
        ((TextView) findViewById(R.id.txt_stplss)).setText("₹" + tip.stopLoss);
        ((TextView) findViewById(R.id.txt_tip_description)).setText(tip.description);
        ((TextView) findViewById(R.id.tip_side_price)).setText("₹" + tip.price);
        GraphView graph = (GraphView) findViewById(R.id.graph);
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
        graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL );
        graph.addSeries(series);
        //final User user = ((OneTouchMainActivity)getActivity()).getUser();
        if(User.getInstance().AuthType == User.GUEST_USER){
            //((Button) findViewById(R.id.btn_Execute_tip)).setEnabled(false);
            ((Button) findViewById(R.id.btn_Execute_tip)).setAlpha(.5f);
            Toast.makeText(this,"Please log In to execute.",Toast.LENGTH_SHORT).show();
        }
        else{
            ((Button) findViewById(R.id.btn_Execute_tip)).setEnabled(true);
            ((Button) findViewById(R.id.btn_Execute_tip)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ExecuteTip.class);
                    v.getContext().startActivity(intent);
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
                Log.i("Live Price: ","===================================== "+ OneTouchMainActivity.marketData.get(key) + "===============================");
                if(OneTouchMainActivity.marketData.containsKey(key)){
                    curr_price =  Double.toString(OneTouchMainActivity.marketData.get(key)/100);
                    createThread();
                }
            }
        }, 0, 5000);
    }
    public void createThread(){
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.txt_live)).setText("₹" + curr_price);
            }
        });
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
