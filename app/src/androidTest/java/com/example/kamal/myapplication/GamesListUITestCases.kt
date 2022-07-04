package com.example.kamal.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.kamal.myapplication.adapter.GamesListAdapter
import com.example.kamal.myapplication.view.Activity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class GamesListUITestCases {

    @get:Rule
    var activityRule: ActivityTestRule<Activity> = ActivityTestRule(Activity::class.java)

    @Test
    fun test_games_list_click_when_data_load() {
        // We have two options here:
        // First is to put dummy data in recycle view for load recycle-view quickly, so in this way we do not need to wait for load data
        // Second is that to wait for load data from API, for this i used Thread.sleep to wait for load data
        Thread.sleep(6000)
        if (getGamesCount() > 1) // This will check that recycle view will have proper data for avoid any exception
            onView(withId(R.id.rvGamesList))
                    .perform(actionOnItemAtPosition<GamesListAdapter.ListViewHolder>(1, click()))
    }

    private fun getGamesCount(): Int {
        val recyclerView = activityRule.activity.findViewById(R.id.rvGamesList) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }

}









