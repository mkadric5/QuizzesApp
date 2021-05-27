package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.lang.IllegalArgumentException
import java.util.*

data class Kviz(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    var predmeti: MutableList<Predmet>? = mutableListOf(),
    @SerializedName("datumPocetak") val datumPocetak: Date,
    @SerializedName("datumKraj") val datumKraj: Date?,
//    val datumRada: Date?,
    @SerializedName("trajanje") val trajanje: Int
//    val grupa: Grupa,
//    val osvojeniBodovi: Double?
) {
}