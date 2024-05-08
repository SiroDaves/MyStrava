package com.siro.mystrava.data.models.activites

data class UploadResponse(
    val activity_id: Int,
    val error: String,
    val external_id: String,
    val id: Int,
    val id_str: String,
    val status: String
)