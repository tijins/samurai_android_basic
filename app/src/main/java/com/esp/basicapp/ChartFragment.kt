package com.esp.basicapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esp.basicapp.databinding.FragmentChartBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

/**
 * MPAndroidChart
 */
class ChartFragment : Fragment(R.layout.fragment_chart) {

    private var _binding: FragmentChartBinding? = null
    private val binding: FragmentChartBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            _binding = FragmentChartBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLineChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLineChart() {
        //表示用サンプルデータの作成//
        val x = listOf<Float>(1f, 2f, 3f, 5f, 8f, 13f, 21f, 34f)//X軸データ
        val y = x.map { it * it }//Y軸データ（X軸の2乗）

        //①Entryにデータ格納
        var entryList = mutableListOf<Entry>()//1本目の線
        for (i in x.indices) {
            entryList.add(
                Entry(x[i], y[i])
            )
        }

        //LineDataSetのList
        val lineDataSets = mutableListOf<ILineDataSet>()
        //②DataSetにデータ格納
        val lineDataSet = LineDataSet(entryList, "square")
        //③DataSetにフォーマット指定(3章で詳説)
        lineDataSet.color = Color.BLUE
        //リストに格納
        lineDataSets.add(lineDataSet)

        //④LineDataにLineDataSet格納
        val lineData = LineData(lineDataSets)
        //⑤LineChartにLineData格納
        binding.lineChart.data = lineData
        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        binding.lineChart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
        }
        //⑦linechart更新
        binding.lineChart.invalidate()
    }
}
