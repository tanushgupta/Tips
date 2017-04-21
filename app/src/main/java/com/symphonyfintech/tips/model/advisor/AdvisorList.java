package com.symphonyfintech.tips.model.advisor;

/**
 * Created by Tanush on 4/20/2017.
 */

public abstract class AdvisorList {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ADVISOR = 1;

    abstract public int getType();
}
