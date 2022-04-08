package com.wtfcompany.relax.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "phone")
    var phone: String = ""

    @ColumnInfo(name = "weight")
    var weight: String = ""

    @ColumnInfo(name = "height")
    var height: String = ""

    @ColumnInfo(name = "birthday")
    var birthday: String = ""

    @ColumnInfo(name = "icon")
    var icon: String = ""

}
