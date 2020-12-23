package com.esp.basicapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = (:userId)")
    fun getUser(userId: Int): User

    @Update
    fun updateUser(user: User)

    @Insert
    fun insert(users: User)

    @Delete
    fun delete(user: User)
}
