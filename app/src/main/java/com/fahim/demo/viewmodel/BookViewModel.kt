package com.fahim.demo.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.demo.database.Book
import com.fahim.demo.model.ImageResponse
import com.fahim.demo.repository.BookInterface
import com.fahim.demo.util.Resource
import kotlinx.coroutines.launch


class BookViewModel @ViewModelInject constructor(
    private val repo: BookInterface,
) : ViewModel() {


    private val TAG: String = "BookViewModel"
    val bookList = repo.getBooks()

    private var insertBookMsg = MutableLiveData<Resource<Book>>()
    val inserBookMessage: LiveData<Resource<Book>> = insertBookMsg

    fun resetInsertBookMessage() {
        insertBookMsg.postValue(Resource.loading(null))
        Log.e("TAG", "resetInsertBookMessage: ")
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repo.deleteBook(book)
        }

    }

    fun inserBook(book: Book) {
        viewModelScope.launch {
            repo.insertBook(book)
        }
    }

    fun validateBook(title: String, author: String, year: String) {

        if (title.isEmpty() || year.isEmpty() || author.isEmpty()) {
            insertBookMsg.postValue(Resource.error("Enter Title, author, year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertBookMsg.postValue(Resource.error("Year should be number", null))
            return
        }
        val book: Book =
            Book(title = title, author = author, year = year, imageUrl = selectedImage.value ?: "")

        inserBook(book)
        setSelectedImageUrl("")
        insertBookMsg.postValue(Resource.success(book))
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repo.updateBook(book)
        }
    }

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() {
            return selectedImage
        }

    fun setSelectedImageUrl(url: String) {
        selectedImage.postValue(url)
        Log.e("TAG", "setSelectedImageUrl: $url")
    }


    fun searchForImage(string: String) {
        if (string.isBlank()) {
            return
        }
        images.value = Resource.loading(null)

        viewModelScope.launch {
            val response = repo.searchBook(string)
            images.value = response
        }
    }

}