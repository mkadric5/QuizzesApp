//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.models.Grupa
//import ba.etf.rma21.projekat.data.repositories.GrupaRepository
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import org.hamcrest.beans.HasPropertyWithValue.hasProperty
//import org.hamcrest.core.IsIterableContaining.hasItem
//import org.junit.Assert.*
//import org.junit.Test
//import org.hamcrest.CoreMatchers.`is` as Is
//
//
//class GrupaRepositoryUnitTest {
//
//    @Test
//    fun getGroupsByPredmetTest1() {
//        val grupeZaRMA = GrupaRepository.getGroupsByPredmet("RMA")
//
//        assertThat(grupeZaRMA, hasItem<Grupa>(hasProperty("nazivPredmeta",Is("RMA"))))
//        assertThat(grupeZaRMA, hasItem<Grupa>(hasProperty("naziv",Is("RMA grupa 1"))))
//    }
//
//    @Test
//    fun getGroupsByPredmetTest2() {
//        val grupeZaIM1 = GrupaRepository.getGroupsByPredmet("IM1")
//
//        assertThat(grupeZaIM1, hasItem<Grupa>(hasProperty("nazivPredmeta",Is("IM1"))))
//        assertThat(grupeZaIM1, hasItem<Grupa>(hasProperty("naziv",Is("IM1 grupa 2"))))
//
//    }
//
//    @Test
//    fun getGroupsByPredmetTest3() {
//        val grupeZaOOAD = GrupaRepository.getGroupsByPredmet("OOAD")
//
//        assertEquals(0,grupeZaOOAD.size)
//    }
//
//    @Test
//    fun upisiUGrupuTest() {
//        PredmetRepository.upisiNaPredmet("MLTI")
//        GrupaRepository.upisiUGrupu("MLTI grupa 1", "MLTI")
//
//        val mojeGrupe = GrupaRepository.getMojeGrupe()
//
//        assertThat(mojeGrupe, hasItem<Grupa>(hasProperty("naziv",Is("MLTI grupa 1"))))
//    }
//
//    @Test
//    fun upisiUGrupuTest2() {
//        val nova = Grupa("neka grupa", "neki predmet")
//        PredmetRepository.upisiNaPredmet("neki predmet")
//        GrupaRepository.upisiUGrupu("neka grupa", "neki predmet")
//
//        val mojeGrupe = GrupaRepository.getMojeGrupe()
//
//        assertTrue(!mojeGrupe.contains(nova))
//    }
//}