package com.app.letsfocus.ui.fragment.timetable;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.letsfocus.R;
import com.app.letsfocus.app.MyNotificationChannel;
import com.app.letsfocus.ui.fragment.add_timetable.AddTimeTableFragment;

import java.util.Date;

public class TimeTableListFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_timetable_list, container, false);

        Button report1_btn = (Button) view.findViewById(R.id.add_timetable_btn);
        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new AddTimeTableFragment());
                fr.commit();
            }
        });

        Button push_btn = view.findViewById(R.id.push_notify);
        push_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        return view;
    }

    private void sendNotification(){
        Notification notification = new NotificationCompat.Builder(getContext(), MyNotificationChannel.CHANNEL_ID_1)
                .setContentTitle("Todo list")
                .setContentText("Còn 5 phút nữa đến giờ chạy bộ rồi")
                .setSmallIcon(R.drawable.ic_ring_circled)
                .build();

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(getNotifyId(),notification);
    }

    private int getNotifyId(){
        return (int) new Date().getTime();
    }
}
