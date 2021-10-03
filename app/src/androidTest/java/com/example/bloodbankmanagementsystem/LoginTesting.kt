package com.example.bloodbankmanagementsystem

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun checkArithmetic() {
        onView(withId(R.id.email))
            .perform(typeText("aashish@gmail.com"))

        onView(withId(R.id.password))
            .perform((typeText("aashish")))

        closeSoftKeyboard()

        onView(withId(R.id.login))
            .perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.tablayout))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}