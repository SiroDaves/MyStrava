package com.siro.mystrava.data.sources.remote

import com.siro.mystrava.data.models.profile.*
import retrofit2.http.*

interface AthleteApi {
    @GET("athlete")
    suspend fun getAthlete(): StravaAthlete

    @GET("athletes/{id}/stats")
    suspend fun getAthleteStats(@Path("id") id: String): AthleteStats

}