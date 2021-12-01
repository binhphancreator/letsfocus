package com.app.letsfocus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.letsfocus.app.Helper;
import com.app.letsfocus.databinding.ActivityMainBinding;
import com.app.letsfocus.ui.home.HomeFragment;
import com.app.letsfocus.ui.profile.ProfileFragment;
import com.app.letsfocus.ui.report1.Report1Fragment;
import com.app.letsfocus.ui.timetable.TimeTableListFragment;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.app.letsfocus.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.navigation_home) {
                    loadFragment(new HomeFragment());
                } else if(id == R.id.navigation_timetable) {
                    loadFragment(new TimeTableListFragment());
                } else if(id == R.id.navigation_report) {
                    loadFragment(new Report1Fragment());
                } else if(id==R.id.navigation_profile) {
                    loadFragment(new ProfileFragment());
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        Helper.loadFragment(R.id.nav_host_fragment_activity_main, fragment, this);
    }
}