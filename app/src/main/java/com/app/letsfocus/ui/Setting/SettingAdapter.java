package com.app.letsfocus.ui.Setting;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letsfocus.R;

import java.util.ArrayList;
public class SettingAdapter extends ArrayAdapter {
    public Context context;
    public ArrayList<SettingList> List;

    public SettingAdapter(Context con, ArrayList<SettingList> List){
        super(con, R.layout.setting_listitems, List);
        this.context = con;
        this.List = List;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.setting_listitems, parent, false);
        }

        // get current item to be displayed
        SettingList currentItem = (SettingList) getItem(position);

        // get the TextView for item name and item description
        TextView title = (TextView)
                convertView.findViewById(R.id.title_text);
        TextView detail = (TextView)
                convertView.findViewById(R.id.detail_text);

        title.setText(currentItem.getItemTitle());
        detail.setText(currentItem.getItemDetail());
        return convertView;
    }
}

