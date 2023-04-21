package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.tipper.inmemory.BMIResultData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.List;

/**
 * Author Michał Wadas s20495
 */

public class BMIGraphActivity extends AppCompatActivity {
    private LineChart lineChartBMI;
    private RecyclerView recycler;
    private BMIResultData bmiResultData;
    private List<BMIActivity.BMIResult> bmiResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmigraph);
        bmiResultData = BMIResultData.getBmiResultData();

        bmiResults = bmiResultData.getBMIResults();

        // LineChart init
        lineChartBMI = findViewById(R.id.bmiLineChart);
        setupLineChart();

        // Recycler init
        recycler = findViewById(R.id.bmiRecyclerView);
        setupRecyclerView();
    }

    private void setupLineChart() {
        if (bmiResults != null) {
            List<Entry> entries = new ArrayList<>();

            addEntries(entries);

            LineDataSet dataSet = new LineDataSet(entries, "BMI");
            
            /*
            setting default color
             */
            dataSet.setColor(Color.BLUE); 
            
            /*
            hiding chart values 
             */
            dataSet.setDrawValues(false);

            // Set colors based on BMI ranges
            float bmi;
            for (int i = 0; i < entries.size(); i++) {
                bmi = entries.get(i).getY();
                if (bmi < 16.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorEmaciation));
                } else if (bmi < 17.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorUnderweight));
                } else if (bmi < 18.5) {
                    dataSet.setColor(getResources().getColor(R.color.colorLowWeight));
                } else if (bmi < 25.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorNormalWeight));
                } else if (bmi < 30.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorOverweight));
                } else if (bmi < 35.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorFirstDegreeObesity));
                } else if (bmi < 40.0) {
                    dataSet.setColor(getResources().getColor(R.color.colorSecondDegreeObesity));
                } else {
                    dataSet.setColor(getResources().getColor(R.color.colorExtremeObesity));
                }
            }

            LineData lineData = new LineData(dataSet);
            lineChartBMI.setData(lineData);

            // legend entries
            ArrayList<LegendEntry> legendEntries = new ArrayList<>();
            String[] labels = new String[]{"Emaciation", "Underweight", "Low weight", "Normal weight", "Overweight", "First-degree obesity", "Second-degree obesity", "Extreme obesity"};
            int[] colors = new int[]{getResources().getColor(R.color.colorEmaciation), getResources().getColor(R.color.colorUnderweight), getResources().getColor(R.color.colorLowWeight), getResources().getColor(R.color.colorNormalWeight), getResources().getColor(R.color.colorOverweight), getResources().getColor(R.color.colorFirstDegreeObesity), getResources().getColor(R.color.colorSecondDegreeObesity), getResources().getColor(R.color.colorExtremeObesity)};
            for (int i = 0; i < labels.length; i++) {
                LegendEntry entry = new LegendEntry();
                entry.label = labels[i];
                entry.formColor = colors[i];
                legendEntries.add(entry);
            }
            Legend legend = lineChartBMI.getLegend();
            legend.setCustom(legendEntries);

            //refreshing
            lineChartBMI.invalidate();
        }
    }

    private void addEntries(List<Entry> entries) {
        for (int i = 0; i < bmiResults.size(); i++) {
            BMIActivity.BMIResult result = bmiResults.get(i);
            entries.add(new Entry(i, (float) result.bmi));
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        BMIResultAdapter adapter = new BMIResultAdapter(bmiResults);
        recycler.setAdapter(adapter);
    }
}