package com.dhaval.enerbet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    List<Integer> energy_day = new ArrayList<>();
    List<Float> realtime_energy = new ArrayList<>();
    List<Float> power_values = new ArrayList<>();
    List<Float> time = new ArrayList<>();
    BarChart barChart;
    int average_energy = 100;
    int i =1;
    LineChart line_chart;
    DatabaseReference mDatabase;
    long c =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart = (BarChart) findViewById(R.id.barChart_days);
        line_chart = (LineChart) findViewById(R.id.line_chart);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setTitle("EnerBet (Home)");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ShowBarChart(barChart);
       // ShowLineChart(line_chart);
        UpdateLineCHart();
        // customize legends



    }

void UpdateLineCHart() {

    mDatabase.child("energy").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            GetData(dataSnapshot.getChildrenCount());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
void GetData(final long count){
         c =0 ;
    mDatabase.child("energy").addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            c++;
            energy_class energy_class = dataSnapshot.getValue(com.dhaval.enerbet.energy_class.class);
            realtime_energy.add(energy_class.getEnergy()*1000);
            String t = energy_class.getTimestamp();
            float tim = Float.parseFloat(""+ t.charAt(14) +""+ t.charAt(15))/60;
         float tt = Integer.parseInt(""+ t.charAt(11) +""+ t.charAt(12)) + tim;

           time.add(tt);

            if(c >= count){
               ShowLineChart(line_chart);
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });



}
    void ShowLineChart(LineChart barChart) {


        int[] colors = new int[]{R.color.Option1, R.color.Option2};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelRotationAngle(270);
        xAxis.setLabelCount(energy_day.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        ArrayList<Entry> entries = new ArrayList<>();
        float energy;
            power_values.add((float) 0.00);
        for( i = 1;i<realtime_energy.size();i++) {
         //   Toast.makeText(MainActivity.this, ""+time.get(i),Toast.LENGTH_LONG).show();
            energy = realtime_energy.get(i)-realtime_energy.get(i-1);
            power_values.add(energy);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                   return ("" + time.get(Math.round(value)));

                }
            });

            entries.add(new Entry(i,energy));


        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Energy Consumption");
        colors = new int[]{R.color.Green };
        lineDataSet.setColors(colors, MainActivity.this);
        //ColorDrawable cd = new ColorDrawable(0x00C853);
       // lineDataSet.setFillDrawable(cd);
        lineDataSet.setDrawFilled(true);
        //dataSet.setFillAlpha(255);
        lineDataSet.setFillAlpha(220);
        lineDataSet.setFillColor(getResources().getColor(R.color.Green));

        ArrayList<LineDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(lineDataSet);

        LineData theData = new LineData(lineDataSet);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMaximum(Collections.max(power_values));
        barChart.getAxisLeft().setAxisMaximum(Collections.max(power_values));

        barChart.getLegend().setEnabled(false);
        barChart.animateXY(3000, 4000);
        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDescription(null);

    }

    void ShowBarChart(BarChart barChart) {

        energy_day.add(50);
        energy_day.add(150);
        energy_day.add(40);
        energy_day.add(90);
        energy_day.add(180);
        energy_day.add(10);
        energy_day.add(130);
        energy_day.add(50);
        energy_day.add(90);
        energy_day.add(100);
        energy_day.add(60);
        energy_day.add(110);
        energy_day.add(140);
        energy_day.add(25);



        int[] colors = new int[]{R.color.Option1, R.color.Option2};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setLabelRotationAngle(270);
        xAxis.setLabelCount(energy_day.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        int energy, red_energy, green_energy,gray_energy = 0;

for( i = 0;i<energy_day.size();i++) {
     energy = energy_day.get(i);
    if(energy>average_energy){
     red_energy = energy - average_energy;
     gray_energy = average_energy;
    }
    else red_energy = 0;

    if(energy<average_energy){
        gray_energy = energy;
        green_energy = average_energy-energy;}
    else green_energy = 0;

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ("" + Math.round(value));

            }
        });

    barEntries.add(new BarEntry(i, new float[]{gray_energy, green_energy, red_energy}));


}

        BarDataSet barDataSet = new BarDataSet(barEntries, "Energy Consumption");
        colors = new int[]{R.color.Gray, R.color.Green, R.color.Red};
        barDataSet.setColors(colors, MainActivity.this);

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barChart.setDrawValueAboveBar(false);


        BarData theData = new BarData(barDataSets);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMaximum(Collections.max(energy_day));
        barChart.getAxisLeft().setAxisMaximum(Collections.max(energy_day));
        theData.setValueTextSize(10f);
        theData.setBarWidth(0.75f);


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


