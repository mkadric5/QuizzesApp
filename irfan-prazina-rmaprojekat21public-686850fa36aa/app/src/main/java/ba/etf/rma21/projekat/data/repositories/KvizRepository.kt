package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
object KvizRepository {

    private lateinit var context: Context

    fun setContext(_context: Context){
        context=_context
    }

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
        val kvizPredmeti = mutableListOf<String>()
        grupeZaKviz!!.forEach { grupa ->
            val predmet = ApiAdapter.retrofit.dajPredmet(grupa.predmetId).body()!!
            if (!kvizPredmeti.contains(predmet.naziv))
                kvizPredmeti.add(predmet.naziv)
        }
        kviz.predmeti=""
        for (i in 0 until kvizPredmeti.size) {
            kviz.predmeti+= kvizPredmeti[i]
            if (i != kvizPredmeti.size-1)
                kviz.predmeti+= ","
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

        suspend fun getByIdDB(idKviza: Int): Kviz {
            val db = AppDatabase.getInstance(context)
            return db.kvizDao().getKviz(idKviza)
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

                return@withContext upisaniKvizovi.distinctBy { k -> k.id }
            }
        }

        suspend fun getUpisaniDB(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                return@withContext db.kvizDao().getAll()
            }
        }

        suspend fun oznaciKaoPredan(idKviza: Int) {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                db.kvizDao().oznaciKaoUradjen(idKviza,true)
            }
        }
    }