package com.app.letsfocus.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Model extends SQLiteOpenHelper implements Cloneable{
    protected static final String DATABASE_NAME = "letsfocus";
    protected SQLiteDatabase db;
    protected ContentValues datarow;

    public Model(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = getWritableDatabase();
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
        List<ContentValues> datarows = convertCursorToContentValue(cursor);
        datarow = datarows.size() > 0 ? datarows.get(0) : null;
        return this;
    }

    public Model create(ContentValues contentValues) {
        long id = db.insert(table(), null, contentValues);
        return find(id);
    }

    public List<Model> all()
    {
        List<Model> list = new ArrayList<>();
        String selectAll = String.format("SELECT * FROM %s", table());
        Cursor cursor = db.rawQuery(selectAll, null);
        List<ContentValues> datarows = convertCursorToContentValue(cursor);
        datarows.stream().forEach( datarow -> {
            list.add(clone(datarow));
        });
        return list;
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
        if(!datarow.containsKey(key)) return null;
        return datarow.getAsString(key);
    }

    public String toString() {
        if(datarow == null) return "null";
        return datarow.toString();
    }

    private Model clone(ContentValues datarow){
        try {
            Model model = (Model) super.clone();
            model.datarow = datarow;
            return model;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public void delete(Integer id) {
        String deleteSql = String.format( "DELETE FROM %s WHERE ID = " + id, table());
        db.execSQL(deleteSql);
    }
}
