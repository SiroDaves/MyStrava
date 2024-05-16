package com.siro.mystrava.data.models.activites

import okhttp3.MultipartBody

data class UploadRequest(
    val name: String,
    val data_type: String,
    val file: MultipartBody.Part
)

data class UploadResponse(
    val activity_id: Int,
    val error: String,
    val external_id: String,
    val id: Int,
    val id_str: String,
    val status: String
)