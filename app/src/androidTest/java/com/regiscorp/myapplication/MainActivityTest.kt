package com.regiscorp.myapplication


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.regiscorp.myapplication.adapter.ItemAdapter
import com.regiscorp.myapplication.constants.MIN_NUM_OF_COMMITS

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        // wait for loading
        Thread.sleep(5000)

        val lastPosition =
            mActivityTestRule.activity.findViewById<RecyclerView>(R.id.recyclerView).adapter!!.itemCount - 1
        // Attempt to scroll to bottom
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<ItemAdapter.MyViewHolder>(
                lastPosition
            )
        )

        // checking min number
        assert(lastPosition > MIN_NUM_OF_COMMITS)

        Thread.sleep(2000)

        // Match the text in an item below the fold and check that it's displayed.
        val itemElementText = "add gradle wrapper jar"
        onView(withText(itemElementText)).check(matches(isDisplayed()))
    }
}
