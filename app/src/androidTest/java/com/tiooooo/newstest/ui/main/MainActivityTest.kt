package com.tiooooo.newstest.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tiooooo.core.util.EspressoIdlingResource
import com.tiooooo.newstest.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadNews() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNews)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
    }

    @Test
    fun loadNewsByRecent() {
        ActivityScenario.launch(MainActivity::class.java)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withContentDescription(androidx.appcompat.R.string.abc_action_menu_overflow_description)).perform(
            click()
        )

        onView(withText(context.getString(R.string.recent_news))).perform(click())
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNews)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
    }

    @Test
    fun loadNewsByRank() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withContentDescription(androidx.appcompat.R.string.abc_action_menu_overflow_description)).perform(
            click()
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withText(context.getString(R.string.popular))).perform(click())
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNews)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
    }

    @Test
    fun swipeRefresh() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.swipeRefresh)).perform(swipeDown())
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
    }
}