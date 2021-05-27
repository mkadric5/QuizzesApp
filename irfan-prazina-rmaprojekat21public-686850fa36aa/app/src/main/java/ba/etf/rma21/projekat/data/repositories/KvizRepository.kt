package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Kviz
//import ba.etf.rma21.projekat.data.neupisaniKvizovi
//import ba.etf.rma21.projekat.data.upisaniKvizovi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object KvizRepository {
//        private var mojiKvizovi: MutableList<Kviz> = upisaniKvizovi().toMutableList()
//        private var ostaliKvizovi: MutableList<Kviz> = neupisaniKvizovi().toMutableList()
//        private var uradjeniKvizovi: MutableList<Kviz> = upisaniKvizovi().filter {
//            kviz -> kviz.datumRada != null && kviz.osvojeniBodovi != null }.toMutableList()
//
//        fun getMyKvizes(): List<Kviz> {
//            return mojiKvizovi
//        }
//
//        fun getOstaliKvizovi(): List<Kviz> {
//            return ostaliKvizovi
//        }
//
//        fun getAll(): List<Kviz> {
//            val sviKvizovi: MutableList<Kviz> = mojiKvizovi.toMutableList()
//            sviKvizovi.addAll(ostaliKvizovi)
//            return sviKvizovi
//        }
//
//        fun getDone(): List<Kviz> {
//            return uradjeniKvizovi
//        }
//
//        fun getFuture(): List<Kviz> {
//            return mojiKvizovi.filter { kviz -> kviz.datumPocetak > Calendar.getInstance().time }
//        }
//
//        fun getNotTaken(): List<Kviz> {
//            return mojiKvizovi.filter { kviz -> kviz.datumKraj < Calendar.getInstance().time && kviz.datumRada == null }
//        }
//
//        fun dodajKviz(grupaNaziv: String, predmetNaziv: String) {
//            val k = ostaliKvizovi.find { kviz -> kviz.nazivPredmeta == predmetNaziv &&
//                    kviz.nazivGrupe == grupaNaziv }
//            if (k != null) {
//                mojiKvizovi.add(k)
//            }
//            ostaliKvizovi.remove(k)
//        }
//
//        fun oznaciKaoUradjen(kviz: Kviz, bodovi: Float){
//            val k = mojiKvizovi.find { kv -> kv.naziv == kviz.naziv }
//            k!!.osvojeniBodovi = bodovi
//            k.datumRada = Calendar.getInstance().time
//            uradjeniKvizovi.add(k)
//        }
//
//        fun getKviz(nazivKviza: String): Kviz{
//            return getAll().find {
//                it.naziv == nazivKviza
//            }!!
//        }

    suspend fun getAll(): List<Kviz> {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSveKvizove()
            var responseBody: List<Kviz>? = response.body()
            if (responseBody == null) {
                responseBody = mutableListOf()
            } else responseBody.forEach { kviz -> popuniPredmeteZaKviz(kviz) }
            return@withContext responseBody!!
        }
    }

    suspend fun popuniPredmeteZaKviz(kviz: Kviz) {
        val grupeZaKviz = ApiAdapter.retrofit.dajGrupeZaKviz(kviz.id).body()
        kviz.predmeti = mutableListOf()
        grupeZaKviz!!.forEach { grupa ->
            val predmet = ApiAdapter.retrofit.dajPredmet(grupa.predmetId).body()!!
            if (!kviz.predmeti!!.any { p -> p.naziv == predmet.naziv })
                kviz.predmeti!!.add(predmet)
        }
    }

        suspend fun getById(id: Int): Kviz? {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.dajKviz(id)
                var responseBody: Kviz? = response.body()
                return@withContext responseBody
            }
        }

        suspend fun getUpisani(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val idStudenta = AccountRepository.getHash()
                val upisaniKvizovi = mutableListOf<Kviz>()
                val upisaneGrupe = ApiAdapter.retrofit.dajUpisaneGrupe(idStudenta).body()
                if (upisaneGrupe != null && upisaneGrupe.isNotEmpty()) {
                    for (grupa in upisaneGrupe) {
                        val kvizoviZaGrupu = ApiAdapter.retrofit.dajKvizoveZaGrupu(grupa.id).body()
                        if (kvizoviZaGrupu != null && kvizoviZaGrupu.isNotEmpty())
                            upisaniKvizovi.addAll(kvizoviZaGrupu)
                    }
                }
                upisaniKvizovi.forEach { kviz -> popuniPredmeteZaKviz(kviz) }
                return@withContext upisaniKvizovi
            }
        }
    }