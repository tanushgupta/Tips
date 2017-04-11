package com.symphonyfintech.tips.adapters.feedAdapter;

import java.nio.*;

//class that extracts data from MCX byte packed message.
//As long as the message structure doesn't change (no new fields or deletion
//of fields) its best left unchanged.
//a few gotchas:
//the char size is two so u should not try to directly read using buffer.getChar
//always cast from byte to char
//the long is 8 bytes so read as int and up cast to long
public class Parser {

	int startPos;
	ByteBuffer buffer;
	ByteOrder bo;

	// size definitions
	final int sizeOfBCHeader = 21;
	final int sizeOfOBP = 14;
	final int sizeOfOpenID = 4;

	public Parser(ByteOrder bo) {
		this.bo = bo;
	}

	public void setBuffer(byte[] byteArray, int start) {
		this.startPos = start;
		buffer = ByteBuffer.wrap(byteArray);
		buffer.order(bo);
	}
	
	

	public MarketPicture fillMarketPicture(byte[] byteArray, int start)

	{

		this.startPos = start+8;
		buffer = ByteBuffer.wrap(byteArray);
		buffer.order(bo);
//        System.out.println(byteArray);
		int pos = startPos;
		MarketPicture mp = new MarketPicture();

		mp.setMessageSize(_retrieveCharArray(pos, 5));

		pos += 5;
		mp.setBroadcastMessageHeader(_fillBCastHeader(pos));
		pos += sizeOfBCHeader;
		mp.setReserved1(buffer.get(pos));
		pos++;
		mp.setInstrumentIdentifier(buffer.getInt(pos));
		pos += 4;

		mp.setReserved2((char) buffer.get(pos));
		pos++;
		mp.setIndexFlag((char) buffer.get(pos));
		pos++;
		mp.setTotalQuantity(buffer.getInt(pos));
		pos += 4;
		mp.setLastTradedPrice(buffer.getInt(pos));
		pos += 4;
		mp.setLastTradedQuantity(buffer.getInt(pos));
		pos += 4;
		mp.setLastTradeTime(buffer.getInt(pos));
		pos += 4;
		mp.setAverageTradedPrice(buffer.getInt(pos));
		pos += 4;

		mp.setOrderByPriceBuy(_getMPArray(pos, 5));
		pos += 5 * sizeOfOBP;
		mp.setOrderByPriceSell(_getMPArray(pos, 5));
		pos += 5 * sizeOfOBP;

		mp.setTotalBuyQuantity(buffer.getDouble(pos));
		pos += 8;
		mp.setTotalSellQuantity(buffer.getDouble(pos));
		pos += 8;
		mp.setReserved3(_retrieveCharArray(pos, 2));
		pos += 2;
		mp.setClosePrice(buffer.getInt(pos));
		pos += 4;
		mp.setOpenPrice(buffer.getInt(pos));
		pos += 4;
		mp.setHighPrice(buffer.getInt(pos));
		pos += 4;
		mp.setLowPrice(buffer.getInt(pos));
		pos += 4;

		mp.setReserved4(buffer.getShort());
		pos += 2;

		mp.setOpenInterestDetails(_fillOpenInterest(pos));
		pos += sizeOfOpenID;
		mp.setTotalTrades(buffer.getInt(pos));
		pos += 4;
		mp.setHighestPriceEver(buffer.getInt(pos));
		pos += 4;
		mp.setLowestPriceEver(buffer.getInt(pos));
		pos += 4;
		mp.setTotalTradedValue(buffer.getDouble(pos));
		pos += 8;

		return mp;
	}

	BroadcastMessageHeader _fillBCastHeader(int pos) {
		BroadcastMessageHeader bch = new BroadcastMessageHeader();
		bch.setExchangeTimeStamp(buffer.getInt(pos));
		pos += 4;
		bch.setMessageCode(buffer.getShort(pos));
		pos += 2;
		bch.setReserved1(buffer.getShort(pos));
		pos += 2;
		bch.setReserved2(_retrieveCharArray(pos, 9));
		pos += 9;
		bch.setMessageSize(buffer.getShort(pos));
		pos += 2;
		bch.setReserved3(buffer.getShort(pos));

		return bch;
	}

	OrderByPrice[] _getMPArray(int pos, int arraySize) {
		OrderByPrice[] obpa = new OrderByPrice[arraySize];
		for (int i = 0; i < arraySize; i++) {
			obpa[i] = _fillOrderByPrice(pos + (i * sizeOfOBP));
		}

		return obpa;
	}

	OpenInterestDetails _fillOpenInterest(int pos) {
		OpenInterestDetails oid = new OpenInterestDetails();
		oid.setCurrentOpenInterest(buffer.getInt(pos));

		return oid;
	}

	OrderByPrice _fillOrderByPrice(int pos) {
		OrderByPrice obp = new OrderByPrice();
		obp.setQuantity(buffer.getInt(pos));
		pos += 4;
		obp.setOrderPrice(buffer.getInt(pos));
		pos += 4;
		obp.setTotalNumberOfOrders(buffer.getInt(pos));
		pos += 4;
		obp.setReserved(buffer.getShort(pos));

		return obp;
	}

	char[] _retrieveCharArray(int pos, int length) {
		char[] _tmp = new char[length];
		for (int ix = 0; ix < length; ix++)
			_tmp[ix] = (char) buffer.get(ix + pos);
		return _tmp;
	}

}
