package com.siro.mystrava.data.models.activites

data class ActivityUpdate (
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val sport_type: String,
    val workout_type: String,
)