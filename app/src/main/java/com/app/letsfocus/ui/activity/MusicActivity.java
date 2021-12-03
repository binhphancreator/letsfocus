package com.app.letsfocus.ui.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.MusicAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        bindComponent();
        render();
        registerEvent();
    }

    private void bindComponent()
    {
        gridView = findViewById(R.id.listMusicGridView);
    }

    private void render()
    {
        List<Object> listData = new ArrayList<>();
        listData.add(new Object());
        listData.add(new Object());
        listData.add(new Object());
        listData.add(new Object());
        gridView.setAdapter(new MusicAdapter(this ,listData));
    }

    private void registerEvent()
    {
        gridView.setOnItemClickListener(((adapterView, view, i, l) -> {
            playMusic();
        }));
    }

    private void playMusic()
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.loyeu);
        mediaPlayer.start();
    }
}