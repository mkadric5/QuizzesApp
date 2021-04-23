package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class UpisPredmetViewModel {

    fun dajOPredmeteZaGodinu(godina: Int): List<String> {
        return PredmetRepository.dajOstalePredmeteZaGodinu(godina)
    }

    fun dajGrupeZaPredmet(naziv: String):List<String> {
        return GrupaRepository.getGroupsByPredmet(naziv).map {
            grupa -> grupa.naziv }
    }

    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
        GrupaRepository.upisiUGrupu(grupaNaziv, predmetNaziv)
        PredmetRepository.upisiNaPredmet(predmetNaziv)
    }
}