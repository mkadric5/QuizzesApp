package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.viewmodel.UpisPredmetViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UpisPredmetVMUnitTest {
    private val upisPredmetViewModel = UpisPredmetViewModel()

    @Test
    fun dajOPredmeteZaGodinuTest() {
        val ostaliZaPrvu = upisPredmetViewModel.dajOPredmeteZaGodinu(1)

        assertTrue(ostaliZaPrvu.contains("TP"))
    }

    @Test
    fun dajGrupeZaPredmetTest() {
        val grupeZaRPR = upisPredmetViewModel.dajGrupeZaPredmet("IM1")

        assertEquals(2,grupeZaRPR.size)
        assertTrue(grupeZaRPR.contains("IM1 grupa 1"))
        assertTrue(grupeZaRPR.contains("IM1 grupa 2"))
    }
}