package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository

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
        return KvizRepository.getDone().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajMojeBuduceKvizove(): List<Kviz> {
        return KvizRepository.getFuture().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun dajMojeNeuradjeneKvizove(): List<Kviz> {
        return KvizRepository.getNotTaken().sortedWith(
                Comparator { k1, k2 -> k1.datumPocetka.compareTo(k2.datumPocetka)})
    }

    fun oznaciKvizKaoUradjen(kviz: Kviz, bodovi: Float) {
        PitanjeKvizRepository.oznaciNeodgovorena(kviz)
        KvizRepository.oznaciKaoUradjen(kviz,bodovi)
    }

    fun dajKviz(nazivKviza: String): Kviz {
        return KvizRepository.getKviz(nazivKviza)
    }
}