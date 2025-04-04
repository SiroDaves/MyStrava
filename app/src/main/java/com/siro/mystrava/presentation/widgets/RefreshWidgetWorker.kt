package com.siro.mystrava.presentation.widgets

import android.content.Context
import androidx.work.*
import com.siro.mystrava.domain.repositories.DashboardRepository
import javax.inject.Inject
import javax.inject.Singleton

class RefreshWidgetWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val stravaDashboardRepository: DashboardRepository
) : Worker(appContext, workerParams) {


    override fun doWork(): Result {
        stravaDashboardRepository.loadActivities()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}

class RefreshWidgetWorkerFactory(
    private val stravaDashboardRepository: DashboardRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshWidgetWorker::class.java.name ->
                RefreshWidgetWorker(appContext, workerParameters, stravaDashboardRepository)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}


@Singleton
class WidgetWorkerFactory @Inject constructor(
    val stravaDashboardRepository: DashboardRepository
) : DelegatingWorkerFactory() {
    init {
        addFactory(RefreshWidgetWorkerFactory(stravaDashboardRepository))
    }
}