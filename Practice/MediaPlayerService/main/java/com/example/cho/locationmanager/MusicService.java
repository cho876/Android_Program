package com.example.cho.locationmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String MESSEAGE_KEY = "1";   // intent 전환 시, 값 받아올 키 값

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean msg = intent.getExtras().getBoolean(MusicService.MESSEAGE_KEY);  // MainActivity로부터 boolean값 받아옴

        if (msg) {       // 재생 버튼 눌렀을 시,
            mediaPlayer = MediaPlayer.create(this, R.raw.prac);   // MediaPlayer 초기화 (raw폴더 내 prac.mp3)
            mediaPlayer.start();    // 재생

            Intent intentMain = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 1, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);

            /* Notification 설정  */
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("음악 플레이 테스트")
                            .setContentIntent(pendingIntent)
                            .setContentText("백그라운드 음악 실행중");

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(001, builder.build());

        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        return START_NOT_STICKY;       // 시스템에 의한 종료 시, 다시 실행되지 않음
    }
}
