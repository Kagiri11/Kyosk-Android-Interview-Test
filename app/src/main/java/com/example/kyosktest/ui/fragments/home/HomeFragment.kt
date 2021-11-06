package com.example.kyosktest.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.apply {
            allChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
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

                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            wheatFlourChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("Wheat Flour")

                    viewModel.itemsByCategory.observe(viewLifecycleOwner, {
                        val productsAdapter = ProductsAdapter()
                        when (it) {
                            is Resource.Success -> {
                                productsAdapter.differ.submitList(it.data as List<Item>)
                                Toast.makeText(requireContext(), "${it.data.size}", Toast.LENGTH_LONG).show()
                                println(it.data.size)
                                binding.rvFoodCategories.adapter = productsAdapter
                                binding.rvNonFoodCategories.adapter = productsAdapter
                            }
                            is Resource.Loading -> {
                            }
                            is Resource.Error -> {
                            }
                        }
                    })
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            maizeFlourChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("Maize Flour")
                    viewModel.itemsByCategory.observe(viewLifecycleOwner, {
                        val productsAdapter = ProductsAdapter()
                        when (it) {
                            is Resource.Success -> {
                                productsAdapter.differ.submitList(it.data as List<Item>)
                                Toast.makeText(requireContext(), "${it.data.size}", Toast.LENGTH_LONG).show()
                                binding.rvFoodCategories.adapter = productsAdapter
                                binding.rvNonFoodCategories.adapter = productsAdapter
                            }
                            is Resource.Loading -> {
                            }
                            is Resource.Error -> {
                            }
                        }
                    })
                    Toast.makeText(requireContext(), "${viewModel.categoriesList.size}", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Movies Fragment", "")
                }
            }
            cookingOilChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("Cooking Oil")
                    Toast.makeText(requireContext(), "${viewModel.categoriesList.size}", Toast.LENGTH_LONG).show()
                }
            }

            ricChip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel.getItemsByCategory("Rice")
                    Toast.makeText(requireContext(), "${viewModel.categoriesList.size}", Toast.LENGTH_LONG).show()
                }
            }
        }
//
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

        viewModel.nonFoodProducts.observe(viewLifecycleOwner, {
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
        })

//        viewModel.itemsByCategory.observe(viewLifecycleOwner, {
//            val productsAdapter = ProductsAdapter()
//            when (it) {
//                is Resource.Success -> {
//                    productsAdapter.differ.submitList(it.data as List<Item>)
//                    binding.rvFoodCategories.adapter = productsAdapter
//                }
//                is Resource.Loading -> {
//                }
//                is Resource.Error -> {
//                }
//            }
//        })


//        viewModel.itemsByCategory.observe(viewLifecycleOwner, {
//            val productsAdapter = ProductsAdapter()
//            val offersAdapter = OffersAdapter()
//            when (it) {
//                is Resource.Success -> {
//                    offersAdapter.differ.submitList(it.data as List<Item>)
//                    productsAdapter.differ.submitList(it.data as List<Item>)
//                    Toast.makeText(requireContext(), "${it.data.size}", Toast.LENGTH_LONG).show()
//                    binding.rvFoodCategories.adapter = productsAdapter
//                    binding.rvNonFoodCategories.adapter = productsAdapter
//                }
//                is Resource.Loading -> {
//                }
//                is Resource.Error -> {
//                }
//            }
//        })

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.allItems.collect {
//                when (it) {
//                    is Resource.Success -> {
//                        offersAdapter.differ.submitList(it.data as List<Item>)
//                        binding.rvRandomProducts.adapter = offersAdapter
//                    }
//                    is Resource.Error -> {
//                    }
//                    is Resource.Loading -> {
//                    }
//                }
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.nonFoodProducts.collect {
//                val productsAdapter = ProductsAdapter()
//                when (it) {
//                    is Resource.Success -> {
//                        productsAdapter.differ.submitList(it.data as List<Item>)
//                        binding.rvNonFoodCategories.adapter = productsAdapter
//                    }
//                    is Resource.Loading -> {
//                    }
//                    is Resource.Error -> {
//                    }
//                }
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.foodProducts.collect {
//                val productsAdapter = ProductsAdapter()
//
//            }
//        }

        return binding.root
    }
}
