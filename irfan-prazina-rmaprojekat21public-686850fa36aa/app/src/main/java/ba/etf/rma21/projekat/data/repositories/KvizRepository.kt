package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.neupisaniKvizovi
import ba.etf.rma21.projekat.data.upisaniKvizovi
import java.util.*

class KvizRepository {

    companion object {
        private var mojiKvizovi: MutableList<Kviz> = upisaniKvizovi().toMutableList()
        private var ostaliKvizovi: MutableList<Kviz> = neupisaniKvizovi().toMutableList()
        private var uradjeniKvizovi: MutableList<Kviz> = upisaniKvizovi().filter {
            kviz -> kviz.datumRada != null && kviz.osvojeniBodovi != null }.toMutableList()

        fun getMyKvizes(): List<Kviz> {
            return mojiKvizovi
        }

        fun getOstaliKvizovi(): List<Kviz> {
            return ostaliKvizovi
        }

        fun getAll(): List<Kviz> {
            val sviKvizovi: MutableList<Kviz> = mojiKvizovi.toMutableList()
            sviKvizovi.addAll(ostaliKvizovi)
            return sviKvizovi
        }

        fun getDone(): List<Kviz> {
            return uradjeniKvizovi
        }

        fun getFuture(): List<Kviz> {
            return mojiKvizovi.filter { kviz -> kviz.datumPocetka > Calendar.getInstance().time }
        }

        fun getNotTaken(): List<Kviz> {
            return mojiKvizovi.filter { kviz -> kviz.datumKraj < Calendar.getInstance().time && kviz.datumRada == null }
        }

        fun dodajKviz(grupaNaziv: String, predmetNaziv: String) {
            val k = ostaliKvizovi.find { kviz -> kviz.nazivPredmeta == predmetNaziv &&
                    kviz.nazivGrupe == grupaNaziv }
            if (k != null) {
                mojiKvizovi.add(k)
            }
            ostaliKvizovi.remove(k)
        }

        fun oznaciKaoUradjen(kviz: Kviz, bodovi: Float){
            val k = mojiKvizovi.find { kv -> kv.naziv == kviz.naziv }
            k!!.osvojeniBodovi = bodovi
            k.datumRada = Calendar.getInstance().time
            uradjeniKvizovi.add(k)
        }

        fun getKviz(nazivKviza: String): Kviz{
            return getAll().find {
                it.naziv == nazivKviza
            }!!
        }
    }
}