package com.esp.basicapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esp.basicapp.databinding.FragmentBarchartBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

/**
 * MPAndroidChart
 */
class BarChartFragment : Fragment(R.layout.fragment_barchart) {

    private var _binding: FragmentBarchartBinding? = null
    private val binding: FragmentBarchartBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            _binding = FragmentBarchartBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBarChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBarChart() {
        //表示用サンプルデータの作成//
        val x = listOf(1f, 2f, 3f, 5f, 6f, 7f, 8f, 9f)//X軸データ
        val y = x.map { it }//Y軸データ（X軸の2乗）

        //①Entryにデータ格納
        var entryList = mutableListOf<BarEntry>()//1本目の線
        for (i in x.indices) {
            entryList.add(
                BarEntry(x[i], y[i])
            )
        }

        //Data
        val dataSets = mutableListOf<IBarDataSet>()
        //②DataSetにデータ格納
        val dataSet = BarDataSet(entryList, "square")
        //③DataSetにフォーマット指定(3章で詳説)
        dataSet.color = Color.BLUE
        //リストに格納
        dataSets.add(dataSet)

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        binding.barChart.data = data
        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        binding.barChart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
        }
        //⑦linechart更新
        binding.barChart.invalidate()
    }
}
