import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest{
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun validate_CourseActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.view_home)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.action_add)).apply {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            perform(ViewActions.click())
        }

        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
        Espresso.onView(ViewMatchers.withId(R.id.course)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.spinner)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.startTime)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bendtime)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.ed_lect)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.note)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}