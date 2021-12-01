package com.app.letsfocus.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.app.letsfocus.R;

import java.util.Objects;

public class Helper {
    public static void loadFragment(int id, Fragment fragment, AppCompatActivity activity)
    {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.detach(Objects.requireNonNull(activity.getSupportFragmentManager().findFragmentById(id)));
        transaction.replace(id, fragment);
        transaction.commitNow();
    }
}
