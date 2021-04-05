package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class KvizListViewModel {

    fun dajMojeKvizove(): List<Kviz> {
        return KvizRepository.getMyKvizes()
    }

    fun dajSveKvizove(): List<Kviz> {
        return KvizRepository.getAll()
    }

    fun dajUradjeneKvizove(): List<Kviz> {
        return KvizRepository.getDone()
    }

    fun dajBuduceKvizove(): List<Kviz> {
        return KvizRepository.getFuture()
    }

    fun dajNeuradjeneKvizove(): List<Kviz> {
        return KvizRepository.getNotTaken()
    }

    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
        GrupaRepository.upisiUGrupu(grupaNaziv)
        PredmetRepository.upisiNaPredmet(predmetNaziv)
    }
}