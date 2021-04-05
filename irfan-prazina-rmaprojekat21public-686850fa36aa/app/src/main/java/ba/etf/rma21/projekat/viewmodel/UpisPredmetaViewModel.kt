package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class UpisPredmetaViewModel {

    fun dajPredmeteZaGodinu(godina: Int): List<String> {
        return PredmetRepository.ostaliPredmeti.filter { predmet -> predmet.godina == godina }.map {
            predmet -> predmet.naziv }
    }

    fun dajGrupeZaPredmet(naziv: String):List<String> {
        return GrupaRepository.getGroupsByPredmet(naziv).map {
            grupa -> grupa.naziv }
    }
}