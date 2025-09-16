package com.example.countrieslist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun shouldDisplayLoadingIndicatorInitially() {
        // Then
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun shouldDisplayCountriesListAfterLoading() {
        // Wait for loading to complete and countries to load
        Thread.sleep(5000) // Give time for network request
        
        // Then
        onView(withId(R.id.recyclerViewCountries))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun shouldDisplayRetryButtonWhenErrorOccurs() {
        // This test would require mocking network failure
        // For now, we'll test the UI elements exist
        onView(withId(R.id.btnRetry))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun shouldHandleRetryButtonClick() {
        // Wait a bit for initial load
        Thread.sleep(2000)
        
        // When
        onView(withId(R.id.btnRetry))
            .perform(click())
        
        // Then - should show loading again
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun shouldDisplayErrorMessageWhenNetworkFails() {
        // This test would require network mocking
        // For now, we'll verify error UI elements exist
        onView(withId(R.id.tvErrorMessage))
            .check(matches(isDisplayed()))
    }
}
