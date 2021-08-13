package com.fahim.demo.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.fahim.demo.R
import com.fahim.demo.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class BookFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {

    }

    @Test
    fun testNavigationFromBooktoBookDetails() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<BookFragment>(factory = fragmentFactory) {
//            assertThat(this.isVisible).isFalse()
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.floatingActionButton)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            BookFragmentDirections.actionBookFragmentToBookDetailsFragment()
        )
    }
}