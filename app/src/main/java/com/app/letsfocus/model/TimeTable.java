package com.app.letsfocus.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.letsfocus.core.Model;

public class TimeTable extends Model {
    public TimeTable(Context context) {
        super(context);
    }

    @Override
    public String table() {
        return "timetable";
    }

    @Override
    public String primaryKey() {
        return "id";
    }

    public void updateTimeTable(Integer id, String nameTask, String timeStart, String timeEnd, Integer repeat) {
        String sqlUpdateTimeTable =  "UPDATE timetable" +
                " SET name = '" + nameTask + "'," +
                " start_time = '" + timeStart + "'," +
                " end_time = '" + timeEnd + "'," +
                " repeat = " + repeat +
                " WHERE" +
                " id = " + id;
        db.getWritableDatabase().execSQL(sqlUpdateTimeTable);
    }
    public TimeTable getTimeTableById(Integer id) {
        this.find(id);
        return this;
    }
}
