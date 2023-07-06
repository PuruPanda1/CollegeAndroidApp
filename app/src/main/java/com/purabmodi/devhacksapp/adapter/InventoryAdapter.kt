package com.purabmodi.devhacksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.purabmodi.devhacksapp.data.models.Item
import com.purabmodi.devhacksapp.databinding.InventoryItemRowBinding

class InventoryAdapter(

    private val onClick: (item: Item) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

    private var itemList = ArrayList<Item>()

    fun setItems(itemList:ArrayList<Item>){
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InventoryItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, onClick)
    }

    class ViewHolder(private val binding: InventoryItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Item,
            onClick: (item: Item) -> Unit
        ) {
            binding.itemName.text = item.name
            binding.itemCat.text = item.category
            binding.itemDate.text = item.submitDate
            binding.root.setOnClickListener {
                onClick(item)
            }
//            binding.newsPublishedAt.text = item.publishedAt
            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.itemImage)
        }
    }

    class Comparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
}