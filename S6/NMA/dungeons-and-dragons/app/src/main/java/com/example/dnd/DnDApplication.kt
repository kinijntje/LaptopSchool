package com.example.dnd

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dnd.data.AppContainer
import com.example.dnd.data.AppDataContainer
import com.example.dnd.workers.NotificationWorker
import java.util.concurrent.TimeUnit

class DnDApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "PeriodicNotificationWork" ,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }
}