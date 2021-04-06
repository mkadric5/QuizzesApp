package ba.etf.rma21.projekat.data.models

import java.lang.IllegalArgumentException
import java.util.*

data class Kviz(
    val naziv: String, val nazivPredmeta: String, val datumPocetka: Date, val datumKraj: Date,
    val datumRada: Date?, val trajanje: Int, val nazivGrupe: String, val osvojeniBodovi: Float?
) {
    init {
        if (datumPocetka > datumKraj) throw IllegalArgumentException("Neispravni parametri")
        if (datumRada != null && osvojeniBodovi == null || datumRada == null && osvojeniBodovi != null)
            throw IllegalArgumentException("Neispravni parametri")
    }
}