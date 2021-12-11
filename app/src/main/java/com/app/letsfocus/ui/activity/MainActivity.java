package com.app.letsfocus.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.core.RemiderBroadcast;
import com.app.letsfocus.databinding.ActivityMainBinding;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.home.HomeFragment;
import com.app.letsfocus.ui.fragment.report1.Report1Fragment;
import com.app.letsfocus.ui.fragment.setting.SettingFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Model> listTime = new ArrayList<>();
    PendingIntent pendingIntent;
    Intent intent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.app.letsfocus.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Helper.loadToolbar(this, R.id.my_toolbar);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.navigation_home) {
                loadFragment(new HomeFragment());
            } else if(id == R.id.navigation_timetable) {
                loadFragment(new TimeTableListFragment());
            } else if(id == R.id.navigation_report) {
                loadFragment(new Report1Fragment());
            } else if(id==R.id.navigation_profile) {
                loadFragment(new SettingFragment());
            }
            return true;
        });

        configAlert();
    }

    private void configAlert(){
        listTime = new ToDo(this).all();
        String todoTime = "";
        for(int i= 0; i<listTime.size(); i++){
            int hour, minute;
            todoTime = String.valueOf(listTime.get(i).get("time"));
            Log.e("check10", todoTime);
            String[] str = todoTime.split(":");
            hour = Integer.parseInt(str[0]);
            minute = Integer.parseInt(str[1]);
            Log.e("check10", str[0]);
            Log.e("check10", str[1]);
            startAlert(hour,minute);

            todoTime = "";
        }
    }
    private void startAlert(int h , int m) {
        intent =new Intent(MainActivity.this, RemiderBroadcast.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m-5);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    public void loadFragment(Fragment fragment) {
        Helper.loadFragment(R.id.nav_host_fragment_activity_main, fragment, getSupportFragmentManager());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


}