package com.mendhie.myfarm.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mendhie.myfarm.data.models.Farmer
import com.mendhie.myfarm.domain.repositories.FarmerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmerViewModel @Inject constructor(private val repo: FarmerRepo): ViewModel() {

    val farmers = repo.farmerDao.getAllFarmers()

    private val _farmer: MutableLiveData<Farmer> = MutableLiveData(Farmer())
    val farmer: LiveData<Farmer>
        get() = _farmer

    //Update farmer firstName with text from text input field
    fun firstNameChanged(text: CharSequence) {
        val currentFarmer = _farmer.value ?: Farmer()
        _farmer.value = currentFarmer.copy(firstName = text.toString())
    }

    //Update lastName with text from text input field
    fun lastNameChanged(text: CharSequence) {
        val currentFarmer = _farmer.value ?: Farmer()
        _farmer.value = currentFarmer.copy(lastName = text.toString())
    }

    fun updateFarmer(farmer: Farmer){
        _farmer.value = farmer
    }

    //add farmer to database
    fun addFarmer(farmer: Farmer): MutableLiveData<Long>{
        val addFarmerResult: MutableLiveData<Long> = MutableLiveData()
        viewModelScope.launch {
            val result = repo.addFarmer(farmer)
            addFarmerResult.value = result
        }
        return addFarmerResult
    }

    //get search results from query
    fun getSearchResults(searchQuery: String): LiveData<List<Farmer>>{
        return repo.farmerDao.getSearchResults(searchQuery)
    }

    //delete farmer from database
    fun deleteFarmer(farmerId: Int){
        viewModelScope.launch {
            repo.farmerDao.deleteFarmer(farmerId)
        }
    }
}