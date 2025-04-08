package com.siro.mystrava.data.models.activites

import androidx.annotation.Keep
import androidx.room.*
import com.siro.mystrava.data.models.detail.Map

@Keep
@Entity(indices = [Index(value = ["start_date"], unique = true)])
data class ActivityItem(
    @PrimaryKey(autoGenerate = true) val activityId: Int,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "distance") val distance: Float,
    @ColumnInfo(name = "calories") val calories: Double?,
    @ColumnInfo(name = "elapsed_time") val elapsed_time: Int,
    @ColumnInfo(name = "moving_time") val moving_time: Int,
    @ColumnInfo(name = "start_date") val start_date: String,
    @ColumnInfo(name = "start_date_local") val start_date_local: String,
    @ColumnInfo(name = "total_elevation_gain") val total_elevation_gain: Float,
    @ColumnInfo(name = "comment_count") val comment_count: Int,
    @ColumnInfo(name = "kudos_count") val kudos_count: Int,
    @ColumnInfo(name = "achievement_count") val achievement_count: Int,
    //@ColumnInfo(name = "map") val map: Map,
    @ColumnInfo(name = "flagged") val flagged: Boolean
)