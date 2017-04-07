package com.symphonyfintech.tips.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.Tip;

import java.util.Random;

/**
 * Created by Tanush on 4/6/2017.
 */

public class DialogTipDetail extends AppCompatActivity{
    DialogTipDetail(Context context,Tip tip){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.dialog_tips_details,null);
        ((TextView) mView.findViewById(R.id.txt_tip_name)).setText(tip.getSymbol());
        ((TextView) mView.findViewById(R.id.txt_tip_side)).setText(tip.getSide());
        ((TextView)mView.findViewById(R.id.txt_status_tip)).setText("ACTIVE");
        ((TextView)mView.findViewById(R.id.txt_live)).setText(tip.getPrice());
        ((TextView)mView.findViewById(R.id.txt_target)).setText(tip.getTargetPrice());
        ((TextView)mView.findViewById(R.id.txt_stplss)).setText(tip.getStopLoss());
        ((TextView)mView.findViewById(R.id.txt_tip_description)).setText(tip.getDescription());
        GraphView graph = (GraphView) mView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
