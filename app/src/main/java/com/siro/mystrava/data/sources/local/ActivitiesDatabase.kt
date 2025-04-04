package com.siro.mystrava.strava.model.activites.db

import android.content.Context
import androidx.room.*
import com.siro.mystrava.data.models.activites.ActivitiesItem
import com.siro.mystrava.data.sources.local.ActivitiesDao

@Database(entities = [ActivitiesItem::class], version = 3, exportSchema = false)
abstract class ActivitiesDatabase : RoomDatabase() {
    abstract fun activitiesDao(): ActivitiesDao

    companion object {
        @Volatile
        private var INSTANCE: ActivitiesDatabase? = null

        fun getDatabase(context: Context): ActivitiesDatabase? {
            if (INSTANCE == null) {
                synchronized(ActivitiesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ActivitiesDatabase::class.java, "activities_items"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE
        }
    }
}