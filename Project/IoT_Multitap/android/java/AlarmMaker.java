package com.example.cho.multitab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

/**
 * AlarmManager를 통한 알람 설정 Class
 */

public class AlarmMaker {
    private Context context;

    public AlarmMaker(Context context) {
        this.context = context;
    }

    /**
     * 예약 시간 / 분을 통한 예약 설정
     *
     * @param index 예약할 콘센트 번호
     * @param hour  예약 시
     * @param min   에약 분
     */
    public void setAlarm(int index, int hour, int min) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BroadcastR.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, index, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, min, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    /**
     * 예약한 Alarm 해체
     *
     * @param index 예약 해제할 콘센트 번호
     */
    public void cancelAlarm(int index) {
        Intent intent = new Intent(context, BroadcastR.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, index, intent, 0);
        sender.cancel();
    }
}
