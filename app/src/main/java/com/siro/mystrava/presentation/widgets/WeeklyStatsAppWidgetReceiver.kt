package com.siro.mystrava.presentation.widgets

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.siro.mystrava.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class WeeklyStatsAppWidgetReceiver() : GlanceAppWidgetReceiver() {
    private val coroutineScope = MainScope()

    private val weeklyStatsWidget = WeeklyStatsWidget()
    override val glanceAppWidget: GlanceAppWidget = weeklyStatsWidget

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val preferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )

        coroutineScope.launch {
            val weeklyStatsString = preferences.getString("weeklyDistance", "")
            val weeklyElevationString = preferences.getString("weeklyElevation", "")

            weeklyStatsWidget.weeklyMiles = weeklyStatsString
            weeklyStatsWidget.weeklyElevation = weeklyElevationString
            weeklyStatsWidget.glanceId?.let { glanceId ->
                coroutineScope.launch {
                    weeklyStatsWidget.update(context, glanceId)
                }

            }

            preferences.edit().putBoolean("widgetEnabled", true).apply()
            Log.d("TAG", "onReceive: widgetEnabled")
        }

//        scheduleRefreshWidget(context = context)
    }

    companion object {
        suspend fun updateWidget(articleTitle: String, context: Context) {
            val glanceId = GlanceAppWidgetManager(context).getGlanceIds(WeeklyStatsWidget::class.java).last()
            updateAppWidgetState(context, glanceId) { prefs ->
//                prefs[WeeklyStatsWidget.miles] = articleTitle
            }
            WeeklyStatsWidget().updateAll(context)
        }
    }
}



private fun scheduleRefreshWidget(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val refreshWidgetWorker = PeriodicWorkRequestBuilder<RefreshWidgetWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraints).build()

    val operation = WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "refreshWidgetWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            refreshWidgetWorker
        )
        .result

    operation.addListener(
        { Log.d("TAG", "scheduleFetchEventData: WORK QUEUED") },
        { it.run() }
    )
}