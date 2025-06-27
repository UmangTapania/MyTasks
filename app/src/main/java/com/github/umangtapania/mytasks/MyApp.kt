package com.github.umangtapania.mytasks

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.umangtapania.mytasks.utils.Constants
import com.github.umangtapania.mytasks.utils.Prefs
import com.github.umangtapania.mytasks.workers.TaskReminderWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApp() : Application(), Configuration.Provider {

    init {
        instance = this
    }
    companion object {

        private var instance: MyApp? = null
        lateinit var prefs: Prefs

        fun applicationContext(): MyApp {
            return instance as MyApp
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext())

        scheduleTaskReminderWorker()
    }


    override val workManagerConfiguration: Configuration
        get() {
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }

    private fun scheduleTaskReminderWorker() {
        val request = PeriodicWorkRequestBuilder<TaskReminderWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.TASK_REMINDER_WORKER,
            ExistingPeriodicWorkPolicy.KEEP, // avoids rescheduling if already set
            request
        )
    }
}