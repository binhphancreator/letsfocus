package com.app.letsfocus.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.MusicAdapter;
import com.app.letsfocus.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private GridView gridView;
    private String musicName, oldMusicName;
    private int musicPath, oldMusicPath;
    private List<Music> listData;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        bindComponent();
        render();
        registerEvent();
        intent = getIntent();
        oldMusicName = intent.getStringExtra("musicName");
        oldMusicPath = intent.getIntExtra("musicPath",0);
        Toast.makeText(this,oldMusicName, Toast.LENGTH_SHORT);
    }

    private void bindComponent()
    {
        gridView = findViewById(R.id.listMusicGridView);
    }

    private void render()
    {
        listData = new ArrayList<>();
        listData.add(new Music("Không có nhạc", "", R.drawable.nosong, 0));
        listData.add(new Music());
        listData.add(new Music("Bài số 2", "Unknown", R.drawable.song2, R.raw.song2));
        listData.add(new Music("USSR Anthem", "Unknown", R.drawable.song3, R.raw.song3));
        listData.add(new Music("German Soldier's Song", "Unknown", R.drawable.song4, R.raw.song4));
        gridView.setAdapter(new MusicAdapter(this ,listData));
    }

    private void registerEvent()
    {
        gridView.setOnItemClickListener(((adapterView, view, i, l) -> {
            Music musicTemp = listData.get(i);
            musicName = musicTemp.getMusicName();
            musicPath = musicTemp.getMusingPath();
            backToFocus();
        }));
    }

    private void backToFocus() {
        intent.putExtra("musicName", musicName);
        intent.putExtra("musicPath", musicPath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("musicName", oldMusicName);
        intent.putExtra("musicPath", oldMusicPath);
        setResult(1, intent);
        finish();
    }
}