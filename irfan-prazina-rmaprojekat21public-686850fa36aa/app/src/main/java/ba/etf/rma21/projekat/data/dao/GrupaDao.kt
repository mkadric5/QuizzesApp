package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Insert
    suspend fun insertGrupa(vararg grupe: Grupa)

    @Query("DELETE FROM Grupa")
    suspend fun deleteAll()
}