package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
object TakeKvizRepository {

    private lateinit var context: Context

    fun setContext(_context: Context){
        context=_context
    }

        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                try{
                    val response = ApiAdapter.retrofit.zapocniKviz(AccountRepository.acHash,idKviza)
                    val responseBody = response.body()
                    if (responseBody!!.student == null)
                        return@withContext null
                    //Kada se tek kreira KvizTaken, KvizId je postavljen na 0
                    if (responseBody != null)
                        responseBody.KvizId = idKviza
                    val db = AppDatabase.getInstance(context)
                    //Ubacujemo kvizTaken u bazu
                    if (!db.kvizTakenDao().getAll().any { kt -> kt.KvizId == idKviza })
                        db.kvizTakenDao().insertKvizTaken(responseBody)
                    return@withContext responseBody
                }
                catch(e: Exception){
                    println("Greska pri pozivu servisa")
//                    Log.e("GRESKA",e.message.toString())
                    return@withContext null
                }
            }
        }

    suspend fun dajPokusajZaKviz(idKviza: Int): KvizTaken? {
        return withContext(Dispatchers.IO) {
            val pocetiKvizovi = getPocetiKvizovi()
            if (pocetiKvizovi.isEmpty())
                return@withContext zapocniKviz(idKviza)
            else {
                val zapocetiPokusaj = pocetiKvizovi.find { kt -> kt.KvizId == idKviza }
                if (zapocetiPokusaj == null)
                    return@withContext zapocniKviz(idKviza)
                else return@withContext zapocetiPokusaj
            }
        }
    }

        suspend fun getPocetiKvizoviApi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO) {
                try{
                    val response = ApiAdapter.retrofit.dajSvePokusaje()
                    val responseBody = response.body()
                    if (responseBody!!.isEmpty()) return@withContext null
                    return@withContext responseBody
                }
                catch(e: Exception) {
                    println("Greska pri pozivu servisa")
                    return@withContext null
                }
            }
        }


    suspend fun getPocetiKvizovi(): List<KvizTaken> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            DBRepository.updateNow()
            return@withContext db.kvizTakenDao().getAll()
        }
    }

//    suspend fun dajPokusajZaKvizDB(idKviza: Int): KvizTaken {
//        return withContext(Dispatchers.IO) {
//            val db = AppDatabase.getInstance(context)
//            return@withContext db.kvizTakenDao().getKvizTakenZaKviz(idKviza)
//        }
//    }
}