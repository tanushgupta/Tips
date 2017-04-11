package com.symphonyfintech.tips.adapters.feedAdapter;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class OrderByPrice  implements Serializable	{
		private long quantity; // 4
		private long orderPrice; // 4
		private long totalNumberOfOrders; // 4
		private short reserved;// 2
	
		public String generateData()
		{
			StringBuilder data = new StringBuilder();
			data.append("|"+quantity);
			data.append("|"+orderPrice);
			data.append("|"+totalNumberOfOrders);
			data.append("|"+reserved);
			return data.toString().trim();
		}
		
		public ByteBuffer generateByteBuffer(ByteBuffer retBuf)
		{
			//byte[] b = new byte[14];
			retBuf.putInt((int)quantity).putInt(((int)orderPrice)).putInt((int)totalNumberOfOrders).putShort(reserved);			
			return retBuf;
		}

		public long getQuantity() {
			return quantity;
		}

		public void setQuantity(long quantity) {
			this.quantity = quantity;
		}

		public long getOrderPrice() {
			return orderPrice;
		}

		public void setOrderPrice(long orderPrice) {
			this.orderPrice = orderPrice;
		}

		public long getTotalNumberOfOrders() {
			return totalNumberOfOrders;
		}

		public void setTotalNumberOfOrders(long totalNumberOfOrders) {
			this.totalNumberOfOrders = totalNumberOfOrders;
		}

		public short getReserved() {
			return reserved;
		}

		public void setReserved(short reserved) {
			this.reserved = reserved;
		}
		
		
	}