package com.app.letsfocus.ui.Profile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letsfocus.R;

import java.util.ArrayList;

public class ProfileAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<title> items;

    //public constructor
    public ProfileAdapter(Context context, ArrayList<title> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.profile_list, parent, false);
        }

        title currentItem = (title) getItem(position);

        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.text_profile);
        ImageView image = (ImageView) convertView.findViewById(R.id.go_arrow);


        if(currentItem.getItemName().equals("Sign out")){
            textViewItemName.setText(currentItem.getItemName());
            textViewItemName.setTextColor(Color.parseColor("#F00D00"));
            image.setImageResource(0);
        } else {
            textViewItemName.setTextColor(Color.parseColor("#000000"));
            textViewItemName.setText(currentItem.getItemName());
            image.setImageResource(R.drawable.ic_action_arrow);
        }

        return convertView;
    }
}

