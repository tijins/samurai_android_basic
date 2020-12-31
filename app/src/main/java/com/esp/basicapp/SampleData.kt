package com.esp.basicapp

data class DataClass(var name: String)

data class DataClassHasArray(var name: String, var childlen: Array<String>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataClassHasArray

        if (name != other.name) return false
        if (!childlen.contentEquals(other.childlen)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + childlen.contentHashCode()
        return result
    }
}

data class DataClassHasArrayNg(var name: String, var childlen: Array<String>)

class NormalClass(var name: String)

