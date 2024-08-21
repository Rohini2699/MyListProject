package com.example.mylistproject.presentation.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.mylistproject.databinding.RawItemLayoutBinding
import com.example.mylistproject.domain.model.Item

/**
 * RecyclerView adapter for displaying a list of items.
 */
class ItemRecyclerAdapter :
    RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>() {

    private var items: List<Item> = emptyList()

    /**
     * Sets the data for the adapter.
     * @param items The list of items to be displayed.
     */
    fun setData(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RawItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    /**
     * View holder class for item views.
     * @param binding The binding object containing the layout views.
     */
    inner class ItemViewHolder(private val binding: RawItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds item data to views.
         * @param item The item object to bind.
         */
        fun bind(item: Item) {
            binding.nameOfItem.text = item.full_name
            binding.emailOfItem.text = item.email_address
            binding.teamOfItem.text = "Team: " + item.team
            binding.phoneOfItem.text = "Phone: " + item.phone_number

            val imageUrl = item.photo_url_small
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()

            Glide.with(binding.itemImage.context).load(imageUrl).apply(requestOptions)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error).into(binding.itemImage);
        }
    }
}
