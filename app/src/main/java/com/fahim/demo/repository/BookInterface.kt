package com.fahim.demo.repository

import androidx.lifecycle.LiveData
import com.fahim.demo.database.Book
import com.fahim.demo.model.ImageResponse
import com.fahim.demo.util.Resource

interface BookInterface {
    suspend fun insertBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun searchBook(image: String): Resource<ImageResponse>
    fun getBooks(): LiveData<List<Book>>
}