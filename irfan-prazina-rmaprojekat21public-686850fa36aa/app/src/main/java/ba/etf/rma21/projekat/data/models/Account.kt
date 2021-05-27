package ba.etf.rma21.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id") val id: Int,
    @SerializedName("student") val emailStudenta: String,
    @SerializedName("acHash") val acHash: String
) {
}