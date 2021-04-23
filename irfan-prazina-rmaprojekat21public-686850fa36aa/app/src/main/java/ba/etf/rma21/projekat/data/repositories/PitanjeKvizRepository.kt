package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import ba.etf.rma21.projekat.data.svaPitanja
import ba.etf.rma21.projekat.data.svaPitanjaZaKvizove

class PitanjeKvizRepository {
    companion object {
        private var svaPitanja: MutableList<PitanjeKviz> = svaPitanjaZaKvizove().toMutableList()
        private var svaOdgovorenaPitanja = mutableListOf<PitanjeKviz>()

        fun getPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
            val pitanjaKvizovi = svaPitanja.filter {
                pitanjeKviz -> pitanjeKviz.kviz == nazivKviza && pitanjeKviz.predmet == nazivPredmeta }

            val pitanja: MutableList<Pitanje> = mutableListOf()

            pitanjaKvizovi.forEach {
                pitanja.addAll(svaPitanja().filter { pitanje -> pitanje.naziv == it.naziv })
            }
            return pitanja
        }

//        fun oznaciKaoOdgovoreno(nazivPitanja: String) {
//            svaOdgovorenaPitanja.add(svaPitanja.find { pitanjeKviz -> pitanjeKviz.naziv == nazivPitanja }!!)
//            svaPitanja.find { pitanjeKviz -> pitanjeKviz.naziv == nazivPitanja }!!.postaviKaoOdgovoreno()
//        }

        fun getPitanjeKvizZaPitanje(pitanje: Pitanje): PitanjeKviz {
            return svaPitanja.find {pK -> pK.naziv == pitanje.naziv}!!
        }

        fun postaviOdgovor(pitanje: Pitanje, odg: Int) {
            val pitanjeK = svaPitanja.find { pK -> pK.naziv == pitanje.naziv }!!
            pitanjeK.odgovor = odg
        }
    }
}