package com.foi.air1603.sport_manager;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Register {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityTestRule = new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void register() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.twRegistracija), withText("Nemate račun? Registrirajte se."),
                        withParent(withId(R.id.content_login))));
        appCompatTextView.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                withId(R.id.etMail));
        textInputEditText.perform(scrollTo(), replaceText("rob.piz@yahoo.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                withId(R.id.etUsernameR));
        textInputEditText2.perform(scrollTo(), replaceText("max1"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                withId(R.id.etPasswordR));
        textInputEditText3.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                withId(R.id.etPasswordR1));
        textInputEditText4.perform(scrollTo(), replaceText("1234"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etPasswordR1), withText("1234")));
        textInputEditText5.perform(pressImeActionButton());

        ViewInteraction textInputEditText6 = onView(
                withId(R.id.etName));
        textInputEditText6.perform(scrollTo(), replaceText("robert"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.etName), withText("robert")));
        textInputEditText7.perform(pressImeActionButton());

        ViewInteraction textInputEditText8 = onView(
                withId(R.id.etLastName));
        textInputEditText8.perform(scrollTo(), replaceText("pizek"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.etLastName), withText("pizek")));
        textInputEditText9.perform(pressImeActionButton());

        ViewInteraction textInputEditText10 = onView(
                withId(R.id.etAddress));
        textInputEditText10.perform(scrollTo(), replaceText("graberje27@"), closeSoftKeyboard());

        ViewInteraction textInputEditText11 = onView(
                withId(R.id.etPhoneNumber));
        textInputEditText11.perform(scrollTo(), replaceText("0915376464"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bRegistracija), withText("Registracija"),
                        withParent(withId(R.id.content_register))));
        appCompatButton.perform(scrollTo(), click());

    }

}
