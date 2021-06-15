package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
object PitanjeKvizRepository {

    private lateinit var context: Context
    private var maxId: Int = 0

    fun setContext(_context: Context){
        context=_context
    }

        suspend fun getPitanjaApi(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                try{
                    val response = ApiAdapter.retrofit.dajPitanjaZaKviz(idKviza)
                    val pitanjaZaKviz = response.body()!!
                    pitanjaZaKviz.forEach { p ->
                        p.idBaza = maxId+1
                        maxId+= 1
                        p.kvizId = idKviza }
                    return@withContext pitanjaZaKviz
                }
                catch(e: Exception) {
                    println("Greska pri pozivu servisa")
                    return@withContext mutableListOf<Pitanje>()
                }
            }
        }

        suspend fun getPitanja(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                return@withContext db.pitanjeDao().getPitanjaZaKviz(idKviza)
            }
        }
    }