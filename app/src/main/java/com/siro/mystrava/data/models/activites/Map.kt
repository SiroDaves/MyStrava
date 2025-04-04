package com.siro.mystrava.data.models.activites

import androidx.annotation.Keep

@Keep
data class Map(
    val id: String,
    val resource_state: Int,
    val summary_polyline: String
)