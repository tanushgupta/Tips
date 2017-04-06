package com.symphonyfintech.tips.model;

import java.util.ArrayList;

/**
 * Created by Tanush on 4/5/2017.
 */

public class Tip {
    private String mSymName;
    private String mSymSide;
    private String mSymType;
    private String mLiveprc;
    private String mTargetprc;
    private String mStploss;
    private String mPostAdv;

    public Tip() {
    }

    public Tip(String mSymName, String mSymSide, String mSymType, String mLiveprc, String mTargetprc, String mStploss, String mPostAdv) {
        this.mSymName = mSymName;
        this.mSymSide = mSymSide;
        this.mSymType = mSymType;
        this.mLiveprc = mLiveprc;
        this.mTargetprc = mTargetprc;
        this.mStploss = mStploss;
        this.mPostAdv = mPostAdv;
    }

    public String getmSymName() {
        return mSymName;
    }

    public String getmSymSide() {
        return mSymSide;
    }

    public String getmSymType() {
        return mSymType;
    }

    public String getmLiveprc() {
        return mLiveprc;
    }

    public String getmTargetprc() {
        return mTargetprc;
    }

    public String getmStploss() {
        return mStploss;
    }

    public String getmPostAdv() {
        return mPostAdv;
    }

    private static int lastTipId = 0;

    public static ArrayList<Tip> createTipsList(int numTips) {
        ArrayList<Tip> tips = new ArrayList<Tip>();

        for (int i = 1; i <= numTips; i++) {
            tips.add(new Tip("Reliance " + ++lastTipId,"Buy @ 14.32","ACTIVE","14.23","24.32","33.23","Posted By: Tanush"));
        }
        return tips;
    }
}
