package com.app.letsfocus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.letsfocus.R;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private List<Object> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TaskAdapter(Context context, List<Object> listData) {
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
            view = layoutInflater.from(context).inflate(R.layout.card_task, viewGroup,false);
            TextView taskName = (TextView) view.findViewById(R.id.taskName);

        }
        return null;
    }
}
