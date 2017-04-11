package com.symphonyfintech.tips.adapters.feedAdapter;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BroadcastMessageHeader  implements Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private long exchangeTimeStamp; // 4
		private short messageCode; // 2
		private short reserved1; // 2
		private char[] reserved2; // 9
		private short messageSize; // 2
		private short reserved3; // 2
		
		public String generateData() {

			
			StringBuilder data = new StringBuilder();

			data.append("" + exchangeTimeStamp);
			data.append("|" + messageCode);
			data.append("|" + reserved1);
			data.append("|" + new String(reserved2));
			data.append("|" + messageSize);
			data.append("|" + reserved3);

			return data.toString();
		}

		public long getExchangeTimeStamp() {
			return exchangeTimeStamp;
		}

		public void setExchangeTimeStamp(long exchangeTimeStamp) {
			this.exchangeTimeStamp = exchangeTimeStamp;
		}

		public short getMessageCode() {
			return messageCode;
		}

		public void setMessageCode(short messageCode) {
			this.messageCode = messageCode;
		}

		public short getReserved1() {
			return reserved1;
		}

		public void setReserved1(short reserved1) {
			this.reserved1 = reserved1;
		}

		public char[] getReserved2() {
			return reserved2;
		}

		public void setReserved2(char[] reserved2) {
			this.reserved2 = reserved2;
		}

		public short getMessageSize() {
			return messageSize;
		}

		public void setMessageSize(short messageSize) {
			this.messageSize = messageSize;
		}

		public short getReserved3() {
			return reserved3;
		}

		public void setReserved3(short reserved3) {
			this.reserved3 = reserved3;
		}
		
		public byte[] generateBCHData() {

			
			//String data1 = ""+exchangeTimeStamp+"|"+messageCode+"|"+reserved1+"|"+new String(reserved2)+"|"+messageSize+"|"+reserved3;
			
					
			ByteOrder bo = ByteOrder.LITTLE_ENDIAN;
			ByteBuffer buffer = ByteBuffer.allocate(21);
			buffer.order(bo);
			
			//System.out.println("BCH: "+data1+"\n");
			buffer.putInt((int)exchangeTimeStamp).putShort(messageCode).putShort(reserved1).put(new String(reserved2).getBytes()).putShort(messageSize).putShort(reserved3);
			//System.out.println("generateBCHData: "+buffer.capacity()+" "+buffer.toString());
			byte[] buff = new byte[21];
			buff = buffer.array();
			return buff;
			
		}
		
	}