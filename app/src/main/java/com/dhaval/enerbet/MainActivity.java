package com.dhaval.enerbet;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    float[] yData = { 15, 10, 15, 8, 4,10 };
    String[] xData = { "Technical excellence", "Nimble", "Innovation", "Integrity", "Colloboration","Passion" };
    PieChart mChart;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_launcher_background);

        barChart = (BarChart) findViewById(R.id.barChart_days);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        mChart = findViewById(R.id.PieChart);
        mChart.setUsePercentValues(true);
        mChart.setCenterTextColor(R.color.Black);
        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();
        ShowBarChart(barChart);
        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);


    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
      /*  Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();*/
    }

    private void addData() {
        ArrayList<PieEntry> yVals1 = new ArrayList<>();


        for (int i = 0; i < yData.length; i++)
            yVals1.add(new PieEntry(yData[i]));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        dataSet.setValueTextColor(R.color.Black);
        dataSet.setColors(new int[]{R.color.Option1, R.color.Option2, R.color.Option3, R.color.Option4, R.color.Option5, R.color.Option6, R.color.Option7, R.color.Option8}, MainActivity.this);
        dataSet.setValueTextColor(R.color.Black);

        // instantiate pie data object now
        PieData data = new PieData(dataSet);
        mChart.setRotationEnabled(true);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(R.color.Black);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Overall");
        mChart.setCenterTextSize(10);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
        mChart.getLegend().setEnabled(false);
    }
    void ShowBarChart(BarChart barChart) {

        final List<Float> total = new ArrayList<>();

        int[] colors = new int[]{R.color.Option1, R.color.Option2};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelRotationAngle(270);
      //  xAxis.setLabelCount(gender.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        ArrayList<BarEntry> barEntries = new ArrayList<>();



                //    barEntries.add(new BarEntry(i, new float[]{Gender1.get(i) * 100 / (sum), Gender2.get(i) * 100 / (sum), Gender3.get(i) * 100 / (sum), Gender4.get(i) * 100 / (sum), Gender5.get(i) * 100 / (sum), Gender6.get(i) * 100 / (sum), Gender7.get(i) * 100 / (sum), Gender8.get(i) * 100 / (sum)}));

                colors = new int[]{R.color.Option1, R.color.Option2, R.color.Option3, R.color.Option4, R.color.Option5, R.color.Option6, R.color.Option7, R.color.Option8};

        BarDataSet barDataSet = new BarDataSet(barEntries, "Gender(Total Votes)");
     //   barDataSet.setColors(colors, AnswerQuestion.this);

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barChart.setDrawValueAboveBar(false);


        BarData theData = new BarData(barDataSets);
     //   theData.setValueFormatter(new MyValueFormatter());
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMaximum(100);
        barChart.getAxisLeft().setAxisMaximum(100);
        theData.setValueTextSize(10f);
        theData.setBarWidth(0.75f);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int val = (int) h.getX();
       //         genderchartclicked(h.getStackIndex(), gender.get(val), total.get(val), val);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        barChart.getLegend().setEnabled(false);

        barChart.animateXY(3000, 4000);
        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDescription(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.challenges_menu:
                startActivity(new Intent(MainActivity.this, challenges.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, MyProfile.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


