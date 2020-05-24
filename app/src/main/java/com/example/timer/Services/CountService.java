package com.example.timer.Services;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.timer.R;
import com.example.timer.RecordActivity;
import com.example.timer.Utils.DateUtils;

import java.util.Observable;
import java.util.Observer;


public class CountService extends Service {
    int current;
    int state;
    private MyObservable myObservable;


    private NotificationCompat.Builder builder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CountBinder();
    }

    public void startCount(int cur){
        current=cur;
        state = 1;
        initialNotification();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (state==1){
                    try {
                        current++;
                        handler.sendMessage(handler.obtainMessage(0,current));
                        updateNotification();
                        Thread.sleep(1000);
                        Log.d("time",""+current);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

   public void stopCount(){
        removeNotification();
        state=0;
        current=0;
    }

    public int getState(){
        return state;
    }

    public class CountBinder extends Binder {
        public CountService getService(){
            return CountService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        state =0;
        myObservable = new MyObservable();
    }


    @Override
    public void onDestroy() {
        stopCount();
        removeNotification();
        Log.d("Service","destroy");
        super.onDestroy();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("timer", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initialNotification() {

        createNotificationChannel();
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("current",current);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this,"timer")
                .setSmallIcon(R.mipmap.pause)
                .setContentTitle("title")
                .setContentText(DateUtils.getFormatTimeFromSeconds(current))
                .setChannelId("timer")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());
    }


    private void removeNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
    }

    private void updateNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("current",current);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setContentText(DateUtils.getFormatTimeFromSeconds(current));
        notificationManager.notify(1,builder.build());
    }


    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //通知更新
            myObservable.notifyChanged(current);
        }
    };

    public class MyObservable extends Observable {

        public void notifyChanged(Object object) {
            this.setChanged();
            this.notifyObservers(object);
        }
    }
}
