package ba.etf.rma21.projekat.viewmodel

import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
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
        return PredmetIGrupaRepository.dajNeupisanePredmeteZaGodinu(godina)
    }

    fun popuniGrupeZaPredmet(actionGrupe: ((grupe: List<Grupa>) -> Unit), predmetId: Int) {
        scope.launch {
            if (predmetId > 0)
            actionGrupe.invoke(dajGrupeZaPredmet(predmetId))
            else actionGrupe.invoke(listOf())
        }
    }

    private suspend fun dajGrupeZaPredmet(predmetId: Int): List<Grupa> {
        return PredmetIGrupaRepository.getGrupeZaPredmet(predmetId)
    }

    fun upisiKorisnika(actionUpis: (fragmentPoruka: Fragment) -> Unit, fragmentPoruka: Fragment, grupaId: Int) {
        scope.launch {
            if (PredmetIGrupaRepository.upisiUGrupu(grupaId))
                actionUpis.invoke(fragmentPoruka)
        }
    }

//    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
//        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
//        GrupaRepository.upisiUGrupu(grupaNaziv, predmetNaziv)
//        PredmetRepository.upisiNaPredmet(predmetNaziv)
//    }
}