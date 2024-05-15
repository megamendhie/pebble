package com.mendhie.myfarm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmer_table")
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var cropType: Int = 0,
    var cropName: String = ""
)
