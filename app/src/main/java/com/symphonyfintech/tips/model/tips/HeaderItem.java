package com.symphonyfintech.tips.model.tips;

import android.support.annotation.NonNull;

/**
 * Created by Tanush on 4/14/2017.
 */

public class HeaderItem extends TipList{

    @NonNull
    private String txtHeader;

    public HeaderItem(@NonNull String txtHeader) {
        this.txtHeader = txtHeader;
    }

    @NonNull
    public String getHeader() {
        return txtHeader;
    }

    // here getters and setters
    // for title and so on, built
    // using date

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
