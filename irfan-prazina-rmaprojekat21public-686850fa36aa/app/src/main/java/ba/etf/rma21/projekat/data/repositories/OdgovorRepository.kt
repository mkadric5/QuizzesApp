package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.OdgovorRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
object OdgovorRepository {

    private lateinit var context: Context

    fun setContext(_context: Context){
        context=_context
    }


    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val odgovoriZaKviz = mutableListOf<Odgovor>()
            var sviPokusaji = TakeKvizRepository.getPocetiKvizovi()
            if (sviPokusaji == null) {
                return@withContext odgovoriZaKviz
            }
            else {
                sviPokusaji.forEach { kt ->
                    if (kt.KvizId == idKviza) {
                        val odgovoriZaKvizTaken =
                            ApiAdapter.retrofit.dajOdgovoreZaPokusaj(
                                AccountRepository.acHash,
                                kt.id
                            ).body()
                        odgovoriZaKviz.addAll(odgovoriZaKvizTaken!!.toMutableList())
                    }
                }
                return@withContext odgovoriZaKviz
            }
        }
    }

    suspend fun getOdgovoriKvizDB(idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            return@withContext db.odgovorDao().getOdgovorZaKviz(idKviza)
        }
    }

    suspend fun dajOdgovoreZaPitanjeKviz(idPitanja: Int, idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            return@withContext getOdgovoriKviz(idKviza).filter { o -> o.pitanjeId == idPitanja }
        }
    }

    suspend fun dajOdgovoreZaPitanjeKvizDB(idPitanja: Int, idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            return@withContext getOdgovoriKvizDB(idKviza).filter { o -> o.pitanjeId == idPitanja }
        }
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int,idPitanje: Int,odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            try{
                val kvizTaken = ApiAdapter.retrofit.dajSvePokusaje().body()!!.find { kt -> kt.id == idKvizTaken }
                val pitanjaNaKvizu = PitanjeKvizRepository.getPitanja(kvizTaken!!.KvizId)
                val pitanje = pitanjaNaKvizu.find { p -> p.id == idPitanje }
                val bodoviPitanje = (((odgovor == pitanje!!.tacan).compareTo(false).
                toDouble()/pitanjaNaKvizu.size)*100).toInt()
                val osvojeniBodovi: Int = (kvizTaken.osvojeniBodovi + bodoviPitanje).toInt()
                val response = ApiAdapter.retrofit.postaviOdgovorZaKviz(
                    AccountRepository.acHash, idKvizTaken,
                    OdgovorRequestBody(odgovor,idPitanje,osvojeniBodovi)
                )
                return@withContext osvojeniBodovi
            }
            catch(e: Exception) {
                println("Greska pri pozivu servisa")
                return@withContext -1
            }
        }
    }

    suspend fun postaviOdgovorKvizDB(idKvizTaken: Int,idPitanje: Int,odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                val kvizTaken = db.kvizTakenDao().getKvizTaken(idKvizTaken)
                val pitanjaNaKvizu = PitanjeKvizRepository.getPitanjaDB(kvizTaken.KvizId)
                val pitanje = pitanjaNaKvizu.find { p -> p.id == idPitanje }
                val bodoviPitanje = (((odgovor == pitanje!!.tacan).compareTo(false).
                toDouble()/pitanjaNaKvizu.size)*100).toInt()
                val osvojeniBodovi: Int = (kvizTaken.osvojeniBodovi + bodoviPitanje).toInt()

                val dosadasnjiOdg = db.odgovorDao().getAll().find{ o -> o.pitanjeId == idPitanje}
                if (dosadasnjiOdg != null)
                    return@withContext kvizTaken.osvojeniBodovi.toInt()
                else {
                    val rownum = db.odgovorDao().getRowNum()
                    db.odgovorDao().insertOdgovor(
                        Odgovor(rownum,odgovor,idKvizTaken,idPitanje,kvizTaken.KvizId)
                    )
                    db.kvizTakenDao().updateKTBodovi(idKvizTaken,osvojeniBodovi)
                    return@withContext osvojeniBodovi
                }
            }
            catch(e: Exception) {
                println("Greska pri pozivu servisa")
                return@withContext -1
            }
        }
    }

    suspend fun predajOdgovoreDB(idKviza: Int): Int {
        return withContext(Dispatchers.IO) {
            var osvojeniBodovi = 0
             getOdgovoriKvizDB(idKviza).forEach{ o ->
                osvojeniBodovi = postaviOdgovorKviz(o.kvizTakenId,o.pitanjeId,o.odgovoreno)
            }
            KvizRepository.oznaciKaoPredan(idKviza)
            return@withContext osvojeniBodovi
        }
    }
}