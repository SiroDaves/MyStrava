package com.siro.mystrava.data.sources.remote

import androidx.annotation.Keep
import com.siro.mystrava.data.models.activites.*
import com.siro.mystrava.data.models.detail.ActivityDetail
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@Keep
interface ActivitiesApi {
    @GET("athlete/activities")
    suspend fun getAthleteActivities(
        @Query("before") before: Int?,
        @Query("after") after: Int?,
        @Query("per_page") count: Int = 200,
        @Query("page") page: Int = 1
    ): List<ActivityItem>

    @GET("activities/{id}")
    suspend fun getActivityDetail(@Path("id") activityId: String): ActivityDetail

    @PUT("activities/{id}")
    suspend fun putActivityUpdate(
        @Path("id") activityId: String,
        @Body body: ActivityUpdate,
    )

    @GET("activities/{id}/streams")
    suspend fun getActivityStream(@Path("id") activityId: String): Stream

    @Multipart
    @POST("uploads")
    suspend fun uploadActivity(
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody? = null,
        @Part("description") description: RequestBody? = null,
        @Part("data_type") dataType: RequestBody,
    ): UploadResponse

}
