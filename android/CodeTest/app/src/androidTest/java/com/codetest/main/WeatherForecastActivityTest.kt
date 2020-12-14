package com.codetest.main

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.codetest.R
import com.codetest.main.features.weather_list.WeatherForecastActivity
import com.codetest.network.model.Location
import com.codetest.network.model.LocationsResponse
import com.codetest.network.model.Status
import com.codetest.network.repository.LocationRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.hamcrest.Matchers.allOf
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
        val locationRepository = mockk<LocationRepository>()
        val locations = listOf(
            Location("id", "City", "10", Status.BARELY_SUNNY),
            Location("id2", "City 2", "5", Status.CLOUDY)
        )

        every { locationRepository.getLocations() } returns Observable.just(LocationsResponse(locations))

        loadKoinModules(
            module(override = true) {
                single {
                    locationRepository
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

    @Test
    fun shouldShowErrorMessage_whenErrorResponse() {
        //Setup
        val locationRepository = mockk<LocationRepository>()

        every { locationRepository.getLocations() } returns Observable.error(Exception("Mocked Error"))

        loadKoinModules(
            module(override = true) {
                single {
                    locationRepository
                }
            }
        )

        //Action
        activityRule.launchActivity(Intent())

        //Assertion
        onView(withText("Error"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        //Tear down
        confirmVerified()
    }

}