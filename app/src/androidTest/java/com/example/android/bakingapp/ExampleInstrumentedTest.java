package com.example.android.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<com.example.android.bakingapp.MainActivity> mainActivityTestRule = new ActivityTestRule<>(com.example.android.bakingapp.MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.android.bakingapp", appContext.getPackageName());
    }

    @Test
    public void elementsDisplayedInMainActivity(){
        onView(withId(R.id.card_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeInAdapter(){
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("Brownies")).check(matches(isDisplayed()));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeItemAdapter_goRecipeDetailsActivity_goRecipeListIngredients(){
        onView(withId(R.id.card_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.master_list_recycler)).check(matches(isDisplayed()));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("List of ingredients")).check(matches(isDisplayed()));
        onView(withId(R.id.master_list_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.list_ingredients_recyclerview)).check(matches(isDisplayed()));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));

    }
}
