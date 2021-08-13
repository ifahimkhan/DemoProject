package com.fahim.demo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book);

    @Delete
    suspend fun deleteBook(book: Book);

    @Update
    suspend fun updateBook(book: Book);

    @Query("select * from books")
    fun observeBooks(): LiveData<List<Book>>

}