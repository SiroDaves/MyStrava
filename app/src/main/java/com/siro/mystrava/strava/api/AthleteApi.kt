package com.siro.mystrava.strava.api

import com.siro.mystrava.strava.model.profile.AthleteStats
import com.siro.mystrava.strava.model.profile.StravaAthlete
import retrofit2.http.GET
import retrofit2.http.Path

interface AthleteApi {
    @GET("athlete")
    suspend fun getAthlete(): StravaAthlete

    @GET("athletes/{id}/stats")
    suspend fun getAthleteStats(@Path("id") id: String): AthleteStats

}