package com.app.letsfocus.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.letsfocus.app.Model;

public class ToDo extends Model {
    public ToDo (Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, time TEXT, duration INTEGER, detail TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public String table() {
        return "todo";
    }

    @Override
    public String primaryKey() {
        return "id";
    }
}
