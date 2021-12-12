package com.app.letsfocus.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.letsfocus.R;
import com.app.letsfocus.core.ColorfulRingProgressView;
import com.app.letsfocus.core.Helper;
import com.app.letsfocus.core.Model;
import com.app.letsfocus.model.ToDo;

public class FocusActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private TextView musicFocusTv;
    private Button breakBtn, finishBtn;
    private boolean isFocus, isStart;
    private int musicPath;
    private MediaPlayer mediaPlayer;
    private String musicName, oldMusicName;
    private ColorfulRingProgressView timerProgress;
    private TextView timerTextView;
    private long totalTime;
    private long timeTicked;
    private CountDownTimer countDownTimer;
    private Model toDoModel;
    private ToDo toDoTmp = new ToDo(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        bindComponent();
        init();
        registerEvent();
    }

    private void init()
    {
        isFocus = false;
        musicName = "Chưa chọn nhạc";
        musicPath = 0;
        totalTime = 20000;
        timeTicked = 0;
        timerProgress.setPercent(0);
        toDoModel = new ToDo(this).find(getIntent().getStringExtra("todoId"));
        totalTime = Helper.convertTimeStringToSecond(toDoModel.get("duration"));
        timerTextView.setText(toDoModel.get("duration"));
        countDownTimer = new CountDownTimer(totalTime * 1000, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTick(long millisUntilFinished) {
                timeTicked += 1;
                timerProgress.setPercent(timeTicked * 100 / totalTime);
                timerTextView.setText(Helper.convertSecondToTimeString(totalTime - timeTicked));
                if((totalTime - timeTicked) < 1) {
                    toDoTmp.increaseTodo();
                    pauseFocus();
                }
            }

            @Override
            public void onFinish() {
                timerProgress.setPercent(100);
                timerProgress.stopAnimateIndeterminate();
                timerTextView.setText(Helper.convertSecondToTimeString(0));
                pauseMusic();
            }
        };
    }

    private void bindComponent()
    {
        timerProgress = findViewById(R.id.timerProgress);
        timerTextView = findViewById(R.id.timerTextView);
        linearLayout = findViewById(R.id.musicPlayerLinearLayout);
        musicFocusTv = findViewById(R.id.musicFocus);
        breakBtn = findViewById(R.id.breakBtn);
        finishBtn = findViewById(R.id.finishBtn);
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
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                toDoTmp.increaseTodo();
                finish();
            }
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
            if(isFocus) {
                isFocus = false;
                breakBtn.setText("Break");
            }
            else {
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
                if((totalTime - timeTicked) < 1) {
                    Toast.makeText(getBaseContext(), "Over time!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isFocus) {
                    timerProgress.animateIndeterminate();
                    isFocus = true;
                    countDownTimer.start();
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
                    pauseFocus();
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

    private void pauseFocus() {
        pauseMusic();
        timerProgress.stopAnimateIndeterminate();
        isFocus = false;
        countDownTimer.cancel();
        breakBtn.setText("Continue");
    }

    @Override
    protected void onStop() {
        super.onStop();
        pauseFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }
}