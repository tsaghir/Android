package com.foi.air1603.sport_manager;


import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompleteUITest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityTestRule = new ActivityTestRule<>(BaseActivity.class);

    public static ViewAction setDate(final int day, final int month, final int year) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((DatePicker) view).updateDate(year, month, day);
            }

            @Override
            public String getDescription() {
                return "Set the date into the datepicker(day, month, year)";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(DatePicker.class);
            }
        };
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

    @Test
    public void completeUITest() {

        ViewInteraction navDrawer = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));

        if (navDrawer != null) {
            navDrawer.perform(click());
            ViewInteraction appCompatCheckedTextView200 = onView(
                    allOf(withId(R.id.design_menu_item_text), withText("Logout"), isDisplayed()));
            appCompatCheckedTextView200.perform(click());
        }

        ViewInteraction textInputEditText = onView(
                withId(R.id.etUsername));
        textInputEditText.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                withId(R.id.etUsername));
        textInputEditText2.perform(scrollTo(), replaceText("vlasnik"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.etUsername), withText("vlasnik")));
        textInputEditText3.perform(pressImeActionButton());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.etUsername), withText("vlasnik")));
        textInputEditText4.perform(scrollTo(), replaceText("vlasnik"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                withId(R.id.etPassword));
        textInputEditText5.perform(scrollTo(), replaceText("m"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.etPassword), withText("m")));
        textInputEditText6.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bPrijava), withText("Sign in"),
                        withParent(withId(R.id.content_login))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.card_view),
                                childAtPosition(
                                        withId(R.id.recycler_places),
                                        0)),
                        0),
                        isDisplayed()));
        SystemClock.sleep(2000);
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("My Sport Facilities"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.recycler_Myplaces)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_my_place_add_appointment)));

        ViewInteraction appCompatEditText = onView(
                withId(R.id.etAppointmentHourStart));
        appCompatEditText.perform(scrollTo(), replaceText("10:00"));

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.etAppointmentHourEnd));
        appCompatEditText2.perform(scrollTo(), replaceText("12:00"));

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.etMaxplayer));
        appCompatEditText3.perform(scrollTo(), replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                withId(R.id.etPasswordA));
        appCompatEditText4.perform(scrollTo(), replaceText("12345"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.etPasswordA), withText("12345")));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.etPasswordA), withText("12345")));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.buttonSetAppointment), withText("Create a Term")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Logout"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());


        ViewInteraction textInputEditText7 = onView(
                withId(R.id.etUsername));
        textInputEditText7.perform(scrollTo(), click());

        ViewInteraction textInputEditText8 = onView(
                withId(R.id.etUsername));
        textInputEditText8.perform(scrollTo(), replaceText("karlo"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.etUsername), withText("karlo")));
        textInputEditText9.perform(pressImeActionButton());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.etUsername), withText("karlo")));
        textInputEditText10.perform(scrollTo(), replaceText("karlo"), closeSoftKeyboard());

        ViewInteraction textInputEditText11 = onView(
                withId(R.id.etPassword));
        textInputEditText11.perform(scrollTo(), replaceText("m"), closeSoftKeyboard());

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.etPassword), withText("m")));
        textInputEditText12.perform(pressImeActionButton());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.bPrijava), withText("Sign in"),
                        withParent(withId(R.id.content_login))));
        appCompatButton6.perform(scrollTo(), click());

        SystemClock.sleep(1000);

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_places),
                        withParent(allOf(withId(R.id.base_main),
                                withParent(withId(R.id.fragment_container)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.buttonPlaceBook), withText("Book a Term")));
        appCompatButton7.perform(scrollTo(), click());


        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.buttonSetAppointment), withText("Create a Term")));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.tvTeamName),
                        withParent(allOf(withId(R.id.base_main),
                                withParent(withId(R.id.fragment_container)))),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Q"), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.actvInviteFriends),
                        withParent(allOf(withId(R.id.base_main),
                                withParent(withId(R.id.fragment_container)))),
                        isDisplayed()));
        appCompatAutoCompleteTextView.perform(replaceText("rpizek.freeride@yahoo.com"), closeSoftKeyboard());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnInviteFriends), withText("Add"),
                        withParent(allOf(withId(R.id.base_main),
                                withParent(withId(R.id.fragment_container)))),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.card_view),
                                childAtPosition(
                                        withId(R.id.recycler_friend_invites),
                                        0)),
                        0),
                        isDisplayed()));
        SystemClock.sleep(500);
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btnSubmit), withText("Complete the reservation"),
                        withParent(allOf(withId(R.id.base_main),
                                withParent(withId(R.id.fragment_container)))),
                        isDisplayed()));

        appCompatButton10.perform(click());
    }

    public static class MyViewAction {

        static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
