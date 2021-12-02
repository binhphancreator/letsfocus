package com.app.letsfocus.ui.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.app.letsfocus.R;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
    }

    private void playMusic()
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.loyeu);
        mediaPlayer.start();
    }
}