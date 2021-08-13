package com.fahim.demo.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.fahim.demo.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BookDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDb")
    lateinit var database: AppDatabase

    private lateinit var dao: BookDAO

    @Before
    fun setUp() {
        /* database = Room.inMemoryDatabaseBuilder(
             ApplicationProvider.getApplicationContext(),
             AppDatabase::class.java
         ).allowMainThreadQueries().build()*/

        hiltRule.inject()
        dao = database.bookDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertBookTesting() = runBlocking {
        val exampleBook = Book(
            id = 1,
            title = "ArtificialIntelligence",
            author = "Fahim Khan",
            year = "2020",
            imageUrl = ""
        );
        dao.insertBook(exampleBook)
        val list = dao.observeBooks().getOrAwaitValue()
        assertThat(list).contains(exampleBook)
    }

    @Test
    fun deleteBookTesting() = runBlocking {
        val exampleBook = Book(
            id = 1,
            title = "ArtificialIntelligence",
            author = "Fahim Khan",
            year = "2020",
            imageUrl = ""
        );
        dao.insertBook(exampleBook)
        dao.deleteBook(exampleBook)
        val list = dao.observeBooks().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleBook)
    }

}