package ba.etf.rma21.projekat.data.models

object ApiConfig {
    var baseURL = "https://rma21-etf.herokuapp.com"

    fun postaviBaseURL(baseUrl: String) {
        baseURL = baseUrl
    }
}