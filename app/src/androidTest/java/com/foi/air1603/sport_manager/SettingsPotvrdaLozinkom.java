package com.foi.air1603.sport_manager;


import android.os.SystemClock;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsPotvrdaLozinkom {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityTestRule = new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void settingsPotvrdaLozinkom() {

        ViewInteraction navDrawer = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));

        if (navDrawer != null) {
            navDrawer.perform(click());
            ViewInteraction appCompatCheckedTextView200 = onView(
                    allOf(withId(R.id.design_menu_item_text), withText("Odjava"), isDisplayed()));
            appCompatCheckedTextView200.perform(click());
        }

        ViewInteraction textInputEditText = onView(
                withId(R.id.etUsername));
        textInputEditText.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                withId(R.id.etUsername));
        textInputEditText2.perform(scrollTo(), replaceText("karlo"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.etUsername), withText("karlo")));
        textInputEditText3.perform(pressImeActionButton());

        ViewInteraction textInputEditText4 = onView(
                withId(R.id.etPassword));
        textInputEditText4.perform(scrollTo(), replaceText("m"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bPrijava), withText("Prijava"),
                        withParent(withId(R.id.content_login))));
        appCompatButton.perform(scrollTo(), click());

        SystemClock.sleep(2000);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        if (appCompatImageButton != null) {
            appCompatImageButton.perform(click());
            ViewInteraction appCompatCheckedTextView = onView(
                    allOf(withId(R.id.design_menu_item_text), withText("Postavke"), isDisplayed()));
            appCompatCheckedTextView.perform(click());
        }


        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switchNFC), isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.switchPassword), isDisplayed()));
        switch_2.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Moje rezervacije"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.main_recycler),
                        withParent(allOf(withId(R.id.swipeRefreshMyReservations),
                                withParent(withId(R.id.base_main)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.myAppointmentConfirm), withText("Potvrdi dolazak"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Password"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")),
                                        withParent(withClassName(is("android.widget.FrameLayout")))),
                                1),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etPasswordV), isDisplayed()));
        textInputEditText5.perform(replaceText("qwert"), closeSoftKeyboard());

        pressBack();
        /*ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnConfirm), withText("Potvrdi dolazak"),
                        withParent(allOf(withId(R.id.activity_password_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.main_recycler),
                        withParent(allOf(withId(R.id.swipeRefreshLayout),
                                withParent(withId(R.id.base_main)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));*/

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
