package com.example.eventplanner.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.view.MyXAxisFormatter
import com.example.eventplanner.viewModel.InsightsViewModel
import com.example.eventplanner.viewModel.classes.MyYAxisValueFormatter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class InsightsFragment : Fragment(R.layout.fragment_insights) {

    private lateinit var barChart: BarChart
    private lateinit var dataVal: MutableList<BarEntry>
    private lateinit var viewModel : InsightsViewModel
    private val xValueFormatter = MyXAxisFormatter()
    private val yValueFormatter = MyYAxisValueFormatter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[InsightsViewModel::class.java]

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            viewModel.initLists(it)
        })
        barChart = view.findViewById(R.id.barChart)
        xValueFormatter.setArray(arrayListOf("completed", "upcoming", "missed"))

        dataVal = arrayListOf()

        listObserver(viewModel.completedCount, 0)
        listObserver(viewModel.upcomingCount, 1 )
        listObserver(viewModel.missedCount, 2)

        initBarchartAxis(barChart.xAxis, xValueFormatter)
        initBarchartAxis(barChart.axisLeft, yValueFormatter)
        initBarchartAxis(barChart.axisRight, yValueFormatter)


    }

    override fun onResume() {
        super.onResume()
        barChart.animateY(3000, Easing.EaseInBack)
    }

    private fun listObserver(list : MutableLiveData<Int>, num : Int){
        list.observe(viewLifecycleOwner, Observer {
            dataVal.add(BarEntry(num.toFloat(), list.value?.toFloat() ?: "0".toFloat()))
            val dataSet = BarDataSet(dataVal, "")
            val barData = BarData(dataSet)
            barChart.data = barData
            barChart.invalidate()
        })
    }

    private fun initBarchartAxis(v : AxisBase, valueFormatter : ValueFormatter){
        v.valueFormatter = valueFormatter
        v.isGranularityEnabled = true
    }

}