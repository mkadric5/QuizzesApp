package ba.etf.rma21.projekat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.UtilTestClass.Companion.withDrawable
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith
import java.util.regex.Matcher


@RunWith(AndroidJUnit4::class)
class LayoutTest {
    @get:Rule
    val mainLayout = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun relativeLayoutPositionsPocetna(){
        Espresso.onView(withId(R.id.filterKvizova)).check(
                ViewAssertions.matches(
                        isCompletelyDisplayed()
                )
        )
        Espresso.onView(withId(R.id.listaKvizova)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.upisDugme)).check(ViewAssertions.matches(isCompletelyDisplayed()))

        Espresso.onView(withId(R.id.filterKvizova)).check(isCompletelyAbove(withId(R.id.listaKvizova)));
        Espresso.onView(withId(R.id.filterKvizova)).check(isCompletelyAbove(withId(R.id.upisDugme)));

        Espresso.onView(withId(R.id.upisDugme)).check(isPartiallyRightOf(withId(R.id.listaKvizova)));
        Espresso.onView(withId(R.id.upisDugme)).check(isPartiallyRightOf(withId(R.id.filterKvizova)));
    }

    @Test
    fun relativeLayoutPositionsUpis(){
        Espresso.onView(withId(R.id.upisDugme)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.odabirGodina)).check(
                ViewAssertions.matches(
                        isCompletelyDisplayed()
                )
        )
        Espresso.onView(withId(R.id.odabirPredmet)).check(
                ViewAssertions.matches(
                        isCompletelyDisplayed()
                )
        )
        Espresso.onView(withId(R.id.odabirGrupa)).check(ViewAssertions.matches(isCompletelyDisplayed()))
        Espresso.onView(withId(R.id.dodajPredmetDugme)).check(
                ViewAssertions.matches(
                        isCompletelyDisplayed()
                )
        )

        Espresso.onView(withId(R.id.odabirGodina)).check(isCompletelyAbove(withId(R.id.odabirPredmet)))
        Espresso.onView(withId(R.id.odabirPredmet)).check(isCompletelyAbove(withId(R.id.odabirGrupa)))
        Espresso.onView(withId(R.id.odabirGrupa)).check(isCompletelyAbove(withId(R.id.dodajPredmetDugme)))

    }
}
