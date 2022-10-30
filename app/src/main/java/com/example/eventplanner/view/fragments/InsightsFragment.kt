package com.example.eventplanner.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.modal.AuthenticationModal
import com.example.eventplanner.view.MyXAxisFormatter
import com.example.eventplanner.viewModel.InsightsViewModel
import com.example.eventplanner.viewModel.classes.MyYAxisValueFormatter
import com.example.eventplanner.viewModel.classes.PieChartValueGetter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter


class InsightsFragment : Fragment(R.layout.fragment_scrolling) {

    private val TAG = "InsightsFragment"
    private lateinit var barChart: BarChart
    private lateinit var pieChart : PieChart
    private lateinit var viewModel : InsightsViewModel
    private val xValueFormatter = MyXAxisFormatter()
    private val pieChartValueGetter = PieChartValueGetter()
    private val yValueFormatter = MyYAxisValueFormatter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart = view.findViewById(R.id.pieChart)
        viewModel = ViewModelProvider(this)[InsightsViewModel::class.java]

        barChart = view.findViewById(R.id.barChart)

        viewModel.catList.observe(viewLifecycleOwner, Observer {
            viewModel.initCategoryList()
            showPieChart()
        })

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            viewModel.initLists(it)
            showBarGraph()
        })
    }

    private fun showBarGraph(){
        xValueFormatter.setArray(arrayListOf("completed", "upcoming", "missed"))

        val dataVal = arrayListOf<BarEntry>()

        dataVal.add(BarEntry(0.toFloat(),  viewModel.completedCount.value?.toFloat() ?: "0".toFloat()))
        dataVal.add(BarEntry(1.toFloat(), viewModel.upcomingCount.value?.toFloat() ?: "0".toFloat()))
        dataVal.add(BarEntry(2.toFloat(), viewModel.missedCount.value?.toFloat() ?: "0".toFloat()))
        val dataSet = BarDataSet(dataVal, "Completed, Upcoming, Missed")

        dataSet.colors = arrayListOf(resources.getColor(R.color.done), resources.getColor(R.color.upcoming), resources.getColor(R.color.missed))
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()

        initBarchartAxis(barChart.xAxis, xValueFormatter)
        initBarchartAxis(barChart.axisLeft, yValueFormatter)
        initBarchartAxis(barChart.axisRight, yValueFormatter)

        barChart.xAxis.isEnabled = false
    }

    private fun showPieChart(){
        val array = arrayListOf<PieEntry>()

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))
        colors.add(Color.parseColor("#890567"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#3ca567"))

        val hashMap = pieChartValueGetter.getCategoryList(viewModel.categoryList, viewModel.eventWithCategoryNameList)
        for (type in hashMap.keys){
            Log.i(TAG, type)
            array.add(PieEntry(hashMap[type]!!.toFloat(), type))
        }

        val pieDataSet= PieDataSet(array, "String")
        //setting text size of the value
        //setting text size of the value
        pieDataSet.valueTextSize = 12f

        Log.i(TAG, hashMap.size.toString())
        pieDataSet.colors = colors.slice(0..hashMap.size)
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)

        pieChart.data = pieData
        pieChart.invalidate()
    }

    override fun onResume() {
        super.onResume()

        showBarGraph()
        viewModel.catList.observe(viewLifecycleOwner, Observer {
            viewModel.initCategoryList()
            showPieChart()
        })
        barChart.animateY(3000, Easing.EaseInBack)
        pieChart.animateX(5000, Easing.EaseInBack)
    }


    private fun initBarchartAxis(v : AxisBase, valueFormatter : ValueFormatter){
        v.valueFormatter = valueFormatter
        v.isGranularityEnabled = true
    }
}