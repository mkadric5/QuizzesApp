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

    fun dajMojeUradjeneKvizove(): List<Kviz> {
        return KvizRepository.getMyDone().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajMojeBuduceKvizove(): List<Kviz> {
        return KvizRepository.getMyFuture().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajMojeNeuradjeneKvizove(): List<Kviz> {
        return KvizRepository.getMyNotTaken().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun upisiKorisnika(grupaNaziv: String, predmetNaziv: String) {
        KvizRepository.dodajKviz(grupaNaziv, predmetNaziv)
        GrupaRepository.upisiUGrupu(grupaNaziv, predmetNaziv)
        PredmetRepository.upisiNaPredmet(predmetNaziv)
    }
}