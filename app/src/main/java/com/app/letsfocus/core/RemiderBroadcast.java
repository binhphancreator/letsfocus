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
                .setContentText("Chuẩn bị đến giờ chạy bộ rồi ")
                .setSmallIcon(R.drawable.ic_calendar)
                .build();

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(getNotifyId(),notification);
    }

    private int getNotifyId(){
        return (int) new Date().getTime();
    }
}
