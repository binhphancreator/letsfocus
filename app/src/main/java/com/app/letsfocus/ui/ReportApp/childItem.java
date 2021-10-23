package com.app.letsfocus.ui.ReportApp;

public class childItem {
    public int image;
    public String name;
    public String time;

    public childItem(String n, String time) {
        this.name = n;
        this.time = time;
    }

        public String getName1(){
        return this.name;
    }

        public String getTime(){
        return this.time;
    }
}
