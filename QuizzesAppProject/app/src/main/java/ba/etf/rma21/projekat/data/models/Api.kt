package ba.etf.rma21.projekat.data.models

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import retrofit2.Response
import retrofit2.http.*

interface Api {

    //Vraca sve predmete
    @GET("/predmet")
    suspend fun dajSvePredmete(): Response<List<Predmet>>

    //Vraca predmet s datim id
    @GET("/predmet/{id}")
    suspend fun dajPredmet(
        @Path("id") idPredmeta: Int
    ): Response<Predmet>

    //Vraca sve grupe
    @GET("/grupa")
    suspend fun dajSveGrupe(): Response<List<Grupa>>

    //Vraca grupu s datim id
    @GET("/grupa/{id}")
    suspend fun dajGrupu(
        @Path("id") idGrupe: Int
    ): Response<Grupa>

    //Vraca grupe za predmet
    @GET("/predmet/{id}/grupa")
    suspend fun dajGrupeZaPredmet(
        @Path("id") idPredmeta: Int
    ): Response<List<Grupa>>

    //Dodaje studenta s hashom id u grupu s datim gid
    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiuGrupu(
        @Path("gid") idGrupe: Int,
        @Path("id") idStudenta: String
    ): Response<Poruka>

    //Vraca grupe u koje je student upisan
    @GET("/student/{id}/grupa")
    suspend fun dajUpisaneGrupe(
        @Path("id") idStudenta: String
    ): Response<List<Grupa>>

    //Vraca sve kvizove
    @GET("/kviz")
    suspend fun dajSveKvizove(): Response<List<Kviz>>

    //Vraca kviz s datim id
    @GET("/kviz/{id}")
    suspend fun dajKviz(
        @Path("id") idKviza: Int
    ): Response<Kviz>

    //Vraca kvizove dodijeljene grupi
    @GET("/grupa/{id}/kvizovi")
    suspend fun dajKvizoveZaGrupu(
        @Path("id") idGrupe: Int
    ): Response<List<Kviz>>

    //Vraca grupe u kojima je kviz dostupan
    @GET("/kviz/{id}/grupa")
    suspend fun dajGrupeZaKviz(
        @Path("id") idKviza: Int
    ): Response<List<Grupa>>

    //Vraca pitanja na kvizu sa zadanim id-em
    @GET("/kviz/{id}/pitanja")
    suspend fun dajPitanjaZaKviz(
        @Path("id") idKviza: Int
    ): Response<List<Pitanje>>

    //Zapocinje odgovaranje studenta sa id-em id na kvizu s id-em kid
    @POST("/student/{id}/kviz/{kid}")
    suspend fun zapocniKviz(
        @Path("id") idStudenta: String,
        @Path("kid") idKviza: Int
    ): Response<KvizTaken>

    //Vraca listu pokusaja za kvizove studenta sa zadanim id-em
    @GET("/student/{id}/kviztaken")
    suspend fun dajSvePokusaje(
        @Path("id") idStudenta: String = AccountRepository.acHash
    ): Response<List<KvizTaken>>

    //Vraca listu odgovora za pokusaj rjesavanja kviza sa id-em ktid i studenta s zadanim id-em
    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun dajOdgovoreZaPokusaj(
        @Path("id") idStudenta: String,
        @Path("ktid") idKvizTaken: Int
    ): Response<List<Odgovor>>

    //Dodaje odgovor za pokusaj rjesavanja kviza sa id-em ktid i za studenta s id-em id
    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun postaviOdgovorZaKviz(
        @Path("id") idStudenta: String,
        @Path("ktid") idKvizTaken: Int,
        @Body odgovorRequest: OdgovorRequestBody
    ): Response<Odgovor>

    //Da li je bilo promjene nad predmetima, grupama, kvizovima ili pitanjima
    @GET("/account/{id}/lastUpdate")
    suspend fun isChanged(
        @Path("id") idStudenta: String,
        @Query("date") datum: String
    ): Response<ResponseChange>

}