package com.example.inikolovski.mvpsampleproject.features.start;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.inikolovski.mvpsampleproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final static String BOOK_TITLE = "Title1";
    private final static String BOOK_AUTHOR = "Author1";
    private final static String BOOK_GENRE = "Genre1";

    private Resources resources;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void onSetup() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }

    @After
    public void tearDown() throws Exception {
//        DbHelper.getInstance().
    }

    @Test
    public void enterTextInAllEditTexts_displaySuccessMessage() {

        onView(withId(R.id.edt_title)) // returns a view interaction object
                .perform(typeText(BOOK_TITLE)); // acting on the object with ViewActions
        onView(withId(R.id.edt_author))
                .perform(typeText(BOOK_AUTHOR));
        onView(withId(R.id.edt_genre))
                .perform(typeText(BOOK_GENRE));

        onFloatingActionButtonClick();

        final String text = resources.getString(R.string.successfully_added_book, BOOK_TITLE);

        checkSnackBarDisplayedByMessage(text);

        onView(withId(R.id.txt_all_books)).perform(click());

        onView(withId(R.id.recycler_view))
                .check(matches(hasDescendant(withText(BOOK_TITLE + BOOK_AUTHOR + BOOK_GENRE)))); // making assertion

    }

    @Test
    public void enterOnlyBookTitle_displayErrorMessage() {
        onView(withId(R.id.edt_title)).perform(typeText(BOOK_TITLE));

        onFloatingActionButtonClick();

        final String text = resources.getString(R.string.error_message);

        checkSnackBarDisplayedByMessage(text);

        onView(withId(R.id.txt_all_books)).perform(click());
    }

    private void checkSnackBarDisplayedByMessage(String message) {
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(message)))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    private void onFloatingActionButtonClick() {
        onView(withId(R.id.fab)).perform(click());
    }

}