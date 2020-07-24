package com.example.myrecipe;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.Serializable;
import static com.example.myrecipe.App.CHANNEL_ID;
public class MyFoodService extends Service implements Serializable {

    private static final String TAG = "MyFoodService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public MyFoodService() {
        super();
        //setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Log.d(TAG, "Wakelock acquired");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("my recipe")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();

            startForeground(1, notification);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "on Destroy MyFoodService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input=intent.getStringExtra("textToService");
        Intent notificationIntent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("My Food Service").setContentText(input).setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        return START_NOT_STICKY;

    }




//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.i(TAG, "*****************************************************************************onHandleIntent");
//        String input=intent.getStringExtra("textToService");
//        Intent notificationIntent=new Intent(this,MainActivity.class);
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);
//        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
//                .setContentTitle("My Food Service").setContentText(input).setSmallIcon(R.drawable.ic_android)
//                .setContentIntent(pendingIntent).build();
//        startForeground(1,notification);
//
//
//    }
}
