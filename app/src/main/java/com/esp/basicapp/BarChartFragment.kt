package com.esp.basicapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esp.basicapp.databinding.FragmentBarchartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
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
        setupBarChart2()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBarChart() {
        val chart = binding.barChart
        //表示用サンプルデータの作成//
        val x = FloatArray(12) { it.toFloat() }
        //Data
        val dataSets = mutableListOf<IBarDataSet>()

        //①Entryにデータ格納
        run {
            val y = x.map { it + 1 }//Y軸データ（X軸の2乗）

            var entryList = mutableListOf<BarEntry>()//1本目の線
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], y[i])
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, "入金済み")
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.color = Color.BLUE
            //リストに格納
            dataSets.add(dataSet)
        }

        run {
            val y = Array(x.size) { 5F }
            var entryList = mutableListOf<BarEntry>()//1本目の線
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], y[i])
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, "未入金")
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.color = Color.MAGENTA
            //リストに格納
            dataSets.add(dataSet)
        }

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        chart.data = data
        chart.description.isEnabled = false

        //棒のグルーピング用処理
        val xAxisSpan = 1f//データのX軸間隔
        val barNumber = 2//棒の本数
        val groupSpace = 0.2f//グループの間隔
        val barSpace = 0.05f//同グループの棒同士の間隔
        val barWidth = (xAxisSpan - groupSpace) / barNumber.toFloat() - barSpace//棒の幅
        data.barWidth = barWidth//棒の幅をbarDataに指定

        //棒のグルーピング
        val startX = chart.barData.dataSets.first().xMin - 0.5f//Xの最小値 - 0.5
        chart.groupBars(startX, groupSpace, barSpace)
        // 凡例のアイコン
        chart.legend.form = Legend.LegendForm.LINE
        // 凡例の位置
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP

        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        chart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IndexAxisValueFormatter(Array(12) {
                "%d月".format(it + 1)
            })
            // 背景グリッドを描画しない
            setDrawGridLines(false)
        }
        //⑦グラフ更新
        chart.invalidate()
    }

    private fun setupBarChart2() {
        val chart = binding.barChart2
        //表示用サンプルデータの作成//
        val x = FloatArray(12) { it.toFloat() }
        //Data
        val dataSets = mutableListOf<IBarDataSet>()

        //①Entryにデータ格納
        run {
            val y = x.map { it + 1 }//Y軸データ（X軸の2乗）

            val entryList = mutableListOf<BarEntry>()//1本目の線
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], y[i])
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, "入金済み")
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.color = Color.BLUE
            //リストに格納
            dataSets.add(dataSet)
        }

        run {
            val y = Array(x.size) { 5F }
            val entryList = mutableListOf<BarEntry>()//1本目の線
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], y[i])
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, "未入金")
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.color = Color.MAGENTA
            //リストに格納
            dataSets.add(dataSet)
        }

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        chart.data = data
        chart.description.isEnabled = false
        // 棒の幅 最大1.0F
        data.barWidth = 0.7F//棒の幅をbarDataに指定
        // バーの上に値を表示しない
        data.setDrawValues(false)

        // 凡例のアイコン
        chart.legend.form = Legend.LegendForm.LINE
        // 凡例の位置
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP

        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        chart.xAxis.apply {
            isEnabled = true
            textColor = Color.BLACK
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IndexAxisValueFormatter(Array(12) {
                "%d月".format(it + 1)
            })
            // 背景グリッドを描画しない
            setDrawGridLines(false)
        }
        // 左側Y軸を無効
        chart.axisLeft.isEnabled = false
        // 右側
        chart.axisRight.apply {
            setDrawTopYLabelEntry(true)
        }
        chart.axisRight.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value.toInt() == 12) {
                    "[千円]"
                } else {
                    "$value 円"
                }
            }
        }

        //⑦グラフ更新
        chart.invalidate()
    }
}
