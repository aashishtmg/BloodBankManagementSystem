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
class RegisterUITest {
    @get:Rule
    val testRule = ActivityScenarioRule(SignupActivity::class.java)

    @Test
    fun testSignupUI() {
        onView(withId(R.id.edfname))
            .perform(typeText("Bikash"))
        onView(withId(R.id.edlname))
            .perform(typeText("Thapa"))
        onView(withId(R.id.edusername))
            .perform(typeText("bikash123"))
        onView(withId(R.id.edaddress))
            .perform(typeText("padampur"))
        onView(withId(R.id.edemail))
            .perform(typeText("bikash@gmail.com"))
        onView(withId(R.id.edphone))
            .perform(typeText("9865036355"))
        onView(withId(R.id.edpassword))
            .perform(scrollTo(), click())
            .perform(typeText("bikash"))
        onView(withId(R.id.confirmpassword))
            .perform(scrollTo(),click())
            .perform(typeText("bikash"))
        closeSoftKeyboard()
        onView(withId(R.id.btnsignup)).perform(scrollTo(), click())
            .perform(click())
    }
}