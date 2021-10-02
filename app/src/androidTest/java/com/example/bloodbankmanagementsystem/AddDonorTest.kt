package com.example.bloodbankmanagementsystem

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class AddDonorTest {
    @get:Rule
    val testRule = ActivityScenarioRule(DonatebloodActivity::class.java)

    @Test
    fun adddonortest() {

        onView(withId(R.id.edfullname))
            .perform(typeText("Bikash Chauhdary"))
        onView(withId(R.id.edaddress))
            .perform(typeText("bharatpur"))
        onView(withId(R.id.edphone))
            .perform(typeText("989345378"))
        onView(withId(R.id.edage))
            .perform(typeText("23"))

        closeSoftKeyboard()
        onView(withId(R.id.btnaddblood)).perform(scrollTo(), click())
            .perform(click())

    }
}