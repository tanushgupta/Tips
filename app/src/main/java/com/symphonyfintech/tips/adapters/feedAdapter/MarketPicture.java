package com.symphonyfintech.tips.adapters.feedAdapter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


//simple entity class to temporarily store data

public class MarketPicture implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "MarketPicture [startPos=" + startPos + ", messageSize="
				+ Arrays.toString(messageSize) + ", broadcastMessageHeader="
				+ broadcastMessageHeader + ", reserved1=" + reserved1
				+ ", instrumentIdentifier=" + instrumentIdentifier
				+ ", reserved2=" + reserved2 + ", indexFlag=" + indexFlag
				+ ", totalQuantity=" + totalQuantity + ", lastTradedPrice="
				+ lastTradedPrice + ", lastTradedQuantity="
				+ lastTradedQuantity + ", lastTradeTime=" + lastTradeTime
				+ ", averageTradedPrice=" + averageTradedPrice
				+ ", orderByPriceBuy=" + Arrays.toString(orderByPriceBuy)
				+ ", orderByPriceSell=" + Arrays.toString(orderByPriceSell)
				+ ", totalBuyQuantity=" + totalBuyQuantity
				+ ", totalSellQuantity=" + totalSellQuantity + ", reserved3="
				+ Arrays.toString(reserved3) + ", closePrice=" + closePrice
				+ ", openPrice=" + openPrice + ", highPrice=" + highPrice
				+ ", lowPrice=" + lowPrice + ", reserved4=" + reserved4
				+ ", openInterestDetails=" + openInterestDetails
				+ ", totalTrades=" + totalTrades + ", highestPriceEver="
				+ highestPriceEver + ", lowestPriceEver=" + lowestPriceEver
				+ ", totalTradedValue=" + totalTradedValue + "]";
	}


	int startPos;

	// Total size: 253

	// size = 31
	char[] messageSize; // 5
	BroadcastMessageHeader broadcastMessageHeader; // 21
	byte reserved1; // numeric:1
	long instrumentIdentifier; // 4

	// size = 22
	char reserved2; // 1:String
	char indexFlag; // 1:String
	long totalQuantity; // 4
	long lastTradedPrice; // 4
	long lastTradedQuantity; // 4
	long lastTradeTime; // 4 : Time in form of Seconds from 01-01-1970
	long averageTradedPrice; // 4

	
	// size = 140
	OrderByPrice orderByPriceBuy[] = new OrderByPrice[5]; // 70
	OrderByPrice orderByPriceSell[] = new OrderByPrice[5];// 70

	// size = 34
	double totalBuyQuantity;// 8
	double totalSellQuantity; // 8
	char reserved3[] = { 'A', 'B' }; // 2
	long closePrice; // 4
	long openPrice; // 4
	long highPrice; // 4
	long lowPrice; // 4


	// size = 2
	short reserved4; // 2

	// size = 24
	OpenInterestDetails openInterestDetails; // 4
	long totalTrades; // 4
	long highestPriceEver; // 4
	long lowestPriceEver; // 4
	
	
	double totalTradedValue; // 8
	
	public String getString() {
		StringBuilder data = new StringBuilder();
		String data1;

		data1 = broadcastMessageHeader.generateData();

		data.append(data1);
		data.append("|" + reserved1);
		data.append("|" + instrumentIdentifier);
		data.append("|" + reserved2);
		data.append("|" + indexFlag);

		data.append("|" + totalQuantity);
		data.append("|" + lastTradedPrice);
		data.append("|" + lastTradedQuantity);
		data.append("|" + lastTradeTime);
		data.append("|" + averageTradedPrice);

		for (int i = 0; i < 5; i++) {
			data1 = orderByPriceBuy[i].generateData();
			data.append(data1);
		}

		for (int i = 0; i < 5; i++) {
			data1 = orderByPriceSell[i].generateData();
			data.append(data1);
		}

		data.append("|" + totalBuyQuantity);
		data.append("|" + totalSellQuantity);

		data.append("|" + new String(reserved3));
		data.append("|" + closePrice);
		data.append("|" + openPrice);
		data.append("|" + highPrice);
		data.append("|" + lowPrice);

		data.append("|" + (reserved4));

		data.append("|" + openInterestDetails.generateData());

		data.append("|" + totalTrades);
		data.append("|" + highestPriceEver);
		data.append("|" + lowestPriceEver);
		data.append("|" + totalTradedValue + "\n\n");

		return data.toString().trim();

	}

	public char[] getMessageSize() {
		return messageSize;
	}

	public void setMessageSize(char[] messageSize) {
		this.messageSize = messageSize;
	}

	public BroadcastMessageHeader getBroadcastMessageHeader() {
		return broadcastMessageHeader;
	}

	public void setBroadcastMessageHeader(
			BroadcastMessageHeader broadcastMessageHeader) {
		this.broadcastMessageHeader = broadcastMessageHeader;
	}

	public byte getReserved1() {
		return reserved1;
	}

	public void setReserved1(byte reserved1) {
		this.reserved1 = reserved1;
	}

	public long getInstrumentIdentifier() {
		return instrumentIdentifier;
	}

	public void setInstrumentIdentifier(long instrumentIdentifier) {
		this.instrumentIdentifier = instrumentIdentifier;
	}

	public char getReserved2() {
		return reserved2;
	}

	public void setReserved2(char reserved2) {
		this.reserved2 = reserved2;
	}

	public char getIndexFlag() {
		return indexFlag;
	}

	public void setIndexFlag(char indexFlag) {
		this.indexFlag = indexFlag;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public long getLastTradedPrice() {
		return lastTradedPrice;
	}

	public void setLastTradedPrice(long lastTradedPrice) {
		this.lastTradedPrice = lastTradedPrice;
	}

	public long getLastTradedQuantity() {
		return lastTradedQuantity;
	}

	public void setLastTradedQuantity(long lastTradedQuantity) {
		this.lastTradedQuantity = lastTradedQuantity;
	}

	public long getLastTradeTime() {
		return lastTradeTime;
	}

	public void setLastTradeTime(long lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	public long getAverageTradedPrice() {
		return averageTradedPrice;
	}

	public void setAverageTradedPrice(long averageTradedPrice) {
		this.averageTradedPrice = averageTradedPrice;
	}

	public OrderByPrice[] getOrderByPriceBuy() {
		return orderByPriceBuy;
	}

	public void setOrderByPriceBuy(OrderByPrice[] orderByPriceBuy) {
		this.orderByPriceBuy = orderByPriceBuy;
	}

	public OrderByPrice[] getOrderByPriceSell() {
		return orderByPriceSell;
	}

	public void setOrderByPriceSell(OrderByPrice[] orderByPriceSell) {
		this.orderByPriceSell = orderByPriceSell;
	}

	public double getTotalBuyQuantity() {
		return totalBuyQuantity;
	}

	public void setTotalBuyQuantity(double totalBuyQuantity) {
		this.totalBuyQuantity = totalBuyQuantity;
	}

	public double getTotalSellQuantity() {
		return totalSellQuantity;
	}

	public void setTotalSellQuantity(double totalSellQuantity) {
		this.totalSellQuantity = totalSellQuantity;
	}

	public char[] getReserved3() {
		return reserved3;
	}

	public void setReserved3(char[] reserved3) {
		this.reserved3 = reserved3;
	}

	public long getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(long closePrice) {
		this.closePrice = closePrice;
	}

	public long getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(long openPrice) {
		this.openPrice = openPrice;
	}

	public long getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(long highPrice) {
		this.highPrice = highPrice;
	}

	public long getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(long lowPrice) {
		this.lowPrice = lowPrice;
	}

	public short getReserved4() {
		return reserved4;
	}

	public void setReserved4(short reserved4) {
		this.reserved4 = reserved4;
	}

	public OpenInterestDetails getOpenInterestDetails() {
		return openInterestDetails;
	}

	public void setOpenInterestDetails(OpenInterestDetails openInterestDetails) {
		this.openInterestDetails = openInterestDetails;
	}

	public long getTotalTrades() {
		return totalTrades;
	}

	public void setTotalTrades(long totalTrades) {
		this.totalTrades = totalTrades;
	}

	public long getHighestPriceEver() {
		return highestPriceEver;
	}

	public void setHighestPriceEver(long highestPriceEver) {
		this.highestPriceEver = highestPriceEver;
	}

	public long getLowestPriceEver() {
		return lowestPriceEver;
	}

	public void setLowestPriceEver(long lowestPriceEver) {
		this.lowestPriceEver = lowestPriceEver;
	}

	public double getTotalTradedValue() {
		return totalTradedValue;
	}

	public void setTotalTradedValue(double totalTradedValue) {
		this.totalTradedValue = totalTradedValue;
	}
	
	
	/**
	 * Generate the byte array message from the MarketPicture message to send across the network.
	 * @return
	 */
	public byte[] setBuffer()
	{  
		//System.out.println("IN MARKETPICTURE SETBUFFER%%%%%%%%%%%%%%%%%%%%%%%%%%");
		ByteBuffer buffer;
		this.startPos = 0;
		buffer = ByteBuffer.allocate(253);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		//31
		buffer.put(new String(messageSize).getBytes()); //5
		buffer.put(broadcastMessageHeader.generateBCHData());//21
		buffer.put(reserved1); //1
		buffer.putInt((int)instrumentIdentifier); //4
		
		//22
		buffer.put((byte)(reserved2));
		buffer.put((byte)indexFlag);
		buffer.putInt((int)totalQuantity);
		buffer.putInt((int)lastTradedPrice);
		buffer.putInt((int)lastTradedQuantity);
		buffer.putInt((int)lastTradeTime);
		buffer.putInt((int)averageTradedPrice);
		
		//156
		byte[] buff1 = new byte[156];
		ByteBuffer buf = ByteBuffer.allocate(156);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		
		//System.out.println("#####orderByPriceBuy: "+orderByPriceBuy.length);
		//System.out.println("######orderByPriceSell: "+orderByPriceSell.length);
		
		for(OrderByPrice obp:orderByPriceBuy)
		{	//System.out.println("orderByPriceBuy data: "+obp.generateData());
			buf = obp.generateByteBuffer(buf);
		}
		for(OrderByPrice obp:orderByPriceSell)
		{
			//System.out.println("orderByPriceSell data: "+obp.generateData());
			buf = obp.generateByteBuffer(buf);
		}
				
		buf.putDouble(totalBuyQuantity);
		buf.putDouble(totalSellQuantity);
		//System.out.println("OrderByPrice buffer@@@@@@@@@@@: "+buf.remaining()+" &&& "+buf.toString());
		buff1 = buf.array();
        buffer.put(buff1);
       // System.out.println("buffer pos: "+buffer.toString()+"  rem "+buffer.remaining());
        //18
		buffer.put(new String(reserved3).getBytes());
		buffer.putInt((int)closePrice);
		buffer.putInt((int)openPrice);
		buffer.putInt((int)highPrice);
		buffer.putInt((int)lowPrice);
		//System.out.println("buffer pos: "+buffer.toString()+"  rem "+buffer.remaining());
		//2
		buffer.putShort(reserved4);
		
		//4
		buffer.putInt((int)(openInterestDetails.getCurrentOpenInterest()));
		
		//20
		buffer.putInt((int)totalTrades);
		buffer.putInt((int)highestPriceEver);
		buffer.putInt((int)lowestPriceEver);
		buffer.putDouble(totalTradedValue);
		//System.out.println("buffer pos: "+buffer.toString()+"  rem "+buffer.remaining());
		
		byte[] buff = new byte[253];
		
		buff = buffer.array();
		
		//System.out.println("RETURNING BUFF#############################"+buff.toString());
		return buff;
		
	}
	
	
	
}
