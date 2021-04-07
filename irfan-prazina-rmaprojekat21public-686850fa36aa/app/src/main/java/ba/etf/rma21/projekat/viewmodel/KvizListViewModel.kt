package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class KvizListViewModel {

    fun dajMojeKvizove(): List<Kviz> {
        return KvizRepository.getMyKvizes().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajSveKvizove(): List<Kviz> {
        return KvizRepository.getAll().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajUradjeneKvizove(): List<Kviz> {
        return KvizRepository.getDone().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajBuduceKvizove(): List<Kviz> {
        return KvizRepository.getFuture().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajNeuradjeneKvizove(): List<Kviz> {
        return KvizRepository.getNotTaken().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
        GrupaRepository.upisiUGrupu(grupaNaziv, predmetNaziv)
        PredmetRepository.upisiNaPredmet(predmetNaziv)
    }
}