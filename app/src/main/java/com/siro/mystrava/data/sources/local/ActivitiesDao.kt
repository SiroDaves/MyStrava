package com.siro.mystrava.data.sources.local

import androidx.room.*
import com.siro.mystrava.data.models.activites.ActivityItem

@Dao
interface ActivitiesDao {
    @Query("SELECT * FROM activitiesItem ")
    fun getAll(): List<ActivityItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ActivityItem)

    @Query("SELECT * FROM activitiesItem LIMIT 2")
    fun getLast10Activities() : List<ActivityItem>

}