package com.example.brewvault

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.brewvault", appContext.packageName)


        onView(withId(R.id.editText_email)).perform(typeText("Kevin.dyhr@gmail.com"))
        onView(withId(R.id.editText_password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.button_login)).perform(click())
        Log.d("EspressoTest", "Checking if SwipeRefreshLayout is displayed...")
        Thread.sleep(3000)
        onView(withId(R.id.swiperefresh)).check(matches(isDisplayed()))
        Log.d("EspressoTest", "SwipeRefreshLayout is displayed, proceeding with FAB interaction.")

        onView(withId(R.id.fab)).perform(click())



    }
}