package com.siro.mystrava.data.sources.local

import androidx.room.*
import com.siro.mystrava.data.models.activites.ActivitiesItem

@Dao
interface ActivitiesDao {
    @Query("SELECT * FROM activitiesItem ")
    fun getAll(): List<ActivitiesItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: ActivitiesItem)

    @Query("SELECT * FROM activitiesItem LIMIT 2")
    fun getLast10Activities() : List<ActivitiesItem>

}