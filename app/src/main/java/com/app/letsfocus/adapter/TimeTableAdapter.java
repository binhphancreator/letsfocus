package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.model.TimeTable;

import java.util.List;

public class TimeTableAdapter extends BaseAdapter {

    private List<Model> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TimeTableAdapter(Context context, List<Model> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Model getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return  Long.parseLong(listData.get(i).get("id"));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = layoutInflater.from(context).inflate(R.layout.card_timetable, viewGroup,false);
            Model timeTableModel = getItem(i);
            TextView nameTaskItemTv = view.findViewById(R.id.nameTaskItemTv);
            TextView timeTaskItemTv = view.findViewById(R.id.timeTaskItemTv);

            nameTaskItemTv.setText(timeTableModel.get("name"));
            timeTaskItemTv.setText(timeTableModel.get("start_time"));
        }
        return view;
    }

}
