package com.example.kyosktest.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.domain.models.Item
import com.example.kyosktest.databinding.FragmentHomeBinding
import com.example.kyosktest.ui.adapters.OffersAdapter
import com.example.kyosktest.ui.adapters.ProductsAdapter
import com.example.kyosktest.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val offersAdapter = OffersAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            viewModel.nonFoodProducts.collect {
                val productsAdapter = ProductsAdapter()
                when (it) {
                    is Resource.Success -> {
                        productsAdapter.differ.submitList(it.data as List<Item>)
                        binding.rvNonFoodCategories.adapter = productsAdapter
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.foodProducts.collect {
                val productsAdapter = ProductsAdapter()
                when (it) {
                    is Resource.Success -> {
                        productsAdapter.differ.submitList(it.data as List<Item>)
                        binding.rvFoodCategories.adapter = productsAdapter
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allItems.collect {
                when (it) {
                    is Resource.Success -> {
                        offersAdapter.differ.submitList(it.data as List<Item>)
                        binding.rvRandomProducts.adapter = offersAdapter
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                    }
                }
            }
        }

        return binding.root
    }
}
