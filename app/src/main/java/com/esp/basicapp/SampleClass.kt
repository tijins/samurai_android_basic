package com.esp.basicapp

import timber.log.Timber

class SampleClass(var name: String, var age: Int) {
    constructor(name: String) : this(name, 0)

    var nickname = "${name}ちゃん"

    init {
        Timber.d("$name のインスタンスを作成しました")
    }

    fun showInfo() {
        Timber.d("${name}さん(${age})のニックネームは${nickname}です")
    }
}
