package com.app.letsfocus.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.app.letsfocus.core.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        db.getWritableDatabase().execSQL(sqlUpdateTodo);
    }

    public void updateStatus_complete(Integer id){
        String sqlUpdateTodo =  "UPDATE todo" +
                                " SET complete = " + 1 +
                                " WHERE id = " +id;
        db.getWritableDatabase().execSQL(sqlUpdateTodo);
    }

    public ToDo getTodoById(Integer id) {
        this.find(id);
        return this;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void increaseTodo() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currentTime);
        String sqlCheck = "SELECT * FROM todobyday WHERE date = '"+ date + "'";
        Cursor cursor =  db.getWritableDatabase().rawQuery(sqlCheck,null);
        if(cursor.moveToFirst()) {
            String sqlUpdate = "UPDATE todobyday SET num_todo = num_todo + 1 WHERE date = '" + date + "'";
            db.getWritableDatabase().execSQL(sqlUpdate);
        }
        else {
            String sqlInsert = "INSERT INTO todobyday (date, num_todo)" +
                    " VALUES ('"+ date +"',1)";
            db.getWritableDatabase().execSQL(sqlInsert);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList getNumTodoCompleted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<String> numToDoList = new ArrayList();
        for(int i = 0; i < 7; i++) {
            int num_todo = 0;
            LocalDate date = LocalDate.now().minusDays(i);
            String dateStr = date.format(formatter);
            String sqlGetNumTodo = "SELECT * FROM todobyday WHERE date = '" + date + "'";
            Cursor cursor = db.getReadableDatabase().rawQuery(sqlGetNumTodo, null);
            if(cursor.moveToFirst()) {
                num_todo = cursor.getInt(1);
            }
            numToDoList.add(String.valueOf(num_todo));
        }
        return numToDoList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getNumCompleteInComplte() {
        String n = "";
        int taskNum = 0;
        String sqlcheckTotal = "SELECT COUNT(*) FROM todo";
        String sqlcheckComplete = "SELECT COUNT(*) FROM todo WHERE complete = 1";
        Cursor cursor = db.getReadableDatabase().rawQuery(sqlcheckTotal, null);
        cursor.moveToFirst();
        n = n + String.valueOf(cursor.getInt(0));
        n = n + " ";
        cursor = db.getReadableDatabase().rawQuery(sqlcheckComplete, null);
        cursor.moveToFirst();
        n = n + String.valueOf(cursor.getInt(0));

        return n;
    }
}
