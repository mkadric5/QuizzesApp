package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.KvizTaken

@Dao
interface KvizTakenDao {

    @Insert
    suspend fun insertKvizTaken(kt: KvizTaken)

    @Query("SELECT * FROM KvizTaken")
    suspend fun getAll(): List<KvizTaken>

    @Query("SELECT * FROM KvizTaken WHERE kvizId=(:idKviza)")
    suspend fun getKvizTakenZaKviz(idKviza: Int): KvizTaken

    @Query("SELECT * FROM KvizTaken WHERE id=(:ktid)")
    suspend fun getKvizTaken(ktid: Int): KvizTaken

    @Query("UPDATE KvizTaken SET osvojeniBodovi=(:bodovi) WHERE id=(:ktid)")
    suspend fun updateKTBodovi(ktid: Int,bodovi: Int)

    @Query("DELETE FROM KvizTaken")
    suspend fun deleteAll()
}