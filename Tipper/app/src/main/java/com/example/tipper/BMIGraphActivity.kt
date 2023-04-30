package com.example.tipper

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tipper.BMIActivity.BMIResult
import com.example.tipper.inmemory.BMIResultData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

/**
 * Author Michał Wadas s20495
 */
class BMIGraphActivity : AppCompatActivity() {
    private var lineChartBMI: LineChart? = null
    private var recycler: RecyclerView? = null
    private var bmiResultData: BMIResultData? = null
    private var bmiResults: List<BMIResult>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmigraph)
        bmiResultData = BMIResultData.bmiResultData
        bmiResults = bmiResultData!!.bMIResults

        // LineChart init
        lineChartBMI = findViewById(R.id.bmiLineChart)
        setupLineChart()

        // Recycler init
        recycler = findViewById(R.id.bmiRecyclerView)
        setupRecyclerView()
    }

    private fun setupLineChart() {
        if (bmiResults != null) {
            val entries: MutableList<Entry> = ArrayList()
            addEntries(entries)
            val dataSet = LineDataSet(entries, "BMI")

            /*
            setting default color
             */dataSet.color = Color.BLUE

            /*
            hiding chart values 
             */dataSet.setDrawValues(false)

            // Set colors based on BMI ranges
            var bmi: Float
            for (i in entries.indices) {
                bmi = entries[i].y
                if (bmi < 16.0) {
                    dataSet.color = resources.getColor(R.color.colorEmaciation)
                } else if (bmi < 17.0) {
                    dataSet.color = resources.getColor(R.color.colorUnderweight)
                } else if (bmi < 18.5) {
                    dataSet.color = resources.getColor(R.color.colorLowWeight)
                } else if (bmi < 25.0) {
                    dataSet.color = resources.getColor(R.color.colorNormalWeight)
                } else if (bmi < 30.0) {
                    dataSet.color = resources.getColor(R.color.colorOverweight)
                } else if (bmi < 35.0) {
                    dataSet.color = resources.getColor(R.color.colorFirstDegreeObesity)
                } else if (bmi < 40.0) {
                    dataSet.color = resources.getColor(R.color.colorSecondDegreeObesity)
                } else {
                    dataSet.color = resources.getColor(R.color.colorExtremeObesity)
                }
            }
            val lineData = LineData(dataSet)
            lineChartBMI!!.data = lineData

            // legend entries
            val legendEntries = ArrayList<LegendEntry>()
            val labels = arrayOf("Emaciation", "Underweight", "Low weight", "Normal weight", "Overweight", "First-degree obesity", "Second-degree obesity", "Extreme obesity")
            val colors = intArrayOf(resources.getColor(R.color.colorEmaciation), resources.getColor(R.color.colorUnderweight), resources.getColor(R.color.colorLowWeight), resources.getColor(R.color.colorNormalWeight), resources.getColor(R.color.colorOverweight), resources.getColor(R.color.colorFirstDegreeObesity), resources.getColor(R.color.colorSecondDegreeObesity), resources.getColor(R.color.colorExtremeObesity))
            for (i in labels.indices) {
                val entry = LegendEntry()
                entry.label = labels[i]
                entry.formColor = colors[i]
                legendEntries.add(entry)
            }
            val legend = lineChartBMI!!.legend
            legend.setCustom(legendEntries)

            //refreshing
            lineChartBMI!!.invalidate()
        }
    }

    private fun addEntries(entries: MutableList<Entry>) {
        for (i in bmiResults!!.indices) {
            val result = bmiResults!![i]
            entries.add(Entry(i.toFloat(), result.bmi.toFloat()))
        }
    }

    private fun setupRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler!!.layoutManager = layoutManager
        val adapter = BMIResultAdapter(bmiResults)
        recycler!!.adapter = adapter
    }
}