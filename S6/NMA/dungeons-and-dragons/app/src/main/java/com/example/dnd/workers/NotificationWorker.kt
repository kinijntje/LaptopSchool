package com.example.dnd.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dnd.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationWorker(private val ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        // Create a notification channel if needed (required for Android 8.0 and above)
        createNotificationChannel()

        // Show the notification
        showNotification()

        // Indicate that the work is successful
        Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // Create a notification channel
        val channelId = "PeriodicNotificationChannel"
        val channelName = "Periodic Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val notificationChannel = NotificationChannel(channelId, channelName, importance)

        // Register the channel with the system
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun showNotification() {
        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "PeriodicNotificationChannel")
            .setContentTitle("Periodic Notification")
            .setContentText("This is a notification that repeats every 15 minutes.")
            .setSmallIcon(R.drawable.home_48px)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    ctx,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, notificationBuilder.build())
        }
    }
}
