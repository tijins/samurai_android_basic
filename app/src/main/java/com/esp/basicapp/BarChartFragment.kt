package com.esp.basicapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.esp.basicapp.databinding.FragmentBarchartBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import timber.log.Timber
import java.util.Date
import kotlin.math.ceil
import kotlin.math.max
import kotlin.random.Random

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
        setupBarChart3()
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
            val y2 = Array(x.size) { 1F }
            var entryList = mutableListOf<BarEntry>()//1本目の線
            for (i in x.indices) {
                entryList.add(
//                    BarEntry(x[i], y[i])
                    BarEntry(x[i], floatArrayOf(y[i], y2[i]))
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, "未入金")
            //③DataSetにフォーマット指定(3章で詳説)
            //dataSet.color = Color.MAGENTA
            dataSet.colors = listOf(Color.MAGENTA, Color.BLUE)
            //リストに格納
            dataSets.add(dataSet)
        }

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        chart.data = data
        chart.description.isEnabled = false
//        chart.setExtraOffsets(0f, 0f, 0f, 0f)

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
        // 左側Y軸を無効
        chart.axisLeft.isEnabled = false
        chart.axisLeft.axisMinimum = 0f
        // 右側
        chart.axisRight.apply {
            setDrawTopYLabelEntry(true)
        }
        chart.axisRight.axisMinimum = 0f
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

    private fun setupBarChart2() {
        val chart = binding.barChart2
        // x軸 1~12月
        val x = FloatArray(12) { it.toFloat() }
        //Data
        val dataSets = mutableListOf<IBarDataSet>()

        //①Entryにデータ格納
        val random = Random(Date().time)

        var maxY: Float
        run {
            val y = Array(x.size) { random.nextInt(50).toFloat() * 10000 / 1000 }
            maxY = y.maxOf { it }
            var entryList = mutableListOf<BarEntry>()
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], y[i])
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, null).apply {
                axisDependency = YAxis.AxisDependency.RIGHT
            }
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.color = ResourcesCompat.getColor(
                resources,
                R.color.gray600, null
            )
            //リストに格納
            dataSets.add(dataSet)
        }

        run {
            // 入金済み
            val y1 = x.map { (random.nextInt(50) * 10000).toFloat() / 1000 }//Y軸データ1
            // 未入金
            val y2 = Array(x.size) { (random.nextInt(40) * 10000).toFloat() / 1000 }//Y軸データ2
            val maxY2 = y1.mapIndexed { index, value -> value + y2[index] }.maxOf { it }
            maxY = max(maxY, maxY2)

            val entryList = mutableListOf<BarEntry>()
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], floatArrayOf(y1[i], y2[i]))
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, null).apply {
                axisDependency = YAxis.AxisDependency.RIGHT
            }
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.colors = listOf(
                ResourcesCompat.getColor(
                    resources,
                    R.color.main700, null
                ),
                ResourcesCompat.getColor(
                    resources,
                    R.color.accent600, null
                ),
            )
            //リストに格納
            dataSets.add(dataSet)
        }

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        chart.data = data
        chart.description.isEnabled = false

        val xAxisSpan = 1f//データのX軸間隔
        val barNumber = 2//棒の本数
        val groupSpace = 0.333f//グループの間隔
        val barSpace = 0.03f//同グループの棒同士の間隔
        val barWidth = (xAxisSpan - groupSpace) / barNumber.toFloat() - barSpace//棒の幅
        data.barWidth = barWidth//棒の幅をbarDataに指定

        //棒のグルーピング
        val startX = chart.barData.dataSets.first().xMin - 0.5f//Xの最小値 - 0.5
        chart.groupBars(startX, groupSpace, barSpace)
        // バーの上に値を表示しない
        data.setDrawValues(false)

        // 凡例のアイコン
        chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            yOffset = -1f//値が大きい程下に下る
            // アイコン
            form = Legend.LegendForm.SQUARE
            formSize = 12f
            // フォント
            textSize = 12f
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
            setCustom(
                listOf(
                    LegendEntry(
                        "入金済",
                        Legend.LegendForm.SQUARE,
                        12f,
                        1f,
                        null,
                        ResourcesCompat.getColor(resources, R.color.main700, null)
                    ),
                    LegendEntry(
                        "未入金",
                        Legend.LegendForm.SQUARE,
                        12f,
                        1f,
                        null,
                        ResourcesCompat.getColor(resources, R.color.accent600, null)
                    ),
                    LegendEntry(
                        "前年度",
                        Legend.LegendForm.SQUARE,
                        12f,
                        1f,
                        null,
                        ResourcesCompat.getColor(resources, R.color.gray600, null)
                    )
                )
            )
        }

        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        chart.xAxis.apply {
            isEnabled = true
            position = XAxis.XAxisPosition.BOTTOM
            // フォント
            textSize = 12f
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
            // 1~12月
            labelCount = 12
            valueFormatter = IndexAxisValueFormatter(Array(12) {
                "%d".format(it + 1)
            })
            // 左右の余白
            spaceMin = 0.6f
            spaceMax = 0.6f
            // 背景グリッドを描画しない
            setDrawGridLines(false)
        }
        // 左側Y軸を無効
        chart.axisLeft.apply {
            isEnabled = false
            axisMinimum = 0f
            axisMaximum = ceil(maxY / 100f) * 100f
        }
        // 右側
        chart.axisRight.apply {
            setDrawTopYLabelEntry(true)
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
            textSize = 12f
            axisMinimum = 0f
            axisMaximum = ceil(maxY / 100f) * 100f
            spaceTop = 6f
            labelCount = 4
            // 右側の縦線
            setDrawAxisLine(false)
        }
        chart.axisRight.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        chart.extraTopOffset = 12f
        Timber.d("chart2 maxY=$maxY axisMax=${chart.axisRight.axisMaximum}")

        //⑦グラフ更新
        chart.invalidate()
    }

    private fun setupBarChart3() {
        val chart = binding.barChart3
        // x軸 1~12月
        val x = FloatArray(12) { it.toFloat() }
        //Data
        val dataSets = mutableListOf<IBarDataSet>()

        //①Entryにデータ格納
        val random = Random(Date().time)
        val maxY: Float
        run {
            // 入金済み
            val y1 = x.map { (random.nextInt(60) * 10000 / 1000).toFloat() }//Y軸データ1
            // 未入金
            val y2 = Array(x.size) { (random.nextInt(40) * 10000 / 1000).toFloat() }//Y軸データ2
            maxY = y1.mapIndexed { index, value -> value + y2[index] }.maxOf { it }

            val entryList = mutableListOf<BarEntry>()
            for (i in x.indices) {
                entryList.add(
                    BarEntry(x[i], floatArrayOf(y1[i], y2[i]))
                )
            }
            //②DataSetにデータ格納
            val dataSet = BarDataSet(entryList, null).apply {
                axisDependency = YAxis.AxisDependency.RIGHT
            }
            //③DataSetにフォーマット指定(3章で詳説)
            dataSet.colors = listOf(
                ResourcesCompat.getColor(
                    resources,
                    R.color.main700, null
                ),
                ResourcesCompat.getColor(
                    resources,
                    R.color.accent600, null
                ),
            )
            dataSet.stackLabels = arrayOf("入金済", "未入金")
            //リストに格納
            dataSets.add(dataSet)
        }

        //④LineDataにLineDataSet格納
        val data = BarData(dataSets)
        //⑤LineChartにLineData格納
        chart.data = data
        chart.description.isEnabled = false
        // 棒の幅 最大1.0F
        data.barWidth = 0.666F//棒の幅をbarDataに指定
        // バーの上に値を表示しない
        data.setDrawValues(false)

        // 凡例のアイコン
        chart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            yOffset = -1f//値が大きい程下に下る
            // アイコン
            form = Legend.LegendForm.SQUARE
            formSize = 12f
            // フォント
            textSize = 12f
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
        }

        //⑥Chartのフォーマット指定(3章で詳説)
        //X軸の設定
        chart.xAxis.apply {
            isEnabled = true
            position = XAxis.XAxisPosition.BOTTOM
            // フォント
            textSize = 12f
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
            // 1~12月
            labelCount = 12
            valueFormatter = IndexAxisValueFormatter(Array(12) {
                "%d".format(it + 1)
            })
            // 左右の余白
            spaceMin = 0.6f
            spaceMax = 0.6f
            // 背景グリッドを描画しない
            setDrawGridLines(false)
        }
        // 左側Y軸を無効
        chart.axisLeft.apply {
            isEnabled = false
            axisMinimum = 0f
            axisMaximum = ceil(maxY / 100f) * 100f
        }
        // 右側
        chart.axisRight.apply {
            setDrawTopYLabelEntry(true)
            textColor = ResourcesCompat.getColor(resources, R.color.gray600, null)
            textSize = 12f
            axisMinimum = 0f
            axisMaximum = ceil(maxY / 100f) * 100f
            spaceTop = 0f
            labelCount = 4
            // 右側の縦線
            setDrawAxisLine(false)
        }
        chart.extraTopOffset = 12f
        Timber.d("chart3 maxY=$maxY axisMax=${chart.axisRight.axisMaximum}")

        chart.axisRight.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        //⑦グラフ更新
        chart.invalidate()
    }
}
