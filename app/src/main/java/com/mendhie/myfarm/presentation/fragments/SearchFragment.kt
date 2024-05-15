package com.mendhie.myfarm.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.mendhie.myfarm.R
import com.mendhie.myfarm.data.models.Farmer
import com.mendhie.myfarm.databinding.FragmentSearchBinding
import com.mendhie.myfarm.presentation.adapters.FarmersAdapter
import com.mendhie.myfarm.presentation.interfaces.OnDeleteListener
import com.mendhie.myfarm.presentation.viewmodels.FarmerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), OnDeleteListener {
    private lateinit var binding: FragmentSearchBinding

    private var defaultSearchResults = listOf<Farmer>()
    private val viewModel: FarmerViewModel by viewModels()
    private val adapter = FarmersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        //Set adapter and layoutManager for search recyclerView
        binding.lstSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.lstSearch.adapter = adapter

        viewModel.farmers.observe(viewLifecycleOwner){results->
            defaultSearchResults = results
            if(binding.edtSearch.text.toString().isEmpty()) {
                adapter.submitList(defaultSearchResults)
                binding.crdSearchResults.visibility = if(defaultSearchResults.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
        observeSearchEditText()

        return binding.root
    }

    private fun observeSearchEditText() {
        binding.edtSearch.doOnTextChanged { _, _, _, _ ->
            val query = binding.edtSearch.text.toString().trim()
            val searchQuery = "%$query%"
            if(query.isEmpty()){
                adapter.submitList(defaultSearchResults)
                binding.crdSearchResults.visibility = if(defaultSearchResults.isNotEmpty()) View.VISIBLE else View.GONE
            }
            else{
                viewModel.getSearchResults(searchQuery).observe(viewLifecycleOwner){searchResults->
                    if(binding.edtSearch.text.toString().trim().isNotEmpty()){
                        adapter.submitList(searchResults)
                        binding.crdSearchResults.visibility = if(searchResults.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }

        }
    }

    override fun onDeleteFarmer(farmerId: Int) {
        viewModel.deleteFarmer(farmerId)
    }
}