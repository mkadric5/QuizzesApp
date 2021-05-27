//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.repositories.GrupaRepository
//import ba.etf.rma21.projekat.data.repositories.KvizRepository
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import org.hamcrest.Matchers.not
//import org.hamcrest.beans.HasPropertyWithValue.hasProperty
//import org.hamcrest.core.IsIterableContaining.hasItem
//import org.junit.Assert.*
//import org.junit.Test
//import org.hamcrest.CoreMatchers.`is` as Is
//
//class KvizRepositoryUnitTest {
//
//    @Test
//    fun getMyKvizesTest() {
//        val moji = KvizRepository.getMyKvizes()
//
//        assertThat(moji, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IM2"))))
//        assertThat(moji, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("RPR"))))
//        assertThat(moji, not(hasItem<Kviz>(hasProperty("nazivGrupe",Is("TP grupa 1")))))
//    }
//
//    @Test
//    fun getAllTest() {
//        val svi = KvizRepository.getAll()
//
//        assertThat(svi, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("MLTI"))))
//        assertThat(svi, hasItem<Kviz>(hasProperty("naziv",Is("TP kviz 1"))))
//        assertThat(svi, hasItem<Kviz>(hasProperty("nazivGrupe",Is("RMA grupa 1"))))
//    }
//
//    @Test
//    fun getDoneTest() {
//        // samo jedan uradjeni kviz u testnim podacima
//        val uradjeni = KvizRepository.getDone()
//
//        assertThat(uradjeni, hasItem<Kviz>(hasProperty("naziv",Is("IM2 kviz 1"))))
//        assertThat(uradjeni, hasItem<Kviz>(hasProperty("osvojeniBodovi",Is(5F))))
//        assertNotNull(uradjeni[0].datumRada)
//    }
//
//    @Test
//    fun getFutureTest() {
//        PredmetRepository.upisiNaPredmet("RMA")
//        GrupaRepository.upisiUGrupu("RMA grupa 1","RMA")
//        KvizRepository.dodajKviz("RMA grupa 1", "RMA")
//        val buduci = KvizRepository.getFuture()
//
//        assertThat(buduci, hasItem<Kviz>(hasProperty("naziv",Is("RMA kviz 1"))))
//        assertEquals(0,buduci.filter { k -> k.datumRada != null || k.osvojeniBodovi != null }.size)
//    }
//
//    @Test
//    fun getNotTakenTest() {
//        val neuradjeni = KvizRepository.getNotTaken()
//
//        assertThat(neuradjeni, hasItem<Kviz>(hasProperty("nazivPredmeta",Is("IM2"))))
//        assertThat(neuradjeni, hasItem<Kviz>(hasProperty("naziv",Is("IM2 kviz 2"))))
//    }
//
//    @Test
//    fun dodajKvizTest() {
//        PredmetRepository.upisiNaPredmet("NA")
//        GrupaRepository.upisiUGrupu("NA grupa 1","NA")
//        KvizRepository.dodajKviz("NA grupa 1", "NA")
//
//        val moji = KvizRepository.getMyKvizes()
//        val ostali = KvizRepository.getOstaliKvizovi()
//
//        assertThat(moji, hasItem<Kviz>(hasProperty("naziv",Is("NA kviz 1"))))
//        assertThat(ostali, not(hasItem<Kviz>(hasProperty("naziv",Is("NA kviz 1")))))
//    }
//}