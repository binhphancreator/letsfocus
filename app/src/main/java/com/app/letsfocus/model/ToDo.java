package com.app.letsfocus.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.letsfocus.core.Model;

public class ToDo extends Model {
    public ToDo (Context context) {
        super(context);
    }

    @Override
    public String table() {
        return "todo";
    }

    @Override
    public String primaryKey() {
        return "id";
    }

    public void updateTodo(Integer id, String nameTask, String timeStart, String timeDuration, String detail) {
        String sqlUpdateTodo =  "UPDATE todo" +
                                " SET name = '" + nameTask + "'," +
                                " time = '" + timeStart + "'," +
                                " duration = '" + timeDuration + "'," +
                                " detail = '" + detail +
                                "' WHERE" +
                                " id = " + id;
        db.execSQL(sqlUpdateTodo);
    }
    public ToDo getTodoById(Integer id) {
        this.find(id);
        return this;
    }
}
