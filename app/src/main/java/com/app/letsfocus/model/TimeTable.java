package com.app.letsfocus.model;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.app.letsfocus.core.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public void updateTimeTable(Integer id, String nameTask, String timeStart, String timeEnd, Integer repeat, String date) {
        String sqlUpdateTimeTable =  "UPDATE timetable" +
                " SET name = '" + nameTask + "'," +
                " start_time = '" + timeStart + "'," +
                " end_time = '" + timeEnd + "'," +
                " repeat = " + repeat + "," +
                " date ='" + date + "'" +
                " WHERE" +
                " id = " + id;
        db.getWritableDatabase().execSQL(sqlUpdateTimeTable);
    }
    public TimeTable getTimeTableById(Integer id) {
        this.find(id);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Model> getTimeTableByDate(String date)
    {
        String[] dateArray = date.split("-");
        LocalDate someDay = LocalDate.of(Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[2]));
        String dayOfWeek = someDay.getDayOfWeek().toString();
        List<Model> list = new ArrayList<>();
        String selectAll = "SELECT * FROM timetable \n" +
                "WHERE repeat = 1 OR (date like '%"+ dateArray[2] +"%' AND repeat = 3)"+
                " OR date = '" + date + "'" +
                " OR date like '%"+ dayOfWeek +"%'";
        List<ContentValues> datarows = db.query(selectAll);
        datarows.stream().forEach( datarow -> {
            list.add(clone(datarow));
        });
        return list;
    }
}
