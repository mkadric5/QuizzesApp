package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.ApiAdapter
import ba.etf.rma21.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@SuppressLint("StaticFieldLeak")
object DBRepository {

    private lateinit var context: Context

    fun setContext(_context: Context){
        context =_context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateNow(): Boolean {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val hash = AccountRepository.getHash()
            val datum = AccountRepository.getLastUpdate()
            val change = ApiAdapter.retrofit.isChanged(hash,datum)
            if (change.body()!!.changed) {
//                Log.e("PORUKA","IMA UPDATE")
                //Datum i vrijeme azuriranja
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")
                val date = LocalDateTime.now().format(formatter).toString()
                //Azuriranje grupa
                val grupe = PredmetIGrupaRepository.getUpisaneGrupe()
                db.grupaDao().deleteAll()
                db.grupaDao().insertGrupa(*grupe.toTypedArray())

                //Azuriranje predmeta
                val predmeti = PredmetIGrupaRepository.getUpisaniPredmeti()
                db.predmetDao().deleteAll()
                db.predmetDao().insertPredmet(*predmeti.toTypedArray())

                //Azuriranje kvizova
                val kvizovi = KvizRepository.getUpisaniApi()
                val kvizoviSPokusajemId = db.kvizTakenDao().getAll().map{ kt -> kt.KvizId }.toMutableList()
                val kvizoviBezPokusajaId = kvizovi.map{ k -> k.id }.minus(kvizoviSPokusajemId).toMutableList()
                db.kvizDao().deleteAllExcept(kvizoviSPokusajemId)
                val nepocetiKvizovi = kvizovi.filter { k -> kvizoviBezPokusajaId.contains(k.id) }
                db.kvizDao().insertKviz(*nepocetiKvizovi.toTypedArray())

                //Azuriranje pitanja
                var pitanja = mutableListOf<Pitanje>()
                kvizoviSPokusajemId.forEach { k ->
                    val pitanjaZaKSP = db.pitanjeDao().getPitanjaZaKviz(k)
                    if (pitanjaZaKSP.isEmpty()) {
                        kvizoviSPokusajemId.remove(k)
                        kvizoviBezPokusajaId.add(k)
                    }
                }
                kvizoviBezPokusajaId.forEach { k ->
                    pitanja.addAll(PitanjeKvizRepository.getPitanjaApi(k)) }
                db.pitanjeDao().deleteAllExcept(kvizoviSPokusajemId)
                db.pitanjeDao().insertPitanje(*pitanja.toTypedArray())

                //Upisuje se vrijeme posljednjeg azuriranja
                db.accDao().setLastUpdate(date)
                return@withContext true
            }
//            Log.e("PORUKA","NEMA UPDATE")
            return@withContext false
        }
    }
}