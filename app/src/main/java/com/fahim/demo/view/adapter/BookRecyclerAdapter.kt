package com.fahim.demo.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fahim.demo.database.Book
import com.fahim.demo.databinding.BookRowBinding
import javax.inject.Inject

class BookRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : ListAdapter<Book, BookRecyclerAdapter.BookViewHolder>(object :
    DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

}) {
/*
    private val diffutil = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {

            return oldItem.id == newItem.id
        }

    }
*/

    class BookViewHolder(public val itemBinding: BookRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root){

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemBinding = BookRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.itemBinding.apply {
            glide.load(book.imageUrl).into(rowImage)
            rowTitle.text = book.title
            rowName.text = book.author
            rowYear.text = book.year
        }

    }


}