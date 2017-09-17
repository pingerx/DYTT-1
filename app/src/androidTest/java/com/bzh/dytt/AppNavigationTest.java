package com.bzh.dytt;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickHomeIconToOpenNavigation() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)));

        onView(withContentDescription(getToolbarNavigationContentDescription(mActivityTestRule.getActivity(), R.id.toolbar))).perform(click());

        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.LEFT)));
    }

    @Test
    public void clickNavigationItemToSwitchPage() {

        clickHomeIconToOpenNavigation();

        onView(withText(R.string.nav_girl_page)).perform(click());

        onView(withId(R.id.girl_page)).check(matches(isDisplayed()));
    }

    /**
     * Returns the content description for the navigation button view in the toolbar.
     */
    public static String getToolbarNavigationContentDescription(@NonNull Activity activity, @IdRes int toolbar1) {
        Toolbar toolbar = (Toolbar) activity.findViewById(toolbar1);
        if (toolbar != null) {
            return (String) toolbar.getNavigationContentDescription();
        } else {
            throw new RuntimeException("No toolbar found.");
        }
    }

}
