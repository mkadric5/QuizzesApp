//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import org.hamcrest.Matchers.not
//import org.hamcrest.beans.HasPropertyWithValue.hasProperty
//import org.hamcrest.CoreMatchers.`is` as Is
//import org.hamcrest.core.IsIterableContaining.hasItem
//import org.junit.Assert.*
//import org.junit.Test
//
//class PredmetRepositoryUnitTest {
//
//    @Test
//    fun getUpisaniPredmetiTest() {
//        val upisani = PredmetRepository.getUpisani()
//
//        assertThat(upisani, hasItem<Predmet>(hasProperty("naziv", Is("RPR"))))
//        assertThat(upisani, not(hasItem<Predmet>(hasProperty("godina", Is(3)))))
//    }
//
//    @Test
//    fun getSviPredmetiTest() {
//        val svi = PredmetRepository.getAll()
//
//        assertThat(svi, hasItem<Predmet>(hasProperty("naziv", Is("RMA"))))
//        assertThat(svi, hasItem<Predmet>(hasProperty("naziv", Is("NA"))))
//    }
//
//    @Test
//    fun getPredmetsByGodinaTest1() {
//        val predmetiPrvaGod = PredmetRepository.getPredmetsByGodina(1)
//
//        assertThat(predmetiPrvaGod, hasItem<Predmet>(hasProperty("naziv",Is("IM1"))))
//        assertThat(predmetiPrvaGod, hasItem<Predmet>(hasProperty("naziv",Is("IM2"))))
//        assertThat(predmetiPrvaGod, hasItem<Predmet>(hasProperty("naziv",Is("TP"))))
//        assertThat(predmetiPrvaGod, hasItem<Predmet>(hasProperty("naziv",Is("MLTI"))))
//
//        assertThat(predmetiPrvaGod, not(hasItem<Predmet>(hasProperty("godina",Is(2)))))
//        assertThat(predmetiPrvaGod, not(hasItem<Predmet>(hasProperty("naziv",Is("RMA")))))
//    }
//
//    @Test
//    fun getPredmetsByGodinaTest2() {
//        val predmetiDrugaGod = PredmetRepository.getPredmetsByGodina(2)
//
//        assertThat(predmetiDrugaGod, hasItem<Predmet>(hasProperty("naziv",Is("RMA"))))
//        assertThat(predmetiDrugaGod, hasItem<Predmet>(hasProperty("naziv",Is("NA"))))
//        assertThat(predmetiDrugaGod, hasItem<Predmet>(hasProperty("naziv",Is("RPR"))))
//
//        assertThat(predmetiDrugaGod, not(hasItem<Predmet>(hasProperty("godina",Is(1)))))
//        assertThat(predmetiDrugaGod, not(hasItem<Predmet>(hasProperty("naziv",Is("IM1")))))
//    }
//
//    @Test
//    fun getPredmetsByGodinaTest345() {
//        val predmetiTrecaGod = PredmetRepository.getPredmetsByGodina(3)
//        val predmetiCetvrtaGod = PredmetRepository.getPredmetsByGodina(4)
//        val predmetiPetaGod = PredmetRepository.getPredmetsByGodina(5)
//
//        assertThat(predmetiTrecaGod, not(hasItem<Predmet>(hasProperty("godina",Is(2)))))
//        assertThat(predmetiTrecaGod, not(hasItem<Predmet>(hasProperty("naziv",Is("TP")))))
//
//        assertThat(predmetiCetvrtaGod, not(hasItem<Predmet>(hasProperty("godina",Is(1)))))
//        assertThat(predmetiCetvrtaGod, not(hasItem<Predmet>(hasProperty("naziv",Is("NA")))))
//
//        assertThat(predmetiPetaGod, not(hasItem<Predmet>(hasProperty("godina",Is(2)))))
//        assertThat(predmetiPetaGod, not(hasItem<Predmet>(hasProperty("naziv",Is("MLTI")))))
//    }
//
//    @Test
//    fun upisiNaPredmetTest() {
//        PredmetRepository.upisiNaPredmet("RMA")
//        val upisani = PredmetRepository.getUpisani()
//        val ostali = PredmetRepository.dajOstalePredmeteZaGodinu(2)
//
//        assertThat(upisani, hasItem<Predmet>(hasProperty("naziv", Is("RMA"))))
//        assertThat(ostali, not(hasItem<Predmet>(hasProperty("naziv", Is("RMA")))))
//    }
//}