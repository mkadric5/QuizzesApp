package ba.etf.rma21.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.dao.GrupaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StaticFieldLeak")
object AccountRepository {
        var acHash: String = "b7aa1b3d-7a8d-4411-9a47-0d23b4f0cb9c"
        private lateinit var context: Context

        fun setContext(_context:Context){
            context=_context
        }

        suspend fun postaviHash(acHash: String): Boolean {
            return withContext(Dispatchers.IO) {
                return@withContext try {
                    val db = AppDatabase.getInstance(context)
                    val accDao = db.accDao()
                    val accs = accDao.getAll()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")
                    val date = LocalDateTime.now().format(formatter)
                        db.grupaDao().deleteAll()
                        db.predmetDao().deleteAll()
                        db.kvizDao().deleteAll()
                        db.pitanjeDao().deleteAll()
                        db.odgovorDao().deleteAll()
                        accDao.updateHash(acHash)
                    true
                } catch(e: Exception) {
                    false
                }
            }
        }

        suspend fun getHash(): String {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                val accDao = db.accDao()
                val accs = accDao.getAll()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")
                val date = LocalDateTime.of(2000,4,4,4,4,4).format(formatter)
                if (accs.isEmpty()) {
                    accDao.insertAcc(Account(0,"email",acHash, date))
                }
                return@withContext accDao.getHash()
            }
        }

     suspend fun getLastUpdate(): String {
         return withContext(Dispatchers.IO) {
             val db = AppDatabase.getInstance(context)
             return@withContext db.accDao().getLastUpdate()
         }
    }


}