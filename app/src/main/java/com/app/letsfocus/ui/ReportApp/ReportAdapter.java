package com.app.letsfocus.ui.ReportApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letsfocus.R;

import java.util.ArrayList;

public class ReportAdapter extends BaseExpandableListAdapter {
    private Context c;
    private ArrayList<groupItem> Gritem;

    public ReportAdapter(Context con, ArrayList<groupItem> Gritem){
        this.c = con;
        this.Gritem = Gritem;
    }
    @Override
    public int getGroupCount() {
        return Gritem.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Gritem.get(groupPosition).child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Gritem.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Gritem.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.
                    inflate(R.layout.report_parent_group, parent, false);
        }
        groupItem currentItem = (groupItem) getGroup(groupPosition);

        ImageView dotted =(ImageView) convertView.findViewById(R.id.dot);
        TextView content =(TextView) convertView.findViewById(R.id.listTitle);
        ImageView arrow =(ImageView) convertView.findViewById(R.id.arrow_down);

        String name = currentItem.name;

        if(name == "Social media") {
            dotted.setColorFilter(Color.parseColor("#3FDA8D"));
            content.setText(name);
            if (isExpanded) {
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }

        if(name == "Gaming app") {
            dotted.setColorFilter(Color.parseColor("#75D6FF"));
            content.setText(name);
            if (isExpanded) {
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }

        if(name == "Study app") {
            dotted.setColorFilter(Color.parseColor("#FFD673"));
            content.setText(name);
            if (isExpanded) {
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }

        if(name == "Health & Fitness") {
            dotted.setColorFilter(Color.parseColor("#FD6969"));
            content.setText(name);
            if (isExpanded) {
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }

        if(name == "Finance") {
            dotted.setColorFilter(Color.parseColor("#FCFF7B"));
            content.setText(name);
            if (isExpanded) {
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }
        }

        if(name == "Books & Reference"){
            dotted.setColorFilter(Color.parseColor("#5E86EC"));
            content.setText(name);
            if(isExpanded){
                arrow.setImageResource(R.drawable.ic_action_arrow_up);
            } else {
                arrow.setImageResource(R.drawable.ic_action_arrow_down);
            }


        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.report_child_item, null);
        }

        childItem item = (childItem) getChild(groupPosition, childPosition);

        String name = item.name;
        String time= item.time;

        TextView text1 = (TextView) convertView.findViewById(R.id.ChildlistTitle);
        ImageView image1 = (ImageView) convertView.findViewById(R.id.app_avt);
        TextView text2 = (TextView) convertView.findViewById(R.id.time_text);

        if(name.equals("Facebook")){
            text1.setText(name);
            image1.setImageResource(R.drawable.face);
            text2.setText(time);
        } else {
            text1.setText(name);
            text2.setText(time);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

