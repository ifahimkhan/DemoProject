package com.fahim.demo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahim.demo.database.Book
import com.fahim.demo.model.ImageResponse
import com.fahim.demo.util.Resource

class FakeBookRepository :
    BookInterface {

    private val books = mutableListOf<Book>()
    private val booksLiveData = MutableLiveData<List<Book>>(books)

    override suspend fun insertBook(book: Book) {
        books.add(book)
        refreshData()
    }

    override suspend fun deleteBook(book: Book) {
        books.remove(book)
        refreshData()
    }

    override suspend fun updateBook(book: Book) {
        TODO("Not yet implemented")
    }


    override suspend fun searchBook(image: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    override fun getBooks(): LiveData<List<Book>> {
        return booksLiveData
    }

    private fun refreshData() {
        booksLiveData.postValue(books);
    }

}