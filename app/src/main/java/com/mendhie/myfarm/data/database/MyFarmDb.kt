package com.mendhie.myfarm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mendhie.myfarm.data.models.Farmer

@Database(
    entities = [Farmer::class],
    version = 1,
    exportSchema = false
)
abstract class MyFarmDb : RoomDatabase() {

    abstract fun farmerDao(): FarmerDao
}