package com.sephora.moviesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sephora.moviesapp.presentation.HomeScreenActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    /*
    * When application starts, it should be able to launch home screen with
    *  tablayout component.
    *
    * Dada a inicialização do aplicativo, a Home Screen Activity deverá ser
    * inicializada e exibir o componente tablayout.
    *
    */

    @get:Rule
    val activityTestRule = ActivityScenarioRule(HomeScreenActivity::class.java)

        @Test
        fun should_be_able_to_launch_home_screen() {
            onView(withId(R.id.tbMenu)).check(matches(isDisplayed()))
        }
}