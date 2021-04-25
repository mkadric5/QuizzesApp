package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import ba.etf.rma21.projekat.viewmodel.KvizListViewModel
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.hamcrest.core.IsIterableContaining.hasItem
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class KvizListVMUnitTest {
    private var kvizListViewModel = KvizListViewModel()

    private fun isSorted(kvizovi: List<Kviz>): Boolean {
        for (i in 0 until (kvizovi.size-2)) {
            if (kvizovi[i].datumPocetka > kvizovi[i+1].datumPocetka)
                return false
        }
        return true
    }

    @Test
    fun dajMojeKvizoveTest() {
        val moji = kvizListViewModel.dajMojeKvizove()

        assertTrue(isSorted(moji))
        assertThat(moji,hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IM2"))))
        assertThat(moji,hasItem<Kviz>(hasProperty("nazivPredmeta", Is("RPR"))))
    }

    @Test
    fun dajSveKvizoveTest() {
        val svi = kvizListViewModel.dajSveKvizove()

        assertTrue(isSorted(svi))
        assertThat(svi, hasItem<Kviz>(hasProperty("naziv",Is("TP kviz 2"))))
        assertThat(svi, hasItem<Kviz>(hasProperty("nazivGrupe",Is("RMA grupa 2"))))
    }

    @Test
    fun dajMojeUradjeneTest() {
        val uradjeni = kvizListViewModel.dajMojeUradjeneKvizove()

        assertTrue(isSorted(uradjeni))
        assertNotNull(uradjeni[0].datumRada)
        assertNotNull(uradjeni[0].osvojeniBodovi)
    }

    @Test
    fun dajMojeBuduceTest() {
        PredmetRepository.upisiNaPredmet("IM1")
        GrupaRepository.upisiUGrupu("IM1 grupa 2","IM1")
        KvizRepository.dodajKviz("IM1 grupa 2", "IM1")
        val buduci = kvizListViewModel.dajMojeBuduceKvizove()

        assertThat(buduci, hasItem<Kviz>(hasProperty("naziv",Is("IM1 kviz 2"))))
        assertEquals(0,buduci.filter { k -> k.datumRada != null || k.osvojeniBodovi != null }.size)
    }

    @Test
    fun dajMojeNeuradjeneTest() {
        val neuradjeni = kvizListViewModel.dajMojeNeuradjeneKvizove()

        assertThat(neuradjeni, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IM2"))))
        assertThat(neuradjeni, hasItem<Kviz>(hasProperty("naziv",Is("IM2 kviz 2"))))
    }

//    @Test
//    fun upisiKorisnikaTest() {
//        kvizListViewModel.upisiKorisnika("MLTI grupa 1","MLTI")
//        val moji = kvizListViewModel.dajMojeKvizove()
//
//        assertThat(moji, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("MLTI"))))
//        assertThat(moji, hasItem<Kviz>(hasProperty("nazivGrupe",Is("MLTI grupa 1"))))
//    }
}