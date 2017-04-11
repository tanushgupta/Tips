package com.symphonyfintech.tips.view.tips;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.symphonyfintech.tips.R;
import com.symphonyfintech.tips.model.tips.Tip;

/**
 * Created by Tanush on 4/7/2017.
 */

public class TipDetailFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_tips_details, container, false);
        return mView;
    }

    public void setUI(Tip tip){
        ((TextView) mView.findViewById(R.id.txt_tip_name)).setText(tip.getSymbol());
        ((TextView) mView.findViewById(R.id.txt_tip_name)).setText(tip.getSymbol());
        ((TextView) mView.findViewById(R.id.txt_tip_side)).setText(tip.getSide());
        ((TextView) mView.findViewById(R.id.txt_status_tip)).setText("ACTIVE");
        ((TextView) mView.findViewById(R.id.txt_live)).setText(tip.getPrice());
        ((TextView) mView.findViewById(R.id.txt_target)).setText(tip.getTargetPrice());
        ((TextView) mView.findViewById(R.id.txt_stplss)).setText(tip.getStopLoss());
        ((TextView) mView.findViewById(R.id.txt_tip_description)).setText(tip.getDescription());
        GraphView graph = (GraphView) mView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

}
