package com.mendhie.myfarm.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendhie.myfarm.R
import com.mendhie.myfarm.databinding.FragmentHomeBinding
import com.mendhie.myfarm.presentation.adapters.FarmersAdapter
import com.mendhie.myfarm.presentation.interfaces.OnDeleteListener
import com.mendhie.myfarm.presentation.viewmodels.FarmerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnDeleteListener {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = FarmersAdapter(this)

    private val viewModel: FarmerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.txtSearch.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.cnsHeader to "home_search_trans")
            findNavController().navigate(R.id.action_home_to_search, null, null, extras)
        }

        binding.fabAddFarmer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFarmerFragment)
        }

        binding.lstFarmers.layoutManager = LinearLayoutManager(requireContext())
        binding.lstFarmers.adapter = adapter

        viewModel.farmers.observe(viewLifecycleOwner){farmers->
            binding.lnrEmptyState.visibility = if(farmers.isNullOrEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(farmers)
        }

        return binding.root
    }

    override fun onDeleteFarmer(farmerId: Int) {
        viewModel.deleteFarmer(farmerId)
    }

}