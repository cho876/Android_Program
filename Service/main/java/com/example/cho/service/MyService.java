package com.example.cho.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    NotificationManager manager;
    ServiceThread thread;
    Notification notification;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        thread.stopForever();
        thread = null;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg){
            Intent intent = new Intent(MyService.this, MainActivity.class);
            /* PendingIntent : 직접 Intent를 넘기지 않고 다른 Class가 대신 전해줌 */
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            /*notification = 상단 바 알림 */
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Content Title")      // Title
                    .setContentText("Content Text")        // Contents
                    .setSmallIcon(R.mipmap.ic_launcher)    // Icon
                    .setTicker("알림1")   // 알림이 뜰때 잠깐 표시되는 Text
                    .setContentIntent(pendingIntent)  // 실행할 작업이 담긴 PendingIntent
                    .build();

            notification.defaults = notification.DEFAULT_SOUND;   // 알람 시, 소리 호출
            notification.flags = notification.FLAG_ONLY_ALERT_ONCE;    // 소리 1번만
            notification.flags = notification.FLAG_AUTO_CANCEL;       //
            manager.notify(111, notification);   // notification의 고유 아이디
            Toast.makeText(MyService.this, "알림", Toast.LENGTH_SHORT).show();
        }
    }
}
