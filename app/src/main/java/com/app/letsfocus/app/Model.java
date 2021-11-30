package com.app.letsfocus.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class Model extends SQLiteOpenHelper{
    protected static final String DATABASE_NAME = "letsfocus";
    protected SQLiteDatabase db;
    protected List<ContentValues> datarows;
    private boolean single = false;

    public Model(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = getWritableDatabase();
        datarows = new ArrayList<>();
    }

    @Override
    public abstract void onCreate(SQLiteDatabase sqLiteDatabase);

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String drop_table = String.format("DROP TABLE IF EXISTS %s", table());
        sqLiteDatabase.execSQL(drop_table);

        onCreate(sqLiteDatabase);
    }

    public abstract String table();
    public abstract String primaryKey();

    public Model find(Object id)
    {
        Cursor cursor = db.query(table(), null, String.format("%s = ?", primaryKey()), new String[]{String.valueOf(id)}, null, null, null);
        datarows = convertCursorToContentValue(cursor);
        single = true;
        return this;
    }

    public Model create(ContentValues contentValues) {
        long id = db.insert(table(), null, contentValues);
        return find(id);
    }

    private List<ContentValues> convertCursorToContentValue(Cursor cursor) {
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

    public String get(String key)
    {
        if(single && datarows.size() > 0) {
            if(!datarows.get(0).containsKey(key)) return null;
            return datarows.get(0).getAsString(key);
        }
        return null;
    }
}
