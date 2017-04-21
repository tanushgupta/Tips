package com.symphonyfintech.tips.model.tips;

/**
 * Created by Tanush on 4/14/2017.
 */

public abstract class TipList {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_TIP = 1;

    abstract public int getType();
}