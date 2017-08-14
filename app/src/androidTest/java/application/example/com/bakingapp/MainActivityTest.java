package application.example.com.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Dell on 15-08-2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerViewItem_openStepsIngredientActivity(){
        onData(anything()).inAdapterView(withId(R.id.recycler_bake_list)).atPosition(1).perform(click());
        onData(anything()).inAdapterView(withId(R.id.list_ingredients)).atPosition(1).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_steps)).atPosition(1).check(matches(isDisplayed()));

    }
}
