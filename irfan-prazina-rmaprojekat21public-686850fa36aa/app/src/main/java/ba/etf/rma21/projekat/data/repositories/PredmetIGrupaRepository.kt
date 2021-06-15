package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Poruka
import ba.etf.rma21.projekat.data.models.Predmet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
object PredmetIGrupaRepository {

    private lateinit var context: Context

    fun setContext(_context: Context){
        context=_context
        KvizRepository.setContext(context)
        TakeKvizRepository.setContext(context)
        PitanjeKvizRepository.setContext(context)
    }

    suspend fun getPredmeti(): List<Predmet> {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSvePredmete()
            var responseBody: List<Predmet>? = response.body()
            if (responseBody == null) {
                responseBody = mutableListOf()
            }
            return@withContext responseBody!!
        }
    }

    suspend fun getUpisaniPredmeti(): List<Predmet> {
        return withContext(Dispatchers.IO) {
            val upisaniPredmeti = mutableListOf<Predmet>()
            val upisaneGrupe = getUpisaneGrupe()
            val sviPredmeti = getPredmeti()
            sviPredmeti.forEach { p ->
                val grupeZaPredmet = getGrupeZaPredmet(p.id)
                if (grupeZaPredmet.any {g1 ->
                    upisaneGrupe.any { g2 -> g1.id == g2.id }
                    }) upisaniPredmeti.add(p)
            }
            return@withContext upisaniPredmeti
        }
    }

    suspend fun dajNeupisanePredmeteZaGodinu(godina: Int): List<Predmet> {
        return getNeupisaniPredmeti().filter { p -> p.godina == godina }
    }

    suspend fun getNeupisaniPredmeti(): List<Predmet> {
        return getPredmeti().minus(getUpisaniPredmeti())
    }

    suspend fun getGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.dajSveGrupe()
            var responseBody: List<Grupa>? = response.body()
            if (responseBody == null) {
                responseBody = mutableListOf()
            }
            return@withContext responseBody!!
        }
    }

    suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
        return withContext(Dispatchers.IO) {
            try{
                var response = ApiAdapter.retrofit.dajGrupeZaPredmet(idPredmeta)
                var responseBody: List<Grupa>? = response.body()
                if (responseBody == null) {
                    responseBody = mutableListOf()
                }
                return@withContext responseBody!!
            }
            catch(e: IllegalStateException) {
                println("Greska u pozivu ka servisu")
                return@withContext mutableListOf<Grupa>()
            }
        }
    }


    suspend fun upisiUGrupu(idGrupa: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val idStudenta = AccountRepository.getHash()
            val response = ApiAdapter.retrofit.upisiuGrupu(idGrupa,idStudenta)
            var responseBodyObject: Poruka = response.body()!!
            var responseBody = responseBodyObject.poruka
            if (responseBody == "Grupa not found." || responseBody == "Ne postoji account gdje je hash = $idStudenta")
                return@withContext false
            return@withContext true
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            try{
                val idStudenta = AccountRepository.getHash()
                var response = ApiAdapter.retrofit.dajUpisaneGrupe(idStudenta)
                var responseBody: List<Grupa>? = response.body()
                if (responseBody == null) {
                    responseBody = mutableListOf()
                }
                return@withContext responseBody!!
            }
            catch(e: IllegalStateException) {
                println("Greska u pozivu ka servisu")
                return@withContext mutableListOf<Grupa>()
            }
        }
    }
}