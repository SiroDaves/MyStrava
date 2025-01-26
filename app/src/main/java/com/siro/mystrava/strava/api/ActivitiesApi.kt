package com.siro.mystrava.strava.api

import androidx.annotation.Keep
import com.siro.mystrava.strava.model.activites.ActivitiesItem
import com.siro.mystrava.strava.model.activitydetail.StravaActivityDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Keep
interface ActivitiesApi {

    @GET("athlete/activities")
    suspend fun getAthleteActivitiesAfter(
        @Query("after") after: Int,
        @Query("per_page") count: Int = 200,
        @Query("page") page: Int = 1
    ): List<ActivitiesItem>

    @GET("athlete/activities")
    suspend fun getAthleteActivitiesBeforeAndAfter(
        @Query("before") before: Int?,
        @Query("after") after: Int?,
        @Query("per_page") count: Int = 200,
        @Query("page") page: Int = 1
    ): List<ActivitiesItem>

    @GET("activities/{id}")
    suspend fun getActivityDetail(
        @Path("id") activityId: String
    ): StravaActivityDetail

}