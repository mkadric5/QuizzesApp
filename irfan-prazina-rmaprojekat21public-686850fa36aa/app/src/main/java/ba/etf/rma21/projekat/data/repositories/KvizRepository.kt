package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import java.util.*

class KvizRepository {

    companion object {
        var mojiKvizovi: MutableList<Kviz> = upisaniKvizovi().toMutableList()
        var ostaliKvizovi: MutableList<Kviz> = neupisaniKvizovi().toMutableList()

        fun getMyKvizes(): List<Kviz> {
            return mojiKvizovi
        }

        fun getAll(): List<Kviz> {
            val sviKvizovi: MutableList<Kviz> = mojiKvizovi.toMutableList()
            sviKvizovi.addAll(ostaliKvizovi)
            return sviKvizovi
        }

        fun getDone(): List<Kviz> {
            return mojiKvizovi.filter { kviz -> kviz.datumRada != null && kviz.osvojeniBodovi != null }
        }

        fun getFuture(): List<Kviz> {
            return mojiKvizovi.filter { kviz -> kviz.datumPocetka > Calendar.getInstance().time }
        }

        fun getNotTaken(): List<Kviz> {
            return mojiKvizovi.filter { kviz -> kviz.datumKraj < Calendar.getInstance().time && kviz.datumRada == null }
        }

        fun dodajKviz(naziv: String) {
            val k = ostaliKvizovi.find { kviz -> kviz.naziv == naziv }
            if (k != null) {
                mojiKvizovi.add(k)
            }
            ostaliKvizovi.remove(k)
        }
    }
}