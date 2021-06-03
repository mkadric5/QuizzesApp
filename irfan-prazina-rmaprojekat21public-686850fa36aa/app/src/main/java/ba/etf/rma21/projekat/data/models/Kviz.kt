package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Kviz(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    var predmeti: MutableList<Predmet>? = mutableListOf(),
    @SerializedName("datumPocetak") val datumPocetka: Date,
    @SerializedName("datumKraj") val datumKraj: Date?,
//    val datumRada: Date?,
    @SerializedName("trajanje") val trajanje: Int,
    var predat: Boolean = false
//    val osvojeniBodovi: Double?
) {
}