package com.example.jatingarg.section1;

/**
 * Created by jatingarg on 21/04/17.
 */
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ApplicationTests {

    private static final String TAG = "ApplicationTests";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.jatingarg.section1", appContext.getPackageName());
    }

    @Test
    public void testViews(){
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_next)).check(matches(isDisplayed()));


        //clicking next menu without entering name
        onView(withId(R.id.menu_next)).perform(click());
        onView(withText("ok")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("ok")).perform(click());

        //clicking next after entering text
        onView(withId(R.id.nameEditText)).perform(typeText("Android"));
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.nameEditText)).check(matches(withText("Android")));
        onView(withId(R.id.nameEditText)).check(matches(withText("Android")));
        onView(withId(R.id.menu_next)).perform(click());
        onView(withId(R.id.nameTextView)).check(matches(isDisplayed()));

        //testing second activity
        onView(withId(R.id.topLeft)).check(matches(isDisplayed()));
        onView(withId(R.id.topRight)).check(matches(isDisplayed()));

        //no need to check for data retention on orientation change as this comes default with a textView
        onView(withId(R.id.nameTextView)).check(matches(isDisplayed()));
        onView(withText("Hello Android")).check(matches(isDisplayed()));

        //IMPORTANT NOTE: - Drag and drop ui testing is not available in espresso, that will have to be checked manually

    }


    public static ViewAction drag(int startX, int startY, int endX, int endY) {
        return new GeneralSwipeAction(
                Swipe.FAST,
                new CustomisableCoordinatesProvider(startX, startY),
                new CustomisableCoordinatesProvider(endX, endY),
                Press.FINGER);
    }
}

 class CustomisableCoordinatesProvider implements CoordinatesProvider {

    private int x;
    private int y;

    public CustomisableCoordinatesProvider(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float[] calculateCoordinates(View view) {
        return new float[]{x,y};
    }
}
