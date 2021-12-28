package com.app.letsfocus.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "letsfocus";
    public Database(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE timetable(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, start_time TEXT, end_time TEXT, repeat INTEGER, date TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, time TEXT, duration TEXT, detail TEXT, complete INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE date(date TEXT UNIQUE, num_todo INTERGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int newVersion, int oldVersion)
    {
        String drop_table_timetable = String.format("DROP TABLE IF EXISTS %s", "timetable");
        String drop_table_todo = String.format("DROP TABLE IF EXISTS %s", "todo");
        String drop_table_todobyday = String.format("DROP TABLE IF EXISTS %s", "todobyday");
        sqLiteDatabase.execSQL(drop_table_timetable);
        sqLiteDatabase.execSQL(drop_table_todo);
        sqLiteDatabase.execSQL(drop_table_todobyday);

        onCreate(sqLiteDatabase);
    }

    public List<ContentValues> query(String sql, String ...params)
    {
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, params);
        return convertCursorToContentValue(cursor);
    }

    public List<ContentValues> select(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
    {
        Cursor cursor = this.getWritableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return convertCursorToContentValue(cursor);
    }

    private List<ContentValues> convertCursorToContentValue(Cursor cursor)
    {
        List<ContentValues> list = new ArrayList<>();
        if(cursor == null) return list;
        int columnCount = cursor.getColumnCount();
        while(cursor.moveToNext())
        {
            ContentValues contentValues = new ContentValues();
            for(int i=0; i<columnCount;i++) {
                contentValues.put(cursor.getColumnName(i), cursor.getString(i));
            }
            list.add(contentValues);
        }
        return list;
    }

    public void execSql(String sql, String ...params)
    {
        this.getWritableDatabase().execSQL(sql, params);
    }

    public long insert(String table, ContentValues contentValues)
    {
        return this.getWritableDatabase().insert(table, null, contentValues);
    }

    public void execSQL(String sql) {
        this.getWritableDatabase().execSQL(sql, null);
    }
}
