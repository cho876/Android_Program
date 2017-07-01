package com.example.cho.service;

import android.os.Handler;

/**
 * Created by Cho on 2017-07-01.
 */

public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){           // Handler Setting
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }

    @Override
    public void run(){
        while(isRun){
            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(5000);              // 5초 단위로 Handler 호출
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
