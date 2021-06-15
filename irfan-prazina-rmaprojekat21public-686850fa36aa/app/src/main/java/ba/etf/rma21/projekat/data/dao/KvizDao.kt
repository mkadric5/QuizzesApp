package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz

@Dao
interface KvizDao {

    @Insert
    suspend fun insertKviz(vararg kvizovi: Kviz)

    @Query("SELECT * FROM Kviz")
    suspend fun getAll(): List<Kviz>

    @Query("DELETE FROM Kviz")
    suspend fun deleteAll()

    @Query("UPDATE Kviz SET predan=(:predanTrue) WHERE id=(:idKviza)")
    suspend fun oznaciKaoUradjen(idKviza: Int, predanTrue: Boolean)

    @Query("SELECT * FROM Kviz WHERE id=(:idKviza)")
    suspend fun getKviz(idKviza: Int): Kviz
}