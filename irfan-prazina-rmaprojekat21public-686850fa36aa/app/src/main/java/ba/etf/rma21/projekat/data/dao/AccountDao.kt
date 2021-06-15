package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account
import retrofit2.http.DELETE

@Dao
interface AccountDao {

    @Insert
    suspend fun insertAcc(acc: Account)

    @Query("SELECT acHash FROM Account WHERE id=0")
    suspend fun getHash(): String

    @Query("SELECT * FROM Account")
    suspend fun getAll(): List<Account>

    @Query("UPDATE Account SET acHash=(:noviHash) WHERE id=0")
    suspend fun updateHash(noviHash: String)

    @Query("DELETE FROM Account")
    suspend fun delete()

    @Query("SELECT lastUpdate FROM Account WHERE id=0")
    suspend fun getLastUpdate(): String

    @Query("UPDATE Account SET lastUpdate=(:newUpdate) WHERE id=0")
    suspend fun setLastUpdate(newUpdate: String)
}