package com.codetest.main

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.hasChildCount
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.codetest.R
import com.codetest.main.model.Location
import com.codetest.main.model.Status
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class WeatherForecastActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<WeatherForecastActivity>
            = ActivityTestRule(WeatherForecastActivity::class.java, false, false)

    @Test
    fun shouldShowRightForecastsCount_whenSuccessResponse() {
        //Setup
        val locationHelperMock = mockk<LocationHelper>()
        val locations = listOf(
            Location("id", "City", "10", Status.BARELY_SUNNY),
            Location("id2", "City 2", "5", Status.CLOUDY)
        )

        every { locationHelperMock.getLocations(any()) } answers {
            firstArg<(List<Location>) -> Unit>().invoke(locations)
        }

        loadKoinModules(
            module {
                single<LocationHelper> {
                    locationHelperMock
                }
            }
        )

        //Action
        activityRule.launchActivity(Intent())

        //Assertion
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(2)))

        //Tear down
        confirmVerified()
    }

}