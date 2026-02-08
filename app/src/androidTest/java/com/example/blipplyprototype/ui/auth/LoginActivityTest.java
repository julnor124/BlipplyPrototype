package com.example.blipplyprototype.ui.auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.blipplyprototype.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void shows_email_error_when_missing() {
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.input_email)).check(matches(hasErrorText("Email is required")));
    }

    @Test
    public void navigates_to_catalog_on_valid_login() {
        onView(withId(R.id.input_email)).perform(typeText("user@example.com"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());
        onView(withId(R.id.rvProducts)).check(matches(isDisplayed()));
    }
}
