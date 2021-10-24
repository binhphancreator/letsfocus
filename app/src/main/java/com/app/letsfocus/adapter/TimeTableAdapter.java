package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letsfocus.R;

import java.util.List;

public class TimeTableAdapter extends BaseAdapter {

    private List<Object> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TimeTableAdapter(Context context, List<Object> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = layoutInflater.from(context).inflate(R.layout.card_timetable, viewGroup,false);
            TextView event = (TextView) view.findViewById(R.id.event);
            TextView time = (TextView) view.findViewById(R.id.time);

        }
        return null;
    }
}
