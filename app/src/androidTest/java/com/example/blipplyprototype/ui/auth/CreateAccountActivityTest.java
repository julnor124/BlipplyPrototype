package com.example.blipplyprototype.ui.auth;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.blipplyprototype.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest {

    @Rule
    public ActivityScenarioRule<CreateAccountActivity> activityRule =
            new ActivityScenarioRule<>(CreateAccountActivity.class);

    @Test
    public void shows_business_name_error_when_missing() {
        onView(withId(R.id.buttonCreateAccount)).perform(click());
        onView(withId(R.id.inputBusinessName))
                .check(matches(hasErrorText("Business name is required")));
    }
}
