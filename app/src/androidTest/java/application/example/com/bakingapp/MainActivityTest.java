package application.example.com.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by Dell on 15-08-2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

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
        public void mainActivityTest() {
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(hasDescendant(withText("Nutella Pie"))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(hasDescendant(withText("Brownies"))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(hasDescendant(withText("Yellow Cake"))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(hasDescendant(withText("Cheesecake"))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(not(hasDescendant(withText("xxxaaa111")))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(not(hasDescendant(withText("")))));
            onView(withId(R.id.recycler_bake_list))
                    .check(matches(not(hasDescendant(withText(" ")))));

            ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_bake_list)
                        ,
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.list_steps))
                    .check(matches(hasDescendant(withText("Recipe Introduction"))));







        }
}