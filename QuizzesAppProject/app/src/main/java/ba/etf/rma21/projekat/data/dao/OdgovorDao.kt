package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {

    @Insert
    suspend fun insertOdgovor(odgovor: Odgovor)

    @Query("SELECT COUNT (*) FROM Odgovor")
    suspend fun getRowNum(): Int

    @Query("SELECT * FROM Odgovor WHERE kvizId=(:kvizId)")
    suspend fun getOdgovorZaKviz(kvizId: Int): List<Odgovor>

    @Query("DELETE FROM Odgovor")
    suspend fun deleteAll()

    @Query("SELECT * FROM Odgovor")
    suspend fun getAll(): List<Odgovor>
}