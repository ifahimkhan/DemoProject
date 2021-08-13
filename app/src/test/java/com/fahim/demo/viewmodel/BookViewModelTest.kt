package com.fahim.demo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fahim.demo.MainCoroutineRule
import com.fahim.demo.getOrAwaitValueTest
import com.fahim.demo.repository.FakeBookRepository
import com.fahim.demo.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BookViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: BookViewModel


    @Before
    fun setUp() {
        viewModel = BookViewModel(FakeBookRepository())
    }

    @Test
    fun `insert Book without year returns error`() {
        viewModel.validateBook("Machine Learning", "Fahim khan", "")
        val value = viewModel.inserBookMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Book without title returns error`() {
        viewModel.validateBook("", "Fahim khan", "2020")
        val value = viewModel.inserBookMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Book without author returns error`() {
        viewModel.validateBook("Machine Learning", "", "2020 ")
        val value = viewModel.inserBookMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}