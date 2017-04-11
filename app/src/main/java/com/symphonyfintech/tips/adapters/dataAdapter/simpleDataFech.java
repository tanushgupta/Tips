package com.symphonyfintech.tips.adapters.dataAdapter;

import com.symphonyfintech.tips.adapters.feedAdapter.MarketPicture;
import com.symphonyfintech.tips.adapters.feedAdapter.Parser;
import com.symphonyfintech.tips.view.general.HomeActivity;

import org.zeromq.ZMQ;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class simpleDataFech implements Runnable {
    String Id;
    public simpleDataFech(String id) {
        this.Id =id;
    }
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            Parser extractor =  new Parser(ByteOrder.LITTLE_ENDIAN);
            ZMQ.Socket subscriber = HomeActivity.ctx.socket(ZMQ.SUB);
            subscriber.connect("tcp://103.69.169.10:5289");
            subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
//            subscriber.subscribe(Id.getBytes());
//            subscriber.subscribe("2885".getBytes());
//      System.out.println(subscriber.getTCPKeepAlive() > 0 ? true  : false);
            while (true) {
                byte[] msg = subscriber.recv();
                ByteBuffer buffer = ByteBuffer.wrap(msg);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
//                System.out.println("******** "+buffer.getInt(35));
                final MarketPicture result = extractor.fillMarketPicture(msg, 0);
//                System.out.println("Key : "+result.getInstrumentIdentifier() +" Value :"+(result.getLastTradedPrice()/100));
                HomeActivity.marketData.put(result.getInstrumentIdentifier() , Double.parseDouble(result.getLastTradedPrice()+""));
//                System.out.println(result.toString());
            }
        }
    }