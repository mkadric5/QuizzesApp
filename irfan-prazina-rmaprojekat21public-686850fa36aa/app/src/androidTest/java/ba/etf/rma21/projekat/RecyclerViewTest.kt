package ba.etf.rma21.projekat

import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma21.projekat.UtilTestClass.Companion.atPosition
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import io.mockk.every
import io.mockk.mockkObject
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {
    @get:Rule
    val mainLayout = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        var cal: Calendar = Calendar.getInstance()
        cal.set(2021,3,10)
        var cal1: Calendar = Calendar.getInstance()
        cal1.set(4021,6,10)
        var mojiKvizovi = ArrayList(listOf<Kviz>(
                Kviz("Crveni Kviz", "RMA", cal.time, cal.time, null, 4, "RMA Grupa 1", 1.0f),
                Kviz("Plavi Kviz", "RMA", cal.time, cal1.time, cal.time, 4, "RMA Grupa 1", 1.0f),
                Kviz("Zuti Kviz", "RMA", cal1.time, cal1.time, null, 4, "RMA Grupa 1", null),
                Kviz("Zeleni Kviz", "RMA", cal.time, cal1.time, null, 4, "RMA Grupa 1", 1.0f)))
        mockkObject(KvizRepository.Companion)
        every {
            KvizRepository.getMyKvizes()
        } returns mojiKvizovi
    }

    @Test
    fun recyclerViewCheckAllTypes(){
        Espresso.onView(ViewMatchers.withId(R.id.filterKvizova)).perform(ViewActions.click())
        Espresso.onData(
                CoreMatchers.allOf(
                        CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                        CoreMatchers.`is`("Svi kvizovi")
                )
        ).perform(ViewActions.click())
        val kvizoviPrije = KvizRepository.getAll()
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova))
                .check(UtilTestClass.hasItemCount(kvizoviPrije.size))
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.crvena))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.plava))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.zuta))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.zelena))));
    }

    @Test
    fun recyclerViewCheckColors(){

        Espresso.onView(ViewMatchers.withId(R.id.filterKvizova)).perform(ViewActions.click())
        Espresso.onData(
                CoreMatchers.allOf(
                        CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                        CoreMatchers.`is`("Svi moji kvizovi")
                )
        ).perform(ViewActions.click())
        val kvizovi = KvizRepository.getMyKvizes()
        Espresso.onView(ViewMatchers.withId(R.id.listaKvizova))
                .check(UtilTestClass.hasItemCount(4))
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(atPosition(0, ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.crvena)))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(atPosition(1, ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.plava)))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(atPosition(1, ViewMatchers.hasDescendant(ViewMatchers.withText(kvizovi[1].osvojeniBodovi.toString())))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(atPosition(2, ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.zelena)))));
        Espresso.onView(withId(R.id.listaKvizova))
                .check(ViewAssertions.matches(atPosition(3, ViewMatchers.hasDescendant(UtilTestClass.withDrawable(R.drawable.zuta)))));
    }
}