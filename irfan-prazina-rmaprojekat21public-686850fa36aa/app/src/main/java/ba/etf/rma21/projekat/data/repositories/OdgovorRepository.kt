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
    private var maxId: Int = 0

    fun setContext(_context: Context){
        context=_context
    }


    suspend fun getOdgovoriKvizApi(idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val odgovoriZaKviz = mutableListOf<Odgovor>()
            var sviPokusaji = TakeKvizRepository.getPocetiKvizoviApi()
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

    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            return@withContext db.odgovorDao().getOdgovorZaKviz(idKviza)
        }
    }

    suspend fun dajOdgovoreZaPitanjeKvizApi(idPitanja: Int, idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            return@withContext getOdgovoriKvizApi(idKviza).filter { o -> o.pitanjeId == idPitanja }
        }
    }

    suspend fun dajOdgovoreZaPitanjeKviz(idPitanja: Int, idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            return@withContext getOdgovoriKviz(idKviza).filter { o -> o.pitanjeId == idPitanja
                    && o.kvizId == idKviza }
        }
    }

    suspend fun postaviOdgovorKvizApi(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            try{
                val kvizTaken = ApiAdapter.retrofit.dajSvePokusaje().body()!!.find { kt -> kt.id == idKvizTaken }
                val pitanjaNaKvizu = PitanjeKvizRepository.getPitanjaApi(kvizTaken!!.KvizId)
                val pitanje = pitanjaNaKvizu.find { p -> p.id == idPitanje }
                var bodoviPitanje: Int

                if (odgovor == pitanje!!.opcije.size)
                    bodoviPitanje = 0
                else bodoviPitanje = (((odgovor == pitanje!!.tacan).compareTo(false).
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

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                val kvizTaken = db.kvizTakenDao().getKvizTaken(idKvizTaken)
                val pitanjaNaKvizu = PitanjeKvizRepository.getPitanja(kvizTaken.KvizId)
                val pitanje = pitanjaNaKvizu.find { p -> p.id == idPitanje }
                var bodoviPitanje: Int

                if (odgovor == pitanje!!.opcije.size)
                    bodoviPitanje = 0
                else bodoviPitanje = (((odgovor == pitanje!!.tacan).compareTo(false).
                toDouble()/pitanjaNaKvizu.size)*100).toInt()

                val osvojeniBodovi: Int = (kvizTaken.osvojeniBodovi + bodoviPitanje).toInt()

                val dosadasnjiOdg = db.odgovorDao().getAll().find{ o -> o.pitanjeId == idPitanje && o.kvizId == kvizTaken.KvizId}
                if (dosadasnjiOdg != null) {
                    return@withContext kvizTaken.osvojeniBodovi.toInt()
                }

                else {
//                    val rownum = db.odgovorDao().getRowNum()
                    db.odgovorDao().insertOdgovor(
                        Odgovor(maxId+1,odgovor,idKvizTaken,idPitanje,kvizTaken.KvizId)
                    )
                    maxId+= 1
                    db.kvizTakenDao().updateKTBodovi(idKvizTaken,osvojeniBodovi)
                    return@withContext osvojeniBodovi
                }
            }
            catch(e: Exception) {
//                Log.e("GRESKA",e.printStackTrace().toString())
                return@withContext -1
            }
        }
    }

    suspend fun predajOdgovore(idKviza: Int): Int {
        return withContext(Dispatchers.IO) {
            var osvojeniBodovi = 0
             getOdgovoriKviz(idKviza).forEach{ o ->
                osvojeniBodovi = postaviOdgovorKvizApi(o.kvizTakenId,o.pitanjeId,o.odgovoreno)
            }
            KvizRepository.oznaciKaoPredan(idKviza)
            return@withContext osvojeniBodovi
        }
    }
}