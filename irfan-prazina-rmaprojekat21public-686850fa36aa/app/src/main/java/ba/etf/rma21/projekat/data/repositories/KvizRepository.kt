package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import java.util.*

object KvizRepository {

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

    private suspend fun popuniPredmeteZaKviz(kviz: Kviz) {
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
                try{
                    var response = ApiAdapter.retrofit.dajKviz(id)
                    var responseBody: Kviz? = response.body()
                    return@withContext responseBody
                }
                catch(e: IllegalStateException) {
                    return@withContext null
                }
            }
        }

        suspend fun getUpisani(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val idStudenta = AccountRepository.getHash()
                val upisaniKvizovi = mutableListOf<Kviz>()
                val upisaneGrupe = PredmetIGrupaRepository.getUpisaneGrupe()
                if (upisaneGrupe.isNotEmpty()) {
                    for (grupa in upisaneGrupe) {
                        var kvizoviZaGrupu: List<Kviz>?
                        kvizoviZaGrupu = try{
                            ApiAdapter.retrofit.dajKvizoveZaGrupu(grupa.id).body()
                        } catch(e: IllegalStateException) {
                            mutableListOf()
                        }
                        if (kvizoviZaGrupu != null && kvizoviZaGrupu.isNotEmpty())
                            upisaniKvizovi.addAll(kvizoviZaGrupu)
                    }
                }
                upisaniKvizovi.forEach { kviz -> popuniPredmeteZaKviz(kviz) }
                return@withContext upisaniKvizovi
            }
        }
    }