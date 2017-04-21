package com.symphonyfintech.tips.model.advisor;

import android.support.annotation.NonNull;

import com.symphonyfintech.tips.model.tips.TipList;
import com.symphonyfintech.tips.view.advisors.*;

/**
 * Created by Tanush on 4/14/2017.
 */

public class AdvisorHeaderItem extends AdvisorList{

    @NonNull
    private String txtHeader;

    public AdvisorHeaderItem(@NonNull String txtHeader) {
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
