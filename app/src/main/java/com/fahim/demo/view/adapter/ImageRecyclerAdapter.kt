package com.fahim.demo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fahim.demo.databinding.ImageRowBinding
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : ListAdapter<String, ImageRecyclerAdapter.BookViewHolder>(object :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {

        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}) {

    private lateinit var onItemClick: (String) -> Unit

    class BookViewHolder(public val itemBinding: ImageRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemBinding = ImageRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val url = getItem(position)
        holder.itemBinding.apply {
            glide.load(url).into(singleArtImageView)
        }

        holder.itemBinding.singleArtImageView.setOnClickListener {
            onItemClick?.let {
                print(url)
                it(url)
            }
        }

    }

    fun setOnItemClickListener(listner: (String) -> Unit) {
        onItemClick = listner
    }


}