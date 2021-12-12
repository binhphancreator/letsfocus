package com.app.letsfocus.ui.fragment.report1;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.letsfocus.R;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.app_usage.AppUsageFragment;
import com.app.letsfocus.ui.fragment.report2.Report2Fragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Report1Fragment extends Fragment {

    BarChart chart;
    private ToDo tmp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report1, container, false);

        chart = view.findViewById(R.id.bar_chart);
        tmp = new ToDo(getContext());

        settingChart();
        setData();

        Button report1_btn = (Button) view.findViewById(R.id.app_usage_btn);
        report1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment_activity_main, new AppUsageFragment());
                fr.commit();
            }
        });

        return view;
    }

    private void setData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> Yvalue = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Yvalue = tmp.getNumTodoCompleted();
        }
        Collections.reverse(Yvalue);

        for (int i = 0; i < 7; i++) {
            values.add(new BarEntry(i, Float.parseFloat(Yvalue.get(i))));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setDrawValues(true);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.BLACK);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.JOYFUL_COLORS);
            set1.setDrawValues(true);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.BLACK);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setFitBars(true);
        }

        chart.invalidate();

    }

    private void settingChart() {

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(10);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        final ArrayList<String> XAxisLabel= new ArrayList<>();
        for(int i = -6; i <= 0; i++){
            XAxisLabel.add(getCalculatedDate("dd/MM", i));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(XAxisLabel));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(9, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(3f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getLegend().setEnabled(false);
    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}