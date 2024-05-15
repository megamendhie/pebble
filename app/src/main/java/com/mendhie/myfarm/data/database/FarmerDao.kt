package com.mendhie.myfarm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mendhie.myfarm.data.models.Farmer

@Dao
interface FarmerDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertFarmer(farmer: Farmer): Long

    @Query("SELECT * FROM farmer_table")
    fun getAllFarmers(): LiveData<List<Farmer>>

    @Query("SELECT * FROM farmer_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    fun getSearchResults(searchQuery: String): LiveData<List<Farmer>>

    @Query("DELETE FROM farmer_table WHERE id = :farmerId")
    suspend fun deleteFarmer(farmerId: Int)
}