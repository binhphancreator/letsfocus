package com.app.letsfocus.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.letsfocus.core.Model;

public class TimeTable extends Model {
    public TimeTable(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE timetable(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, start_time TEXT, end_time TEXT, repeat INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public String table() {
        return "timetable";
    }

    @Override
    public String primaryKey() {
        return "id";
    }
}
