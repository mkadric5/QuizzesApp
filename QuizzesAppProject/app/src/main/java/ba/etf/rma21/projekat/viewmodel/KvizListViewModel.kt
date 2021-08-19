package ba.etf.rma21.projekat.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KvizListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)


    @RequiresApi(Build.VERSION_CODES.O)
    fun showKvizes(actionKvizes: ((kvizes: List<Kviz>) -> Unit),
                   filterNaziv: String) {
        val pKVM = PitanjeKvizViewModel()
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                scope.launch {
                    DBRepository.updateNow()
                    val kvizovi = KvizRepository.getUpisani()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanjaApi(k.id))
                    }
                    actionKvizes.invoke(kvizovi)
                }
            }
            "Svi kvizovi" -> {
                scope.launch {
                    val kvizovi = KvizRepository.getAllApi()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanjaApi(k.id))
                    }
                    actionKvizes.invoke(kvizovi)
                }
            }
            "Urađeni kvizovi" -> {
                scope.launch {
                    DBRepository.updateNow()
                    val kvizovi = KvizRepository.getUpisani()
                    kvizovi.forEach { k ->
                        k.predat = pKVM.isPredatKviz(k,PitanjeKvizRepository.getPitanjaApi(k.id))
                    }
                    actionKvizes.invoke(kvizovi.filter { k -> k.predat })
                }
            }
            "Budući kvizovi" -> {
                scope.launch {
                    DBRepository.updateNow()
                    val kvizovi = KvizRepository.getUpisani()
                    actionKvizes.invoke(kvizovi.filter { k -> LocalDate.parse(k.datumPocetak,formatter) > date })

                }
            }
            "Prošli kvizovi" -> {
                scope.launch {
                    DBRepository.updateNow()
                    val kvizovi = KvizRepository.getUpisani()
                    actionKvizes.invoke(kvizovi.filter { k -> k.datumKraj != null && date > LocalDate.parse(k.datumKraj,formatter) })
                }
            }
        }
    }
}