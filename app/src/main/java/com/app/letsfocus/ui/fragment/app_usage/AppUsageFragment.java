package com.app.letsfocus.ui.fragment.app_usage;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import android.app.AppOpsManager;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.letsfocus.R;
import com.app.letsfocus.adapter.AppAdapter;

import com.app.letsfocus.model.App;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AppUsageFragment extends Fragment {

    Button enableBtn;
    TextView permissionDescriptionTv, usageTv;
    ListView appsList;
    View root;
    PieChart chart;
    FrameLayout frameChart;
    long timeUsedTotal = 0;
    ArrayList<PieEntry> pieData = new ArrayList<>();
    long start = System.currentTimeMillis() - 1000 * 3600 * 24;
    long end = System.currentTimeMillis();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_app_usage, container, false);
        enableBtn = root.findViewById(R.id.enable_btn);
        permissionDescriptionTv = root.findViewById(R.id.permission_description_tv);
        usageTv = root.findViewById(R.id.usage_tv);
        appsList = root.findViewById(R.id.apps_list);
        chart = root.findViewById(R.id.report_piechart);
        frameChart = root.findViewById(R.id.layout_chart);
        settingChart();
        setData();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getGrantStatus()) {
            loadStatistics();
        } else {
            showHideNoPermission();
            enableBtn.setOnClickListener(view -> startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
        }
    }
    /**
     * Kiem tra quyen cap he thong
     * @return true neu da cap quyen
     */
    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getActivity().getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getActivity().getApplicationContext().getPackageName());

        return (mode == MODE_ALLOWED);

    }


    /**
     * lay du lieu su dung app trong 24h
     */

    public void loadStatistics() {
        UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(getActivity().USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start , end);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());
        }

        // nhom cac du lieu su dung theo app va thoi gian start - end
        if (appList.size() > 0) {
            Map<String, UsageStats> sortedMap = usm.queryAndAggregateUsageStats(start, end);
            showAppsUsage(sortedMap);
        }
    }


    public void showAppsUsage(Map<String, UsageStats> sortedMap) {
        ArrayList<App> appsList = new ArrayList<>();
        List<UsageStats> usageStatsList = new ArrayList<>(sortedMap.values());

        // sap xep thoi gian cac ung dung chay
        Collections.sort(usageStatsList, Comparator.comparingLong(UsageStats::getTotalTimeInForeground));

        //tong thoi gian da dung dien thoai
        long totalTime = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            totalTime = usageStatsList.stream().map(UsageStats::getTotalTimeInForeground).mapToLong(Long::longValue).sum();
        }

        //tao appsList
        for (UsageStats usageStats : usageStatsList) {
            try {
                String packageName = usageStats.getPackageName();
                ApplicationInfo ai = getActivity().getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0);
                Drawable icon = getActivity().getApplicationContext().getPackageManager().getApplicationIcon(ai);
                String appName = getActivity().getApplicationContext().getPackageManager().getApplicationLabel(ai).toString();
                //convert tu (long) thoi gian to (String) thoi gian
                String usageDuration = getDurationBreakdown(usageStats.getTotalTimeInForeground());

                int usagePercentage = (int) (usageStats.getTotalTimeInForeground() * 100 / totalTime);

                App appUsageStats = new App(icon, appName, usagePercentage, usageDuration);
                if(usagePercentage>1) appsList.add(appUsageStats);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        // dao vi tri danh sach cac app duoc su dung nhieu nhat
        Collections.reverse(appsList);
        // tao adapter
        AppAdapter adapter = new AppAdapter(getActivity(), appsList);
        // gan adapter sang ListView
        ListView listView = root.findViewById(R.id.apps_list);
        listView.setAdapter(adapter);
        /**
         * tao chart
         */
        Collections.reverse(usageStatsList);
        for(UsageStats item : usageStatsList){
            // get 4 most use app
            if (pieData.size() < 4 ) {
                String packageName = item.getPackageName();
                String[] packageNames = packageName.split("\\.");
                String appName = packageNames[packageNames.length - 1].trim();
                // add data of 1 app to arraylist
                pieData.add(new PieEntry(item.getTotalTimeInForeground(), appName));
            } else {
                timeUsedTotal = timeUsedTotal + item.getTotalTimeInForeground();
            }
        }
        // add data of all other app
        pieData.add(new PieEntry(timeUsedTotal, "others"));

        showHideItemsWhenShowApps();
    }

    /**
     * convert tu (long) thoi gian to (String) thoi gian
     */

    private String getDurationBreakdown(long millis) {

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (hours + " h " + minutes + " m " + seconds + " s");
    }

    /**
     * giao dien khi chua duoc cap quyen
     */

    public void showHideNoPermission() {
        enableBtn.setVisibility(View.VISIBLE);
        permissionDescriptionTv.setVisibility(View.VISIBLE);
        usageTv.setVisibility(View.GONE);
        appsList.setVisibility(View.GONE);
        chart.setVisibility(View.GONE);
        frameChart.setVisibility(View.GONE);
    }

    /**
     * giao dien khi da duoc cap quyen
     */

    public void showHideItemsWhenShowApps() {
        enableBtn.setVisibility(View.GONE);
        permissionDescriptionTv.setVisibility(View.GONE);
        usageTv.setVisibility(View.VISIBLE);
        appsList.setVisibility(View.VISIBLE);
        chart.setVisibility(View.VISIBLE);
        frameChart.setVisibility(View.VISIBLE);

    }

    private void settingChart(){
        // chart position
        chart.setExtraOffsets(30, 10, 30, 10);
        // enable percent value
        chart.setUsePercentValues(true);
        // disable chart description
        chart.getDescription().setEnabled(false);
        chart.setDragDecelerationFrictionCoef(0.3f);
        // draw hole inside chart
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(255);
        // chart hole radius
        chart.setHoleRadius(70f);
        chart.setTransparentCircleRadius(61f);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        // highlight on tap
        chart.setHighlightPerTapEnabled(true);
        // chart animation
        chart.animateY(1400, Easing.EaseInOutQuad);
        // disable chart legend
        chart.getLegend().setEnabled(false);
    }

    private void setData() {
        // add data to dataset
        PieDataSet dataSet = new PieDataSet(pieData, "App Usage");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add color for chart
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);

        // customize data outside
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        // data position
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        // add data to chart
        PieData data = new PieData(dataSet);
        // data format %
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        // redraw/update chart
        chart.invalidate();
    }
}