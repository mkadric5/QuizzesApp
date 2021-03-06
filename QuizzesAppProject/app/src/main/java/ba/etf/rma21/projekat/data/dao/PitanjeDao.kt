package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Converter
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {

    @Insert
    suspend fun insertPitanje(vararg pitanja: Pitanje)

    @Query("DELETE FROM Pitanje")
    suspend fun deleteAll()

    @Query("DELETE FROM Pitanje WHERE kvizId NOT IN (:pocetiKvizoviId)")
    suspend fun deleteAllExcept(pocetiKvizoviId: List<Int>)

    @Query("SELECT * FROM Pitanje WHERE kvizId=(:kvizId)")
    suspend fun getPitanjaZaKviz(kvizId: Int): List<Pitanje>

    @Query("SELECT * FROM Pitanje")
    suspend fun getAllPitanja(): List<Pitanje>
}