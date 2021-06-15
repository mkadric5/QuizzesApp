package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Kviz(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "predmeti") var predmeti: String = "",
    @ColumnInfo(name = "datumPocetka") @SerializedName("datumPocetak") val datumPocetak: String,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") val datumKraj: String?,
//    val datumRada: Date?,
    @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje: Int,
    @ColumnInfo(name = "predan") var predat: Boolean = false
//    val osvojeniBodovi: Double?
) {
}