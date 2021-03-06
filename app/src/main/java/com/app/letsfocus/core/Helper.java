package com.app.letsfocus.core;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class Helper {
    public static void loadFragment(int id, Fragment fragment, FragmentManager fragmentManager)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment, null);
        transaction.setReorderingAllowed(true);
        transaction.commit();
    }

    public static void loadToolbar(AppCompatActivity activity, int id)
    {
        Toolbar myToolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(myToolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    public static long convertTimeStringToSecond(String timeStr)
    {
        if(!timeStr.contains(":")) return 0;
        String[] times = timeStr.split(":");
        long hours = Long.parseLong(times[0]);
        long minutes = Long.parseLong(times[1]);
        return hours * 3600 + minutes * 60;
    }

    public static String convertSecondToTimeString(long seconds)
    {
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes = minutes % 60;

        String hourStr, minuteStr, secondStr;

        if(hours < 10) hourStr = String.format("0%s", hours);
        else hourStr = String.valueOf(hours);

        if(minutes < 10) minuteStr = String.format("0%s", minutes);
        else minuteStr = String.valueOf(minutes);

        if(seconds < 10) secondStr = String.format("0%s", seconds);
        else secondStr = String.valueOf(seconds);

        if(hours == 0) return String.format("%s : %s", minuteStr, secondStr);
        return String.format("%s : %s : %s", hourStr, minuteStr, secondStr);
    }
}
