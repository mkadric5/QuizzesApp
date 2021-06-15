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

    fun setContext(_context: Context){
        context=_context
    }

        suspend fun getPitanja(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                //try{
                    val response = ApiAdapter.retrofit.dajPitanjaZaKviz(idKviza)
                    val pitanjaZaKviz = response.body()!!
                    pitanjaZaKviz.forEach { p -> p.kvizId = idKviza }
                    return@withContext pitanjaZaKviz.distinctBy { p -> p.id }
                //}
//                catch(e: Exception) {
//                    println("Greska pri pozivu servisa")
//                    return@withContext mutableListOf<Pitanje>()
//                }
            }
        }

        suspend fun getPitanjaDB(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                return@withContext db.pitanjeDao().getPitanjaZaKviz(idKviza)
            }
        }
    }