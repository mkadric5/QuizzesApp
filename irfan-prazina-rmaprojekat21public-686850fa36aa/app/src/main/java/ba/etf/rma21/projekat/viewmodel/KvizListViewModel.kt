package ba.etf.rma21.projekat.viewmodel

import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class KvizListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun showKvizes(actionKvizes: ((kvizes: List<Kviz>) -> Unit),
                   filterNaziv: String) {
        val pKVM = PitanjeKvizViewModel()
        val date = Calendar.getInstance().time
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getUpisani()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanja(k.id))
                    }
                    actionKvizes.invoke(kvizovi)
                }
            }
            "Svi kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getAll()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanja(k.id))
                    }
                    actionKvizes.invoke(kvizovi)
                }
            }
            "Urađeni kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getUpisani()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanja(k.id))
                    }
                    actionKvizes.invoke(kvizovi.filter { k -> k.predat })
                }
            }
            "Budući kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getUpisani()
                    actionKvizes.invoke(kvizovi.filter { k -> k.datumPocetka > date })
                }
            }
            "Prošli kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getUpisani()
                    actionKvizes.invoke(kvizovi.filter { k -> k.datumKraj != null && date > k.datumKraj })
                }
            }
        }
    }
}