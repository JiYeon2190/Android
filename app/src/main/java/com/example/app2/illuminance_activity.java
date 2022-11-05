package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class illuminance_activity extends Activity {
    DatabaseReference mydb;
    LineChart chart;
    private TextView date;
    private Thread thread;
    private TextView cdsavg, cdsmax, cdsmin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.illuminance);

        chart = findViewById(R.id.chart);
        date = findViewById(R.id.date);
        cdsavg = findViewById(R.id.cdsavg);
        cdsmax = findViewById(R.id.cdsmax);
        cdsmin = findViewById(R.id.cdsmin);

        date.setText(getTime());

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawZeroLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawZeroLine(false);

        LineData data = new LineData();
        chart.setData(data);

        mydb= FirebaseDatabase.getInstance().getReference();

        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double cdsAvg = snapshot.child("cds_avg").getValue(Double.class);
                Double cdsMax = snapshot.child("cds_max").getValue(Double.class);
                Double cdsMin = snapshot.child("cds_min").getValue(Double.class);;

                cdsavg.setText(String.format("%.1f", cdsAvg) + " lux");
                cdsmax.setText(String.format("%.1f", cdsMax) + " lux");
                cdsmin.setText(String.format("%.1f", cdsMin) + " lux");

                String cdsData = snapshot.child("cds").getValue().toString();

                float SensorValue = Float.parseFloat(cdsData);
                if(thread != null)
                    thread.interrupt();

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        LineData data = chart.getData();
                        if(data != null){
                            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
                            if(set==null){
                                set = createSet();
                                data.addDataSet(set);
                            }

                            data.addEntry(new Entry(set.getEntryCount(), (float) SensorValue), 0);
                            data.notifyDataChanged();

                            chart.notifyDataSetChanged();
                            chart.setVisibleXRangeMaximum(10);

                            chart.moveViewToX(data.getEntryCount());
                        }
                    }
                };

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            runOnUiThread(runnable);
                            try {
                                Thread.sleep(5000);
                            }catch (InterruptedException ie)
                            {
                                ie.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //홈 버튼
        ImageButton home_btn = (ImageButton) findViewById(R.id.vector_ek54);
        home_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_activity.class);
                startActivity(intent);
            }
        });

        //다이어리 버튼
        ImageButton diary_btn = (ImageButton) findViewById(R.id.vector_ek23);
        diary_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), diary_activity.class);
                startActivity(intent);
            }
        });

        //앨범 버튼
        ImageButton album_btn = (ImageButton) findViewById(R.id.vector_ek27);
        album_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), album_activity.class);
                startActivity(intent);
            }
        });

        //온도 버튼
        TextView temperature_btn = (TextView) findViewById(R.id.____ek10);
        temperature_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), graph_activity.class);
                startActivity(intent);
            }
        });

        //조도 버튼
        ImageButton illuminance_btn = (ImageButton) findViewById(R.id.vector_ek90);
        illuminance_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), illuminance_activity.class);
                startActivity(intent);
            }
        });

        //포름알데히드 버튼
        TextView formaldehyde_btn = (TextView) findViewById(R.id.________ek8);
        formaldehyde_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), formaldehyde_activity.class);
                startActivity(intent);
            }
        });
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "실시간 조도");

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.rgb(223, 125, 118));

        set.setCircleColor(Color.rgb(223, 125, 118));
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());

        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawValues(false);
        return set;
    }
}
