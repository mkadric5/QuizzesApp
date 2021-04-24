package ba.etf.rma21.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun saveValuesSpinnerTest() {
        onView(withId(R.id.predmeti)).perform(click())

        val godina = "2"
        val predmet = "NA"
        val grupa = "NA grupa 2"

        // odaberemo neke podatke u spinnerima
        onView(withId(R.id.odabirGodina)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`(godina))).perform(click())
        onView(withId(R.id.odabirPredmet)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`(predmet))).perform(click())
        onView(withId(R.id.odabirGrupa)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`(grupa))).perform(click())

        // trebali bi se vratiti na kvizove, a da se odabrane vrijednosti sacuvaju
        pressBack()
        onView(withId(R.id.filterKvizova)).check(matches(isDisplayed()))
        onView(withId(R.id.listaKvizova)).check(matches(isDisplayed()))

        onView(withId(R.id.predmeti)).perform(click())
        onView(withId(R.id.odabirGodina)).check(matches(withSpinnerText(containsString(godina))))
        onView(withId(R.id.odabirPredmet)).check(matches(withSpinnerText(containsString(predmet))))
        onView(withId(R.id.odabirGrupa)).check(matches(withSpinnerText(containsString(grupa))))
    }

    @Test
    fun takeKvizTest() {
        val kvizovi = KvizRepository.getMyKvizes()
        onView(withId(R.id.kvizovi)).perform(click())
        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(kvizovi[1].naziv)),
                hasDescendant(withText(kvizovi[1].nazivPredmeta))), click()))

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(0))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("zelena"))).perform(click())

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(1))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("slon"))).perform(click())

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(2))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("Kolumbo"))).perform(click())

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(3))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("Prag"))).perform(click())

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(4))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("4"))).perform(click())

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(5))
        onView(withId(R.id.odgovoriLista)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), `is`("Razvoj Mobilnih aplikacija"))).perform(click())

        onView(withId(R.id.predajKviz)).check(matches(isDisplayed()))
        onView(withId(R.id.predajKviz)).perform(click())

        onView(withId(R.id.tvPoruka)).check(matches(withText(containsString("Završili ste kviz RPR kviz 1 sa tačnosti 100"))))
        onView(withId(R.id.kvizovi)).check(matches(isDisplayed()))

        onView(withId(R.id.kvizovi)).perform(click())
        onView(withId(R.id.listaKvizova)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(kvizovi[0].naziv)),
                hasDescendant(withText(kvizovi[0].nazivPredmeta))), click()))

        onView(withId(R.id.navigacijaPitanja)).perform(NavigationViewActions.navigateTo(6))
    }
}