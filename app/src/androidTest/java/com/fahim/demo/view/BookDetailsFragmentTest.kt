package com.fahim.demo.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.fahim.demo.R
import com.fahim.demo.database.Book
import com.fahim.demo.getOrAwaitValue
import com.fahim.demo.launchFragmentInHiltContainer
import com.fahim.demo.repository.FakeBookRepositoryIT
import com.fahim.demo.viewmodel.BookViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BookDetailsFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Test
    fun testNavigationFromBookDetailsToImageApi() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<BookDetailsFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.main_imageView)).perform(click())
        Mockito.verify(navController)
            .navigate(BookDetailsFragmentDirections.actionBookDetailsFragmentToImageApiFragment())
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<BookDetailsFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.pressBack()
        Mockito.verify(navController).navigateUp()
    }

    @Test
    fun testSave() {
        val book = Book(
            title = "Machine Learning",
            author = "Mohammed Fahim khan",
            year = "2021", imageUrl = ""
        )
        val testViewModel = BookViewModel(FakeBookRepositoryIT())
        launchFragmentInHiltContainer<BookDetailsFragment>(factory = fragmentFactory) {
            viewModel = testViewModel
            binding.etTitle.editText?.setText(book?.title.toString())
            binding.etName.editText?.setText(book?.author.toString())
            binding.etYear.editText?.setText(book?.year.toString())

        }


        /*onView(withId(R.id.et_title)).perform(replaceText(book.title))
        onView(withId(R.id.et_name)).perform(replaceText(book.author))
        onView(withId(R.id.et_year)).perform(replaceText(book.year))*/
        onView(withId(R.id.btn_save)).perform(click())
        val list = testViewModel.bookList.getOrAwaitValue()
        assertThat(list).contains(book)
    }

}