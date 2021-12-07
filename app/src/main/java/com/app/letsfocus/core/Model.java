package com.app.letsfocus.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Model implements Cloneable{
    protected Database db;
    protected ContentValues datarow;

    public Model(Context context) {
        db = new Database(context);
    }

    public abstract String table();
    public abstract String primaryKey();

    public Model find(Object id)
    {
        List<ContentValues> datarows = db.select(table(), null, String.format("%s = ?", primaryKey()), new String[]{String.valueOf(id)}, null, null, null);
        datarow = datarows.size() > 0 ? datarows.get(0) : null;
        return this;
    }

    public Model create(ContentValues contentValues) {
        long id = db.insert(table(), contentValues);
        return find(id);
    }

    public List<Model> all()
    {
        List<Model> list = new ArrayList<>();
        String selectAll = String.format("SELECT * FROM %s", table());
        List<ContentValues> datarows = db.query(selectAll);
        datarows.stream().forEach( datarow -> {
            list.add(clone(datarow));
        });
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
        db.getWritableDatabase().execSQL(deleteSql);
    }
}
