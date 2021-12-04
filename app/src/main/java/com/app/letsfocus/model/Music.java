package com.app.letsfocus.model;

import com.app.letsfocus.R;

public class Music {
    String musicName, musicSinger;
    int musicImage;
    int musingPath;

    public Music(){
        this.musicName = "Lỡ Yêu";
        this.musicSinger = "Imagin Dragon";
        this.musicImage = R.drawable.loyeu;
        this.musingPath = R.raw.loyeu;
    }

    public Music(String musicName, String musicSinger, int musicImage, int musingPath) {
        this.musicName = musicName;
        this.musicSinger = musicSinger;
        this.musicImage = musicImage;
        this.musingPath = musingPath;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicSinger() {
        return musicSinger;
    }

    public void setMusicSinger(String musicSinger) {
        this.musicSinger = musicSinger;
    }

    public int getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(int musicImage) {
        this.musicImage = musicImage;
    }

    public int getMusingPath() {
        return musingPath;
    }

    public void setMusingPath(int musingPath) {
        this.musingPath = musingPath;
    }
}
