package com.mendhie.myfarm.presentation.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mendhie.myfarm.R
import com.mendhie.myfarm.data.models.Farmer
import com.mendhie.myfarm.databinding.FragmentAddFarmerBinding
import com.mendhie.myfarm.presentation.viewmodels.FarmerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFarmerFragment : Fragment() {

    private var farmer = Farmer()
    private lateinit var binding: FragmentAddFarmerBinding
    private val viewModel: FarmerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddFarmerBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toolbar.apply {
            //set default toolbar title and navigationIcon click
            setNavigationOnClickListener{findNavController().navigateUp()}
        }

        viewModel.farmer.observe(viewLifecycleOwner){
            farmer = it
        }

        binding.spnCropType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItemText: String = parent!!.getItemAtPosition(position).toString()
                farmer.cropType = position
                farmer.cropName = selectedItemText

                viewModel.updateFarmer(farmer)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        
        binding.btnAddFarmer.setOnClickListener { 
            viewModel.addFarmer(farmer).observe(viewLifecycleOwner){response->
                if(response==null)
                    return@observe
                if(response>0)
                    displayDialog()
                else
                    Toast.makeText(requireContext(), "Could not save farmer", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun displayDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_farmer_added, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        val btnClose = dialog.findViewById<Button>(R.id.btnClose)
        btnClose?.setOnClickListener {
            dialog.cancel()
            findNavController().navigateUp()
        }
    }

}