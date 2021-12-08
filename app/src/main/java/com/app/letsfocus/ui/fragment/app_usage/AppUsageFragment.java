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
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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

    Button enableBtn, showBtn;
    TextView permissionDescriptionTv, usageTv, tv1;
    ListView appsList;
    View root;
    PieChart chart;
    FrameLayout frameChart;
    long timeUsedTotal = 0;
    ArrayList<PieEntry> pieData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_app_usage, container, false);
        enableBtn = root.findViewById(R.id.enable_btn);
        showBtn = root.findViewById(R.id.show_btn);
        permissionDescriptionTv = root.findViewById(R.id.permission_description_tv);
        usageTv = root.findViewById(R.id.usage_tv);
        appsList = root.findViewById(R.id.apps_list);
        chart = root.findViewById(R.id.report_piechart);
        frameChart = root.findViewById(R.id.layout_chart);

        //set  up chart
        chart.setExtraOffsets(50, 10, 50, 10);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setDragDecelerationFrictionCoef(0.3f);
        chart.setCenterTextTypeface(Typeface.DEFAULT);
//        chart.setCenterText("Your apps used time");
//        chart.setCenterTextSize(20);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(70f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.getLegend().setEnabled(false);

        // set up data

        setData();

        return root;
    }


    // each time the application gets in foreground -> getGrantStatus and render the corresponding buttons
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
     * load the usage stats for last 24h
     */

    public void loadStatistics() {
        UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(getActivity().USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - 1000 * 3600 * 24, System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());
        }

        // Group the usageStats by application and sort them by total time in foreground
        if (appList.size() > 0) {
            Map<String, UsageStats> mySortedMap = new TreeMap<>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getPackageName(), usageStats);
            }
            showAppsUsage(mySortedMap);
        }
    }


    public void showAppsUsage(Map<String, UsageStats> mySortedMap) {
        //public void showAppsUsage(List<UsageStats> usageStatsList) {
        ArrayList<App> appsList = new ArrayList<>();
        List<UsageStats> usageStatsList = new ArrayList<>(mySortedMap.values());

        // sort the applications by time spent in foreground
        Collections.sort(usageStatsList, Comparator.comparingLong(UsageStats::getTotalTimeInForeground));

        // get total time of apps usage to calculate the usagePercentage for each app
        long totalTime = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            totalTime = usageStatsList.stream().map(UsageStats::getTotalTimeInForeground).mapToLong(Long::longValue).sum();
        }

        //fill the appsList
        for (UsageStats usageStats : usageStatsList) {
            try {
                String packageName = usageStats.getPackageName();
                Drawable icon = getActivity().getDrawable(R.drawable.no_image);
                String[] packageNames = packageName.split("\\.");
                String appName = packageNames[packageNames.length - 1].trim();


                if (isAppInfoAvailable(usageStats)) {
                    ApplicationInfo ai = getActivity().getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0);
                    icon = getActivity().getApplicationContext().getPackageManager().getApplicationIcon(ai);
                    appName = getActivity().getApplicationContext().getPackageManager().getApplicationLabel(ai).toString();
                }

                String usageDuration = getDurationBreakdown(usageStats.getTotalTimeInForeground());
                int usagePercentage = (int) (usageStats.getTotalTimeInForeground() * 100 / totalTime);

                App usageStatDTO = new App(icon, appName, usagePercentage, usageDuration);
                appsList.add(usageStatDTO);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


        // reverse the list to get most usage first
        Collections.reverse(appsList);
        // build the adapter
        AppAdapter adapter = new AppAdapter(getActivity(), appsList);


        // attach the adapter to a ListView
        ListView listView = root.findViewById(R.id.apps_list);
        listView.setAdapter(adapter);

        Collections.reverse(usageStatsList);
        for(UsageStats item : usageStatsList){
            if (pieData.size() < 4 ) {
                String packageName = item.getPackageName();
                String[] packageNames = packageName.split("\\.");
                String appName = packageNames[packageNames.length - 1].trim();
                pieData.add(new PieEntry((float) item.getTotalTimeInForeground(), appName));
            } else {
                timeUsedTotal = timeUsedTotal + item.getTotalTimeInForeground();
                Log.w("aaa", String.valueOf(timeUsedTotal));
            }
        }
        pieData.add(new PieEntry((float) totalTime, "others"));

        showHideItemsWhenShowApps();
    }

    /**
     * check if PACKAGE_USAGE_STATS permission is aloowed for this application
     *
     * @return true if permission granted
     */
    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getActivity().getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getActivity().getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getActivity().getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == MODE_ALLOWED);
        }
    }


    private boolean isAppInfoAvailable(UsageStats usageStats) {
        try {
            getActivity().getApplicationContext().getPackageManager().getApplicationInfo(usageStats.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * helper method to get string in format hh:mm:ss from miliseconds
     *
     * @param millis (application time in foreground)
     * @return string in format hh:mm:ss from miliseconds
     */
    private String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (hours + " h " + minutes + " m " + seconds + " s");
    }


    /**
     * helper method used to show/hide items in the view when  PACKAGE_USAGE_STATS permission is not allowed
     */
    public void showHideNoPermission() {

        enableBtn.setVisibility(View.VISIBLE);
        permissionDescriptionTv.setVisibility(View.VISIBLE);
        showBtn.setVisibility(View.GONE);
        usageTv.setVisibility(View.GONE);
        appsList.setVisibility(View.GONE);

    }


    public void showHideItemsWhenShowApps() {
        enableBtn.setVisibility(View.GONE);
        permissionDescriptionTv.setVisibility(View.GONE);
        showBtn.setVisibility(View.GONE);
        usageTv.setVisibility(View.VISIBLE);
        appsList.setVisibility(View.VISIBLE);
        chart.setVisibility(View.VISIBLE);
        frameChart.setVisibility(View.VISIBLE);

    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Your apps used time");
        s.setSpan(new RelativeSizeSpan(2f), 0, 9, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 9, s.length(), 0);
        return s;
    }

    private void setData() {

        PieDataSet dataSet = new PieDataSet(pieData, "App Usage");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }
}