package com.symphonyfintech.tips.model.advisor;

import android.support.annotation.NonNull;

/**
 * Created by Tanush on 4/20/2017.
 */

public class AdvisorItem extends AdvisorList{
    @NonNull
    private AdvisorBean advisorBean;

    public AdvisorItem(@NonNull AdvisorBean bean) {
        this.advisorBean = bean;
    }

    @NonNull
    public AdvisorBean getadvisor() {
        return advisorBean;
    }

    @Override
    public int getType() {
        return TYPE_ADVISOR;
    }
}
