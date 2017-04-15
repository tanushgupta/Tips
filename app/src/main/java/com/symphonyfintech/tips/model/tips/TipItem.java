package com.symphonyfintech.tips.model.tips;

import android.support.annotation.NonNull;

/**
 * Created by Tanush on 4/14/2017.
 */

public class TipItem extends TipList{

    @NonNull
    private TipBean tipBean;

    public TipItem(@NonNull TipBean bean) {
        this.tipBean = bean;
    }

    @NonNull
    public TipBean getTip() {
        return tipBean;
    }

    @Override
    public int getType() {
        return TYPE_EVENT;
    }
}
