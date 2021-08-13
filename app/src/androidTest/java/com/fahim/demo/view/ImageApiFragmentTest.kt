package com.fahim.demo.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.fahim.demo.R
import com.fahim.demo.getOrAwaitValue
import com.fahim.demo.launchFragmentInHiltContainer
import com.fahim.demo.repository.FakeBookRepositoryIT
import com.fahim.demo.view.adapter.ImageRecyclerAdapter
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
class ImageApiFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun selectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val testviewModel = BookViewModel(FakeBookRepositoryIT())
        val selectedImageUrl = "fahimurl"
        launchFragmentInHiltContainer<ImageApiFragment>(factory = fragmentFactory)
        {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testviewModel
            mAdapter.submitList(listOf(selectedImageUrl))
        }
        Espresso.onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.BookViewHolder>(
                0,
                click()
            )
        )

        Mockito.verify(navController).popBackStack()
        assertThat(testviewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)
        assertThat(testviewModel.selectedImageUrl.getOrAwaitValue()).isNotEmpty()

    }
}