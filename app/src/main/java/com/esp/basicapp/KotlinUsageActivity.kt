package com.esp.basicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin_usage.btn_constructor
import kotlinx.android.synthetic.main.activity_kotlin_usage.btn_data
import kotlinx.android.synthetic.main.activity_kotlin_usage.btn_if
import kotlinx.android.synthetic.main.activity_kotlin_usage.btn_it
import kotlinx.android.synthetic.main.activity_kotlin_usage.btn_static
import timber.log.Timber

class KotlinUsageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_usage)

        btn_it.setOnClickListener {
            sampleIt()
        }

        btn_if.setOnClickListener {
            sampleIf()
        }

        btn_static.setOnClickListener {
            sampleStatic()
        }

        btn_data.setOnClickListener {
            sampleData()
        }

        btn_constructor.setOnClickListener {
            sampleConstructor()
        }
    }

    private fun sampleIt() {
        val list = listOf(0, 1, 2, 3)

        // itとは、.alsoや、foreachなどで利用できる処理対象の変数
        list.forEach {
            Timber.d("list.forEach/ it=${it}")
        }
        val sum = list.map { it + 10 }.sumBy { it * 2 }
        Timber.d("listの各要素を+10して、2倍した合計 = $sum")

        // itを省略しない場合
        val sum2 = list.map { elm -> elm + 10 }.sumBy { plus10 -> plus10 * 2 }
        Timber.d("listの各要素を+10して、2倍した合計 = $sum2")
    }

    // Kotlinには3項演算子(Javaの式?A:B)がありません
    // その代わりに、多くのブロック文は、式の一部になれます
    private fun sampleIf() {
        val age = 10
        val message = if (age < 20) {
            "20歳未満の方は登録できません"
        } else {
            "登録完了しました"
        }
        Timber.d(message)

        val message2 = when (age) {
            0 -> "0歳の方は登録できません"
            1 -> "1歳クラスにようこそ"
            2 -> "2歳クラスにようこそ"
            else -> "一般クラスにようこそ"
        }
        Timber.d(message2)

        val message3 = try {
            if (age < 20) {
                throw IllegalArgumentException("20歳未満の方は登録できません")
            }
            "登録完了しました"
        } catch (e: IllegalArgumentException) {
            e.message
        }
        Timber.d(message3)
    }

    private fun sampleStatic() {
        val add = Utility.add(1, 2)
        Timber.d("add= $add")

        val multiply = Utility.multiply(1, 2)
        Timber.d("multiply= $multiply")

        val version = Utility.VERSION
        Timber.d("version= $version")
    }

    private fun sampleData() {
        val data1 = DataClass("suzkuki")
        val data2 = DataClass("suzkuki")
        if (data1 == data2) {
            Timber.d("data1 == data2 です")
        } else {
            Timber.d("data1 == data2 ではありません")
        }

        val normal1 = NormalClass("suzkuki")
        val normal2 = NormalClass("suzkuki")
        if (normal1 == normal2) {
            Timber.d("normal1 == normal2 です")
        } else {
            Timber.d("normal1 == normal2 ではありません")
        }

        val dataHasArray1 = DataClassHasArray("suzkuki", arrayOf("ichiro", "hanako"))
        val dataHasArray2 = DataClassHasArray("suzkuki", arrayOf("ichiro", "hanako"))
        if (dataHasArray1 == dataHasArray2) {
            Timber.d("dataHasArray1 == dataHasArray2 です")
        } else {
            Timber.d("dataHasArray1 == dataHasArray2 ではありません")
        }

        val dataHasArrayNg1 = DataClassHasArrayNg("suzkuki", arrayOf("ichiro", "hanako"))
        val dataHasArrayNg2 = DataClassHasArrayNg("suzkuki", arrayOf("ichiro", "hanako"))
        if (dataHasArrayNg1 == dataHasArrayNg2) {
            Timber.d("dataHasArrayNg1 == dataHasArrayNg2 です")
        } else {
            Timber.d("dataHasArrayNg1 == dataHasArrayNg2 ではありません")
        }
    }

    private fun sampleConstructor() {
        val sample1 = SampleClass("田中", 100)
        val sample2 = SampleClass("鈴木")

        sample1.showInfo()
        sample2.showInfo()
    }
}
