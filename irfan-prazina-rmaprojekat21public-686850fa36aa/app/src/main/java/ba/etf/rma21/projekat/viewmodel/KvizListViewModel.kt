package ba.etf.rma21.projekat.viewmodel

import android.util.Log
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KvizListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

//    fun dajMojeKvizove(): List<Kviz> {
//        return KvizRepository.getMyKvizes().sortedWith(
//                Comparator { k1, k2 -> k1.datumPocetak.compareTo(k2.datumPocetak)})
//    }

    fun showKvizes(actionKvizes: ((kvizes: List<Kviz>) -> Unit),
                   filterNaziv: String) {
        when(filterNaziv) {
            "Svi moji kvizovi" -> {
                scope.launch {
                    actionKvizes.invoke(KvizRepository.getUpisani())
                }
            }
            "Svi kvizovi" -> {
                scope.launch {
                    actionKvizes.invoke(KvizRepository.getAll())
                }
            }
            "Urađeni kvizovi" -> {
                scope.launch {
                    actionKvizes.invoke(KvizRepository.getAll())
                }
            }
            "Budući kvizovi" -> {
                scope.launch {
                    actionKvizes.invoke(KvizRepository.getAll())
                }
            }
            "Prošli kvizovi" -> {
                scope.launch {
                    actionKvizes.invoke(KvizRepository.getAll())
                }
            }
        }
    }

//    fun dajMojeKvizove(): List<Kviz> {
//        var result = upisaniKvizovi()
//        scope.launch {
//            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
//            result = KvizRepository.getAll()
//            // Prikaže se rezultat korisniku na glavnoj niti
//        }
//        return result
//    }

//    fun dajSveKvizove(): List<Kviz> {
//        return KvizRepository.getAll().sortedWith(
//                Comparator { k1, k2 -> k1.datumPocetak.compareTo(k2.datumPocetak)})
//    }

    fun dajSveKvizove(): List<Kviz> {
        var result = listOf<Kviz>()
        scope.launch {
            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
            result = KvizRepository.getAll()
            // Prikaže se rezultat korisniku na glavnoj niti
        }
        return result
    }

//    fun dajMojeUradjeneKvizove(): List<Kviz> {
//        return KvizRepository.getDone().sortedWith(
//                Comparator { k1, k2 -> k1.datumPocetak.compareTo(k2.datumPocetak)})
//    }

    fun dajMojeUradjeneKvizove(): List<Kviz> {
        return listOf()
    }

//    fun dajMojeBuduceKvizove(): List<Kviz> {
//        return KvizRepository.getFuture().sortedWith(
//                Comparator { k1, k2 -> k1.datumPocetak.compareTo(k2.datumPocetak)})
//    }

    fun dajMojeBuduceKvizove(): List<Kviz> {
        return listOf()
    }

//    fun dajMojeNeuradjeneKvizove(): List<Kviz> {
//        return KvizRepository.getNotTaken().sortedWith(
//                Comparator { k1, k2 -> k1.datumPocetak.compareTo(k2.datumPocetak)})
//    }

    fun dajMojeNeuradjeneKvizove(): List<Kviz> {
        return listOf()
    }

//    fun oznaciKvizKaoUradjen(kviz: Kviz, bodovi: Float) {
//        PitanjeKvizRepository.oznaciNeodgovorena(kviz)
//        KvizRepository.oznaciKaoUradjen(kviz,bodovi)
//    }


//    fun dajKviz(nazivKviza: String): Kviz {
//        return KvizRepository.getKviz(nazivKviza)
//    }
}