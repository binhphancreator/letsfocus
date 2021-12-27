package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.model.App;

import java.util.ArrayList;

public class AppAdapter extends ArrayAdapter<App> {

    public AppAdapter(Context context, ArrayList<App> usageStatArrayList) {
        super(context, 0, usageStatArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // lay du lieu theo vi tri
        App usageStats = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item_app, parent, false);
        }

        TextView app_name_tv = convertView.findViewById(R.id.app_name_tv);
        TextView usage_duration_tv =  convertView.findViewById(R.id.usage_duration_tv);
        TextView usage_perc_tv = convertView.findViewById(R.id.usage_perc_tv);
        ImageView icon_img =  convertView.findViewById(R.id.icon_img);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        app_name_tv.setText(usageStats.appName);
        usage_duration_tv.setText(usageStats.usageDuration);
        usage_perc_tv.setText(usageStats.usagePercentage + "%");
        icon_img.setImageDrawable(usageStats.appIcon);
        progressBar.setProgress(usageStats.usagePercentage);

        return convertView;
    }
}