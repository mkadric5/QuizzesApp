package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Odgovor(
    @SerializedName("id") val id: Int,
    @SerializedName("odgovoreno") val odgovoreno: Int,
    @SerializedName("KvizTakenId") val kvizTakenId: Int,
    @SerializedName("PitanjeId") val pitanjeId: Int
) {
}