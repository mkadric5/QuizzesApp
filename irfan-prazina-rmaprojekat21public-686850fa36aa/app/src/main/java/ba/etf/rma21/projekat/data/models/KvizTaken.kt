package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class KvizTaken(
    @SerializedName("id") val id: Int,
    @SerializedName("student") val student: String,
    @SerializedName("osvojeniBodovi") var osvojeniBodovi: Double,
    @SerializedName("datumRada") val datumRada: Date,
    @SerializedName("KvizId") var KvizId: Int
){
}