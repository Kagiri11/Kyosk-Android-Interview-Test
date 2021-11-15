package com.example.kyosktest.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.domain.models.Item
import com.example.kyosktest.databinding.FragmentHomeBinding
import com.example.kyosktest.ui.adapters.OffersAdapter
import com.example.kyosktest.ui.adapters.ProductsAdapter
import com.example.kyosktest.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val offersAdapter = OffersAdapter()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.allChip.isChecked = true

        binding.tvCategories.setOnClickListener {
            viewModel.getItemsByCategory("Cooking Oil")
        }

        binding.apply {
            allChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getAllItems()
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            soapChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("${buttonView.text}")
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            wheatFlourChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("${buttonView.text}")
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            maizeFlourChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("${buttonView.text}")
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            cookingOilChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("${buttonView.text}")
                }
            }

            ricChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("${buttonView.text}")
                }
            }
        }

        observeAllItems()

        viewModel.itemsByCategory.observe(viewLifecycleOwner, {
            val productsAdapter = ProductsAdapter()
            when (it) {
                is Resource.Success -> {
                    productsAdapter.differ.submitList(it.data as List<Item>)
                    binding.tvFoodCategories.text = "By Category"
                    binding.tvNonFood.visibility = INVISIBLE
                    binding.rvFoodCategories.adapter = productsAdapter
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        })

        return binding.root
    }

    private fun observeAllItems() {

        viewModel.nonFoodProducts.observe(viewLifecycleOwner, {
            val productsAdapter = ProductsAdapter()
            when (it) {
                is Resource.Success -> {
                    binding.tvNonFood.visibility = VISIBLE
                    productsAdapter.differ.submitList(it.data as List<Item>)
                    binding.rvNonFoodCategories.adapter = productsAdapter
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        })
        viewModel.allItems.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    offersAdapter.differ.submitList(it.data as List<Item>)
                    Toast.makeText(requireContext(), "${it.data.size}", Toast.LENGTH_LONG).show()
                    binding.rvRandomProducts.adapter = offersAdapter
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        })

        viewModel.foodProducts.observe(viewLifecycleOwner, {
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
        })
    }
}
