package com.app.letsfocus.core;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.app.letsfocus.R;

import java.util.Date;

public class RemiderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }

    private void sendNotification(Context context){
        Notification notification = new NotificationCompat.Builder(context, NoticeChannel.CHANNEL_ID_1)
                .setContentTitle("Todo list")
                .setContentText("Có công việc cần làm sau 5 phút nữa, check app ngay nhé!")
                .setSmallIcon(R.drawable.ic_alarm_24)
                .build();

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(getNotifyId(),notification);
    }

    private int getNotifyId(){
        return (int) new Date().getTime();
    }
}
