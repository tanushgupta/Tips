package com.symphonyfintech.tips.adapters.feedAdapter;

/**
 * @author neha.prakash
 * Copyright (c) 2012, Symphony Fintech. All Rights Reserved
 * @version 1.0
 */


import java.io.Serializable;

public class MarketPicturePriority implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	MarketPicture mp;
	long priority = 0;
	
	public MarketPicture getMp() {
		return mp;
	}
	public void setMp(MarketPicture mp) {
		this.mp = mp;
	}
	public void incrementPriority() {
		++priority;	
	}
	public void initPriority() {
		priority = 0;
	}
	
	public long getPriority(){
		return priority;
	}
	
	
}
