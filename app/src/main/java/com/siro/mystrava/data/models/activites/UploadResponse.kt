package com.siro.mystrava.data.models.activites

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("activity_id") val activityId: Long?,
    val error: String?,
    @SerializedName("external_id") val externalId: String?,
    val id: Long?,
    @SerializedName("id_str") val idStr: String?,
    val status: String?
)