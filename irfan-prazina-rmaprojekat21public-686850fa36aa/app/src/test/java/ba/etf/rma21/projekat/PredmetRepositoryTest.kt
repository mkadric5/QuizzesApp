package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import org.hamcrest.Matchers.not
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.core.IsIterableContaining.hasItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class PredmetRepositoryTest {

//    @Test
//    fun getUpisaniPredmetiTest() {
//        val kvizovi = PredmetRepository.getUpisani()
//
//        assertEquals(kvizovi.size,2)
//        assertThat(kvizovi, hasItem<Kviz>(hasProperty("nazivPredmeta", Is("RPR"))))
//        assertThat(kvizovi, not(hasItem<Kviz>(hasProperty("nazivPredmeta", Is("RMA")))))
//    }
}