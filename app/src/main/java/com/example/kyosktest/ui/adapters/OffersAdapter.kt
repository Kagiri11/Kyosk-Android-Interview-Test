package com.example.kyosktest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.domain.models.Item
import com.example.kyosktest.databinding.ItemOfferBinding
import com.example.kyosktest.databinding.ItemProductBinding

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val binding: ItemOfferBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemOfferBinding = ItemOfferBinding.inflate(layoutInflater, parent,false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            Glide.with(this.root)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(productImage)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
