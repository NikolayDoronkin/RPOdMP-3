package com.wtfcompany.relax.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "user_mood",
    primaryKeys = ["user_id", "date"]
)
data class UserMood(
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "mood") val mood: Int,
)
