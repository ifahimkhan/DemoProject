package com.fahim.demo.repository

import androidx.lifecycle.LiveData
import com.fahim.demo.api.RetrofitAPI
import com.fahim.demo.database.Book
import com.fahim.demo.database.BookDAO
import com.fahim.demo.model.ImageResponse
import com.fahim.demo.util.Constant
import com.fahim.demo.util.Resource
import javax.inject.Inject

class BookRepository @Inject constructor(
    val dao: BookDAO,
    val retrofitAPI: RetrofitAPI
) :
    BookInterface {
    override suspend fun insertBook(book: Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    override suspend fun updateBook(book: Book) {
        dao.updateBook(book)
    }

    override suspend fun searchBook(image: String): Resource<ImageResponse> {
        return try {

            val response = retrofitAPI.getImages(image, Constant.API_KEY)
            if (response.isSuccessful) {

                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("No data", null)
            }
        } catch (e: Exception) {
            Resource.error("No data", null)
        }
    }

    override fun getBooks(): LiveData<List<Book>> {
        return dao.observeBooks()
    }

}