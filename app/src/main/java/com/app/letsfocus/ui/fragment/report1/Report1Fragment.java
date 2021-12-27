package com.app.letsfocus.ui.fragment.report1;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.model.ToDo;
import com.app.letsfocus.ui.fragment.app_usage.AppUsageFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
    TextView total_tv, complete_tv;
    ValueFormatter vf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report1, container, false);

        chart = view.findViewById(R.id.bar_chart);
        total_tv = view.findViewById(R.id.total_task);
        complete_tv = view.findViewById(R.id.complete_task);

        tmp = new ToDo(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set number of all task and completed task
            total_tv.setText(tmp.getNumCompleteInComplte().split(" ")[0]);
            complete_tv.setText(tmp.getNumCompleteInComplte().split(" ")[1]);
        }
        // data format from float to int for chart Y axis
        vf = new ValueFormatter() { //value format here, here is the overridden method
            @Override
            public String getFormattedValue(float value) {
                return ""+(int)value;
            }
        };

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
            // get data from database
            Yvalue = tmp.getNumTodoCompleted();
        }
        // reverse data list
        Collections.reverse(Yvalue);

        for (int i = 0; i < 7; i++) {
            // add data to BarchartEntry
            values.add(new BarEntry(i, Float.parseFloat(Yvalue.get(i))));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setDrawValues(true);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.BLACK);
            // set data to chart
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            // update when data changed
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.JOYFUL_COLORS);
            set1.setDrawValues(true);
            set1.setValueTextSize(10f);
            set1.setValueTextColor(Color.BLACK);

            // add data to bardataset
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            // add data to bar data
            BarData data = new BarData(dataSets);
            data.setValueFormatter(vf);
            chart.setData(data);
            chart.setFitBars(true);
        }
        // redraw/update chart
        chart.invalidate();
    }

    private void settingChart() {
        // disable bar shadow
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        // disable bar chart description
        chart.getDescription().setEnabled(false);
        // max bar can visible
        chart.setMaxVisibleValueCount(10);
        chart.setPinchZoom(false);
        // disable background frid
        chart.setDrawGridBackground(false);

        final ArrayList<String> XAxisLabel= new ArrayList<>();
        for(int i = -6; i <= 0; i++){
            // get recent 7 days
            XAxisLabel.add(getCalculatedDate("dd/MM", i));
        }

        // set up  X axis
        XAxis xAxis = chart.getXAxis();
        // X axis position
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        // label can visible
        xAxis.setLabelCount(6);
        // format X data to dat/month
        xAxis.setValueFormatter(new IndexAxisValueFormatter(XAxisLabel));

        // set up Y axis
        YAxis leftAxis = chart.getAxisLeft();
        // 9 data can visible
        leftAxis.setLabelCount(9, true);
        // y axis position
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(3f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        // format int data
        leftAxis.setValueFormatter(vf);
        // disable right Y axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        // disable chart legend
        chart.getLegend().setEnabled(false);
    }

    // get 7 days back from today
    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }
}