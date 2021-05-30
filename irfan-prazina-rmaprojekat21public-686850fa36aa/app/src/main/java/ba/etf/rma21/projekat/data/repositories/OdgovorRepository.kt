package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.OdgovorRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OdgovorRepository {

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

    suspend fun dajOdgovoreZaPitanjeKviz(idPitanja: Int, idKviza: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            return@withContext getOdgovoriKviz(idKviza).filter { o -> o.pitanjeId == idPitanja }
        }
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int,idPitanje: Int,odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
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
    }
}