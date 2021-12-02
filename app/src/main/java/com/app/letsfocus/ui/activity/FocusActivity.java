package com.app.letsfocus.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.letsfocus.R;

public class FocusActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        bindComponent();
        registerEvent();
    }

    private void bindComponent()
    {
        linearLayout = findViewById(R.id.musicPlayerLinearLayout);
    }

    private void registerEvent()
    {
        linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivityForResult(intent, 1);
        });
    }
}