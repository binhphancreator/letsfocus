package com.app.letsfocus.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

public class Helper {
    public static void loadFragment(int id, Fragment fragment, FragmentManager fragmentManager)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.detach(Objects.requireNonNull(fragmentManager.findFragmentById(id)));
        transaction.replace(id, fragment);
        transaction.commitNow();
    }

    public static void loadToolbar(AppCompatActivity activity, int id)
    {
        Toolbar myToolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(myToolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }
}
