package com.mendhie.myfarm.presentation.adapters

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.mendhie.myfarm.data.models.Farmer

@BindingAdapter("app:setButtonEnabled")
fun setButtonEnabled(btnAction: Button, farmer: Farmer?){
    if(farmer==null)
        btnAction.isEnabled = false
    farmer?.let {
        btnAction.isEnabled = it.firstName.isNotEmpty() && it.lastName.isNotEmpty() && it.cropType>0
    }
}