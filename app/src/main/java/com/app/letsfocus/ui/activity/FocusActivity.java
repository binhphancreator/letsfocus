package com.app.letsfocus.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.letsfocus.R;

public class FocusActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private TextView musicFocusTv;
    private Button breakBtn;
    private boolean isFocus, isStart;
    private int musicPath;
    private MediaPlayer mediaPlayer;
    private String musicName, oldMusicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        init();
        bindComponent();
        registerEvent();
    }

    private void init()
    {
        isFocus = false;
        musicName = "Chưa chọn nhạc";
        musicPath = 0;
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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            oldMusicName = musicName;
            musicName = data.getStringExtra("musicName");
            musicPath = data.getIntExtra("musicPath",0);
            musicFocusTv.setText(musicName);
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
                if(!isFocus) {
                    isFocus = true;
                    breakBtn.setText("Break");
                    if(musicPath != 0) {
                        if(musicName.equals(oldMusicName)) {
                            resumeMusic();
                        }
                        else{
                            playMusic();
                            oldMusicName = musicName;
                        }
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
        mediaPlayer.setLooping(true);
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void resumeMusic() {
        mediaPlayer.start();
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
        pauseMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }
}