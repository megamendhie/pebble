package com.mendhie.myfarm.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.mendhie.myfarm.data.database.FarmerDao
import com.mendhie.myfarm.data.models.Farmer
import javax.inject.Inject

class FarmerRepo @Inject constructor(val farmerDao: FarmerDao) {

    suspend fun addFarmer(farmer: Farmer):Long{
        return farmerDao.insertFarmer(farmer)
    }
}