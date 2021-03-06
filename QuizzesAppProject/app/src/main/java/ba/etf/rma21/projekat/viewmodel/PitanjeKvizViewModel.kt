package ba.etf.rma21.projekat.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PitanjeKvizViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun otvoriPokusaj(actionKvizes: (kvizTaken: KvizTaken?, pitanja: List<Pitanje>,
                                     kviz: Kviz) -> Unit, kviz: Kviz) {
        scope.launch {
            DBRepository.updateNow()
            val pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
            actionKvizes.invoke(
                TakeKvizRepository.dajPokusajZaKviz(kviz.id),
                pitanja,KvizRepository.getById(kviz.id))
        }
    }

    suspend fun isPredatKviz(kviz: Kviz, pitanja: List<Pitanje>): Boolean {
        val dosadasnjiOdgovori = OdgovorRepository.getOdgovoriKvizApi(kviz.id)
        return dosadasnjiOdgovori.size == pitanja.size
    }

    fun postaviOdgovor(kvizTaken: KvizTaken,idPitanje: Int,odgovor: Int) {
        scope.launch {
            kvizTaken.osvojeniBodovi =
                OdgovorRepository.postaviOdgovorKviz(kvizTaken.id,idPitanje,odgovor).toDouble()
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

    fun zavrsiKvizOtvoriPoruku(actionZavrsenKviz: (kviz: Kviz?) -> Unit,kvizTaken: KvizTaken, pitanja: List<Pitanje>) {
        scope.launch {
            pitanja.forEach { p ->
                val odgovorZaPitanje = OdgovorRepository.dajOdgovoreZaPitanjeKviz(p.id,kvizTaken.KvizId)
                //Da li je odgovoreno na pitanje
                if (odgovorZaPitanje.isEmpty()) {
                    kvizTaken.osvojeniBodovi =
                        OdgovorRepository.postaviOdgovorKviz(kvizTaken.id,p.id,p.opcije.size).toDouble()
                }
            }
            OdgovorRepository.predajOdgovore(kvizTaken.KvizId)
            actionZavrsenKviz.invoke(KvizRepository.getById(kvizTaken.KvizId))
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