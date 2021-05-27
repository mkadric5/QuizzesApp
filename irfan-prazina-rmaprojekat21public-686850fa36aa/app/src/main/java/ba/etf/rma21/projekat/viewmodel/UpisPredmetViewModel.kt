package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UpisPredmetViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val predmeti = mutableListOf<Predmet>()
//    fun dajOPredmeteZaGodinu(godina: Int): List<String> {
//        return PredmetRepository.dajOstalePredmeteZaGodinu(godina)
//    }

    fun dajOPredmeteZaGodinu(godina: Int): List<String> {
        var result = listOf<String>()
        scope.launch {
            result = PredmetIGrupaRepository.getNeupisaniPredmeti().map { p -> p.naziv }
        }
        return result
    }

    fun popuniPredmeteZaGodinu(actionPredmeti: ((predmeti: List<Predmet>) -> Unit), godina: Int) {
        scope.launch {
            actionPredmeti.invoke(dajPredmeteZaGodinu(godina))
        }
    }

    private suspend fun dajPredmeteZaGodinu(godina: Int): List<Predmet> {
        return PredmetIGrupaRepository.dajPredmeteZaGodinu(godina)
    }

    fun popuniGrupeZaPredmet(actionGrupe: ((grupe: List<Grupa>) -> Unit), predmetId: Int) {
        scope.launch {
            actionGrupe.invoke(dajGrupeZaPredmet(predmetId))
        }
    }

    private suspend fun dajGrupeZaPredmet(predmetId: Int): List<Grupa> {
        return PredmetIGrupaRepository.getGrupeZaPredmet(predmetId)
    }

//    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
//        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
//        GrupaRepository.upisiUGrupu(grupaNaziv, predmetNaziv)
//        PredmetRepository.upisiNaPredmet(predmetNaziv)
//    }
}