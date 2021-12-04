package com.app.letsfocus.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.model.Music;

public class FocusActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private TextView musicFocusTv;
    private Button breakBtn;
    private boolean isFocus, isStart;
    private int musicPath;
    private MediaPlayer mediaPlayer;
    private String musicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        isFocus = false;
        musicName = "Chưa chọn nhạc";
        musicPath = 0;
        bindComponent();
        registerEvent();
    }

    private void bindComponent()
    {
        linearLayout = findViewById(R.id.musicPlayerLinearLayout);
        musicFocusTv = findViewById(R.id.musicFocus);
        breakBtn = findViewById(R.id.breakBtn);
    }

    private void registerEvent()
    {
        linearLayout.setOnClickListener(view -> {
            isFocus = false;
            Intent intent = new Intent(this, MusicActivity.class);
            intent.putExtra("musicName", musicName);
            intent.putExtra("musicPath", musicPath);
            startActivityForResult(intent, 1);
        });
        setBreakBtnEvent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(true) {
                musicName = data.getStringExtra("musicName");
                musicPath = data.getIntExtra("musicPath",0);
                musicFocusTv.setText(musicName);
            }
            if(isFocus == true) {
                isFocus = true;
                breakBtn.setText("Break");
            }
            else {
                isFocus = false;
                if(isStart) {
                    breakBtn.setText("Continue");
                }
                else {
                    breakBtn.setText("Start");
                }
            }
        }
    }

    private void setBreakBtnEvent() {
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                if(isFocus == false) {
                    isFocus = true;
                    breakBtn.setText("Break");
                    if(musicPath != 0) {
                        playMusic();
                    }
                }
                else {
                    isFocus = false;
                    breakBtn.setText("Continue");
                    pauseMusic();
                }
            }
        });
    }

    private void playMusic()
    {
        mediaPlayer = MediaPlayer.create(getBaseContext(),musicPath);
        mediaPlayer.start();
    }
    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}