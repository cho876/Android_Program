package com.example.cho.multitab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 알람 시간이 되었을 시, Notification을 통한 동작 Class
 */

public class BroadcastR extends BroadcastReceiver {
    private JoinDbThread joinDbThread;
    private RegisterRequest registerRequest;

    @Override
    public void onReceive(Context context, Intent intent) {
        int index = intent.getExtras().getInt("index");
        joinDB(context, index);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, PowerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.vec_power)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("MULTI TAP")
                .setContentText("예약 설정 실행 중")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);

        manager.notify(1, builder.build());
    }

    private void joinDB(Context context, int index) {
        joinDbThread = new JoinDbThread(context, registerRequest);
        joinDbThread.setIndex(index);
        joinDbThread.setDaemon(true);
        joinDbThread.run();
    }
}
