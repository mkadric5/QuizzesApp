package ba.etf.rma21.projekat.data.models

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    //Vraca sve predmete
    @GET("predmet")
    suspend fun dajSvePredmete(): Response<List<Predmet>>

    //Vraca predmet s datim id
    @GET("predmet/{id}")
    suspend fun dajPredmet(
        @Path("id") idPredmeta: Int
    ): Response<Predmet>

    //Vraca sve grupe
    @GET("grupa")
    suspend fun dajSveGrupe(): Response<List<Grupa>>

    //Vraca grupe za predmet
    @GET("predmet/{id}/grupa")
    suspend fun dajGrupeZaPredmet(
        @Path("id") idPredmeta: Int
    ): Response<List<Grupa>>

    //Dodaje studenta s hashom id u grupu s datim gid
    @POST("grupa/{gid}/student/{id}")
    suspend fun upisiuGrupu(
        @Path("gid") idGrupe: Int,
        @Path("id") idStudenta: String
    ): Response<String>

    //Vraca grupe u koje je student upisan
    @GET("student/{id}/grupa")
    suspend fun dajUpisaneGrupe(
        @Path("id") idStudenta: String
    ): Response<List<Grupa>>

    //Vraca sve kvizove
    @GET("kviz")
    suspend fun dajSveKvizove(): Response<List<Kviz>>

    //Vraca kviz s datim id
    @GET("kviz/{id}")
    suspend fun dajKviz(
        @Path("id") idKviza: Int
    ): Response<Kviz>

    //Vraca kvizove dodijeljene grupi
    @GET("grupa/{id}/kvizovi")
    suspend fun dajKvizoveZaGrupu(
        @Path("id") idGrupe: Int
    ): Response<List<Kviz>>

    //Vraca grupe u kojima je kviz dostupan
    @GET("kviz/{id}/grupa")
    suspend fun dajGrupeZaKviz(
        @Path("id") idKviza: Int
    ): Response<List<Grupa>>
}