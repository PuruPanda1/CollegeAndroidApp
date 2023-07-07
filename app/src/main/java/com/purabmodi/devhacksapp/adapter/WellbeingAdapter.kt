package com.purabmodi.devhacksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.data.models.App
import com.purabmodi.devhacksapp.databinding.InventoryItemRowBinding

class WellbeingAdapter(
    private val onClick: (item: App) -> Unit
) : RecyclerView.Adapter<WellbeingAdapter.ViewHolder>() {

    private var itemList = ArrayList<App>()

    fun setItems(itemList:ArrayList<App>){
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
            item: App,
            onClick: (item: App) -> Unit
        ) {
            binding.itemName.text = item.name
            binding.itemCat.text = ""
            binding.itemDate.text = item.usage
            binding.root.setOnClickListener {
                onClick(item)
            }
//            binding.newsPublishedAt.text = item.publishedAt
            if(item.image.isNotBlank()){
                Glide.with(binding.root.context)
                    .load(item.image)
                    .into(binding.itemImage)
            }else{
                binding.itemImage.foreground = binding.itemImage.context.getDrawable(R.drawable.ic_launcher_foreground)
            }
        }
    }

}