package com.symphonyfintech.tips.adapters.feedAdapter;

import org.zeromq.ZMQ;

import java.util.Random;

public class Pubserver {
	static final String d = "done";
  
  public static void main(String[] args) {
      ZMQ.Context ctx = ZMQ.context(1);
      ZMQ.Socket publisher = ctx.socket(ZMQ.PUSH);
      publisher.bind("tcp://192.168.50.72:5556");
      
      Random random = new Random();
      while (true) {
        int id = random.nextInt(100000);
        int data = random.nextInt(500);
        publisher.send(String.format("%05d %d", id, data));
        try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//        System.out.println(id+" -- "+ data);
      }
  }
}