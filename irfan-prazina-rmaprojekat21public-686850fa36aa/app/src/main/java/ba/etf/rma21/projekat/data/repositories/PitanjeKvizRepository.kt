package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeKvizRepository {

        suspend fun getPitanja(idKviza: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                try{
                    val response = ApiAdapter.retrofit.dajPitanjaZaKviz(idKviza)
                    val pitanjaZaKviz = response.body()!!
                    return@withContext pitanjaZaKviz
                }
                catch(e: Exception) {
                    println("Greska pri pozivu servisa")
                    return@withContext mutableListOf<Pitanje>()
                }
            }
        }
    }