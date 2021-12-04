
package com.app.letsfocus.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.model.Music;

import java.util.List;

public class MusicAdapter extends BaseAdapter {
    private List<Music> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MusicAdapter(Context context, List<Music> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.card_music, viewGroup,false);
            TextView musicName = (TextView) view.findViewById(R.id.musicName);
            TextView singer = (TextView) view.findViewById(R.id.singer);
            ImageView musicImg = (ImageView) view.findViewById(R.id.musicImg);

            Music music = listData.get(i);

            musicName.setText(music.getMusicName());
            singer.setText(music.getMusicSinger());
            musicImg.setImageResource(music.getMusicImage());
        }
        return view;
    }
}
