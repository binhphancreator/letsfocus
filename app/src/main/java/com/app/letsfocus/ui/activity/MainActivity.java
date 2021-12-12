package com.app.letsfocus.ui.activity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.databinding.ActivityMainBinding;
import com.app.letsfocus.ui.fragment.home.HomeFragment;
import com.app.letsfocus.ui.fragment.report1.Report1Fragment;
import com.app.letsfocus.ui.fragment.setting.SettingFragment;
import com.app.letsfocus.ui.fragment.timetable.TimeTableListFragment;

public class MainActivity extends AppCompatActivity {

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
    }

    public void loadFragment(Fragment fragment) {
        Helper.loadFragment(R.id.nav_host_fragment_activity_main, fragment, getSupportFragmentManager());
    }
}