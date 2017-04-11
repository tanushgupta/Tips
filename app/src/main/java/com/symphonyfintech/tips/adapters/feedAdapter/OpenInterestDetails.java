package com.symphonyfintech.tips.adapters.feedAdapter;

import java.io.Serializable;

public class OpenInterestDetails  implements Serializable	{
		private long currentOpenInterest;// 4
		
		public String generateData() {

			String data = "";
			data = ("|" + currentOpenInterest);
			return data;
		}

		public long getCurrentOpenInterest() {
			return currentOpenInterest;
		}

		public void setCurrentOpenInterest(long currentOpenInterest) {
			this.currentOpenInterest = currentOpenInterest;
		}
		
		
	}