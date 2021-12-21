package com.app.letsfocus.core;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.letsfocus.R;
import com.app.letsfocus.ui.activity.MainActivity;

import java.util.Date;

public class RemiderBroadcast extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    Boolean b;
    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("switch_state",Context.MODE_PRIVATE);
        b = sharedPreferences.getBoolean("state",true);
        if(b){
            sendNotification(context);
        }
    }

    private void sendNotification(Context context){
        Intent notifyIntent = new Intent(context, MainActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context,0, notifyIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, NoticeChannel.CHANNEL_ID_1)
                .setContentTitle("Todo list")
                .setContentText("Có công việc cần làm bây giờ check app ngay nhé!")
                .setSmallIcon(R.drawable.ic_alarm_24)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(getNotifyId(),notification);
    }

    private int getNotifyId(){
        return (int) new Date().getTime();
    }
}
