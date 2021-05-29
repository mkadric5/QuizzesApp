package ba.etf.rma21.projekat.viewmodel

import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeKvizViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

//    fun dajPitanjaZaKviz(kviz: Kviz): List<Pitanje> {
//        return PitanjeKvizRepository.getPitanja(kviz.naziv,kviz.nazivPredmeta)
//    }
//
//    fun dajPitanjeKvizZaPitanje(pitanje: Pitanje): PitanjeKviz {
//        return PitanjeKvizRepository.getPitanjeKvizZaPitanje(pitanje)
//    }
//
//    fun postaviOdgovor(pitanje: Pitanje, odgovor: Int) {
//        PitanjeKvizRepository.postaviOdgovor(pitanje,odgovor)
//    }
//
//    fun odgovoreno(pitanje: Pitanje): Boolean {
//        val pitanjeKviz = dajPitanjeKvizZaPitanje(pitanje)
//        return pitanjeKviz.odgovor != null
//    }
//
//    fun tacnoOdgovoreno(pitanje: Pitanje): Boolean {
//        val pitanjeKviz = dajPitanjeKvizZaPitanje(pitanje)
//        return pitanjeKviz.odgovor == pitanje.tacan
//    }

    fun otvoriPokusaj(actionKvizes: (kvizTaken: KvizTaken?, pitanja: List<Pitanje>,
                                     kviz: Kviz, predatKviz: Boolean) -> Unit,kviz: Kviz) {
        scope.launch {
            val pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
            val dosadasnjiOdgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)
            actionKvizes.invoke(
                TakeKvizRepository.dajPokusajZaKviz(kviz.id),
                pitanja,kviz,dosadasnjiOdgovori.size == pitanja.size)
        }
    }

    fun otvoriPitanje(actionPitanje: (pitanje: Pitanje,odgovori: List<Odgovor>) -> Unit,pitanje: Pitanje, idKviza: Int) {
        scope.launch {
            actionPitanje.invoke(pitanje,OdgovorRepository.getOdgovoriKviz(idKviza))
        }
    }

    fun postaviOdgovor(idKvizTaken: Int,idPitanje: Int,odgovor: Int, bodoviPitanje: Int) {
        scope.launch {
            OdgovorRepository.postaviOdgovorKviz(idKvizTaken,idPitanje,odgovor,bodoviPitanje)
        }
    }

    fun popuniOdgovore(actionOdgovori: (odgovori: List<Odgovor>) -> Unit, idKviza: Int, idPitanja: Int) {
        scope.launch {
            val odgovori = OdgovorRepository.dajOdgovoreZaPitanjeKviz(idPitanja,idKviza)
//            if (odgovori.isNotEmpty())
//                println("odgovarano je")
//            else println("nije odgovarano")
            actionOdgovori.invoke(odgovori)
        }
    }

    fun otvoriPorukuZavrsenKviz(actionZavrsenKviz: (kviz: Kviz?) -> Unit,idKviza: Int) {
        scope.launch {
            actionZavrsenKviz.invoke(KvizRepository.getById(idKviza))
        }
    }
}