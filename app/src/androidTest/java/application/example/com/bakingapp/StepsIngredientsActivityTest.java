package application.example.com.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Created by Dell on 15-08-2017.
 */
@RunWith(AndroidJUnit4.class)
public class StepsIngredientsActivityTest {
    @Rule
    public ActivityTestRule<StepsIngredientsActivity>activityActivityTestRule=new ActivityTestRule<>(StepsIngredientsActivity.class);
    @Test
    public void recyclerView_StepsIngredientActivity(){
    onData(anything()).inAdapterView(withId(R.id.list_steps)).atPosition(0).perform(click());
        onView(withId(R.id.long_desrciption)).check(matches(isDisplayed()));
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }
}
