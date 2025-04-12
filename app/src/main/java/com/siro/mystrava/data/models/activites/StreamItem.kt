package com.siro.mystrava.data.models.activites

class Stream : ArrayList<StreamItem>()

data class StreamItem(
    val `data`: List<Double>,
    val original_size: Int,
    val resolution: String,
    val series_type: String,
    val type: String
)