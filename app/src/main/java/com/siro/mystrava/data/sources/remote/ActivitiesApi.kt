package com.siro.mystrava.data.sources.remote

import androidx.annotation.Keep
import com.siro.mystrava.data.models.activites.ActivityItem
import com.siro.mystrava.data.models.detail.ActivityDetail
import retrofit2.http.*

@Keep
interface ActivitiesApi {

    @GET("athlete/activities")
    suspend fun getAthleteActivitiesAfter(
        @Query("after") after: Int,
        @Query("per_page") count: Int = 200,
        @Query("page") page: Int = 1
    ): List<ActivityItem>

    @GET("athlete/activities")
    suspend fun getAthleteActivitiesBeforeAndAfter(
        @Query("before") before: Int?,
        @Query("after") after: Int?,
        @Query("per_page") count: Int = 200,
        @Query("page") page: Int = 1
    ): List<ActivityItem>

    @GET("activities/{id}")
    suspend fun getActivityDetail(
        @Path("id") activityId: String
    ): ActivityDetail

}