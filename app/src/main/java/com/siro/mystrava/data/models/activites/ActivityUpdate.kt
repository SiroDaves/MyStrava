package com.siro.mystrava.data.models.activites

data class ActivityUpdate (
    val id: Long,
    val name: String,
    val description: String,
    val device_name: String,
    val type: String,
)