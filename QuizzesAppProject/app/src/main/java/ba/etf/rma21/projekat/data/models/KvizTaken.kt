package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class KvizTaken(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "student") @SerializedName("student") val student: String,
    @ColumnInfo(name = "osvojeniBodovi") @SerializedName("osvojeniBodovi") var osvojeniBodovi: Double,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") val datumRada: String,
    @ColumnInfo(name = "kvizId") @SerializedName("KvizId") var KvizId: Int
){
}