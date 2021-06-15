package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDao {

    @Insert
    suspend fun insertPredmet(vararg predmeti: Predmet)

    @Query("DELETE FROM Predmet")
    suspend fun deleteAll()
}