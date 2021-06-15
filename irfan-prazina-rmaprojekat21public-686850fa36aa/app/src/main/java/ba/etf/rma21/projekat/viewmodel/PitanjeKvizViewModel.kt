package ba.etf.rma21.projekat.viewmodel

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


    fun otvoriPokusaj(actionKvizes: (kvizTaken: KvizTaken?, pitanja: List<Pitanje>,
                                     kviz: Kviz, predatKviz: Boolean) -> Unit,kviz: Kviz) {
        scope.launch {
            val pitanja = PitanjeKvizRepository.getPitanjaDB(kviz.id)
            val dosadasnjiOdgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)
            actionKvizes.invoke(
                TakeKvizRepository.dajPokusajZaKviz(kviz.id),
                pitanja,kviz,kviz.predat)
        }
    }

    suspend fun isPredatKviz(kviz: Kviz, pitanja: List<Pitanje>): Boolean {
        val dosadasnjiOdgovori = OdgovorRepository.getOdgovoriKviz(kviz.id)
        return dosadasnjiOdgovori.size == pitanja.size
    }

    fun postaviOdgovor(kvizTaken: KvizTaken,idPitanje: Int,odgovor: Int) {
        scope.launch {
            kvizTaken.osvojeniBodovi =
                OdgovorRepository.postaviOdgovorKvizDB(kvizTaken.id,idPitanje,odgovor).toDouble()
        }
    }

    fun popuniOdgovore(actionOdgovori: (odgovori: List<Odgovor>) -> Unit, idKviza: Int, idPitanja: Int) {
        scope.launch {
            val odgovori = OdgovorRepository.dajOdgovoreZaPitanjeKvizDB(idPitanja,idKviza)
//            if (odgovori.isNotEmpty())
//                println("odgovarano je")
//            else println("nije odgovarano")
            actionOdgovori.invoke(odgovori)
        }
    }

    fun otvoriPorukuZavrsenKviz(actionZavrsenKviz: (kviz: Kviz?) -> Unit,idKviza: Int) {
        scope.launch {
            actionZavrsenKviz.invoke(KvizRepository.getByIdDB(idKviza))
        }
    }

    fun zavrsiKvizOtvoriPoruku(actionZavrsenKviz: (kviz: Kviz?) -> Unit,kvizTaken: KvizTaken, pitanja: List<Pitanje>) {
        scope.launch {
            pitanja.forEach { p ->
                val odgovorZaPitanje = OdgovorRepository.dajOdgovoreZaPitanjeKvizDB(p.id,kvizTaken.KvizId)
                //Da li je odgovoreno na pitanje
                if (odgovorZaPitanje.isEmpty()) {
                    postaviOdgovor(kvizTaken,p.id,p.opcije.size)
                }
            }
            OdgovorRepository.predajOdgovoreDB(kvizTaken.KvizId)
            actionZavrsenKviz.invoke(KvizRepository.getByIdDB(kvizTaken.KvizId))
        }
    }

    fun vratiPrethodniFragment(
        actionFragPitanje: () -> Unit, actionFragKvizovi: () -> Unit,
        pitanja: List<Pitanje>, kvizTaken: KvizTaken) {
        scope.launch {
            pitanja.forEach { p ->
                val odgovorZaPitanje = OdgovorRepository.dajOdgovoreZaPitanjeKviz(p.id,kvizTaken.KvizId)
                //Da li je odgovoreno na pitanje
                if (odgovorZaPitanje.isNotEmpty()) {
                    actionFragPitanje.invoke()
                    return@launch
                }
            }
            actionFragKvizovi.invoke()
        }
    }
}