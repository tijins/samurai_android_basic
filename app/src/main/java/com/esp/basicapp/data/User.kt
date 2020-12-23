package com.esp.basicapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    // uidは通し番号なので、insert時に自動で採番される
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "first_name") val name: String?,
    @ColumnInfo(name = "age") val age: Int?
)
