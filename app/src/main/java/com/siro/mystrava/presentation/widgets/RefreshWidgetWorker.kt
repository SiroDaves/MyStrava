package com.siro.mystrava.presentation.widgets

import android.content.Context
import androidx.work.*
import com.siro.mystrava.domain.repositories.HomeRepository
import javax.inject.*

class RefreshWidgetWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val homeRepo: HomeRepository
) : Worker(appContext, workerParams) {


    override fun doWork(): Result {
        //homeRepo.loadActivities()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}

class RefreshWidgetWorkerFactory(
    private val homeRepo: HomeRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshWidgetWorker::class.java.name ->
                RefreshWidgetWorker(appContext, workerParameters, homeRepo)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}


@Singleton
class WidgetWorkerFactory @Inject constructor(
    val homeRepo: HomeRepository
) : DelegatingWorkerFactory() {
    init {
        addFactory(RefreshWidgetWorkerFactory(homeRepo))
    }
}