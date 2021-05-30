package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeKvizRepository {

        suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.zapocniKviz(AccountRepository.acHash,idKviza)
                val responseBody = response.body()
                //Kada se tek kreira KvizTaken, KvizId je postavljen na 0
                if (responseBody != null)
                    responseBody.KvizId = idKviza
                return@withContext responseBody
            }
        }

    suspend fun dajPokusajZaKviz(idKviza: Int): KvizTaken? {
        return withContext(Dispatchers.IO) {
            val pocetiKvizovi = getPocetiKvizovi()
            if (pocetiKvizovi == null)
                return@withContext zapocniKviz(idKviza)
            else {
                val zapocetiPokusaj = pocetiKvizovi.find { kt -> kt.KvizId == idKviza }
                if (zapocetiPokusaj == null)
                    return@withContext zapocniKviz(idKviza)
                else return@withContext zapocetiPokusaj
            }
        }
    }

        suspend fun getPocetiKvizovi(): List<KvizTaken>? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.dajSvePokusaje()
                val responseBody = response.body()
//                if (responseBody!!.isEmpty()) return@withContext null
                return@withContext responseBody
            }
        }
}