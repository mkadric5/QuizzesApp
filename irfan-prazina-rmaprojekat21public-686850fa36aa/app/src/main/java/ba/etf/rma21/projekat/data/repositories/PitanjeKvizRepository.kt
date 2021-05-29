package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import ba.etf.rma21.projekat.data.svaPitanjaZaKvizove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeKvizRepository {
        private var svaPitanja: MutableList<PitanjeKviz> = svaPitanjaZaKvizove().toMutableList()
        private var svaOdgovorenaPitanja = mutableListOf<PitanjeKviz>()

//        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
//            val pitanjaKvizovi = svaPitanja.filter {
//                pitanjeKviz -> pitanjeKviz.kviz == nazivKviza && pitanjeKviz.predmet == nazivPredmeta }
//
//            val pitanja: MutableList<Pitanje> = mutableListOf()
//
//            pitanjaKvizovi.forEach {
//                pitanja.addAll(svaPitanja().filter { pitanje -> pitanje.naziv == it.naziv })
//            }
//            return pitanja
//        }
//
////        fun oznaciKaoOdgovoreno(nazivPitanja: String) {
////            svaOdgovorenaPitanja.add(svaPitanja.find { pitanjeKviz -> pitanjeKviz.naziv == nazivPitanja }!!)
////            svaPitanja.find { pitanjeKviz -> pitanjeKviz.naziv == nazivPitanja }!!.postaviKaoOdgovoreno()
////        }
//
//        fun getPitanjeKvizZaPitanje(pitanje: Pitanje): PitanjeKviz {
//            return svaPitanja.find {pK -> pK.naziv == pitanje.naziv}!!
//        }
//
//        fun postaviOdgovor(pitanje: Pitanje, odg: Int? = null) {
//            val pitanjeK = svaPitanja.find { pK -> pK.naziv == pitanje.naziv }!!
//            pitanjeK.odgovor = odg
//        }
//
//        fun oznaciNeodgovorena(kviz: Kviz) {
//            svaPitanja.filter { pK -> pK.kviz == kviz.naziv }.forEach {
//                if (it.odgovor == null)
//                    it.odgovor = -1
//                }
//            }

        suspend fun getPitanja(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.dajPitanjaZaKviz(idKviza)
                val pitanjaZaKviz = response.body()!!
                return@withContext pitanjaZaKviz
            }
        }
    }