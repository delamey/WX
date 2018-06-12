package com.example.dell.wx;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;

public class AlarmService extends IntentService{
    private AlarmManager alarmManager;
    Intent intent1;
    PendingIntent pendingIntent;
    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final Vibrator vib = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        final long [] pattern = {100,400,100,400};
        new Thread(new Runnable() {
            @Override
            public void run() {
                vib.vibrate(pattern,3);
            }
        }).start();
         alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        int time=10000;
        long triggerArTime= SystemClock.elapsedRealtime()+time;
         intent1=new Intent(this,AlarmService.class);
        pendingIntent=PendingIntent.getService(this,0,intent1,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerArTime,pendingIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
    }
}
