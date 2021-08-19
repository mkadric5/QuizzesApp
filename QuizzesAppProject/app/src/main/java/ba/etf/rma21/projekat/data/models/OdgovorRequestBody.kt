package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class OdgovorRequestBody(
    @SerializedName("odgovor") val odgovor: Int,
    @SerializedName("pitanje") val pitanjeId: Int,
    @SerializedName("bodovi") val bodovi: Int
)