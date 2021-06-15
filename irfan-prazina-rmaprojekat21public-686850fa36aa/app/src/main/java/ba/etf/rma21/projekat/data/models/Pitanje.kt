package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Pitanje(
  @ColumnInfo(name = "id") @SerializedName("id") var id: Int,
  @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
  @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") val tekstPitanja: String,
  @ColumnInfo(name = "opcije") @SerializedName("opcije") val opcije: List<String>,
  @ColumnInfo(name = "tacan") @SerializedName("tacan") val tacan: Int,
  @ColumnInfo(name = "kvizId") var kvizId: Int,
  @PrimaryKey var idBaza: Int
) {
}