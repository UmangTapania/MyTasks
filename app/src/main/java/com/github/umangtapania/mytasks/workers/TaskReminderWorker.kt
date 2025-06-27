package com.github.umangtapania.mytasks.workers

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.umangtapania.mytasks.MainActivity
import com.github.umangtapania.mytasks.db.TaskDao
import com.github.umangtapania.mytasks.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class TaskReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val taskDao: TaskDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val highPriorityTasks = taskDao.getHighPriorityPendingTasks()
            val allUncompleted = taskDao.getAllUncompletedTasks()

            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val todayDate = formatter.parse(formatter.format(Date()))!!

            val missedDeadlines = allUncompleted.filter { task ->
                try {
                    val deadlineDate = formatter.parse(task.deadline)
                    deadlineDate != null && deadlineDate.before(todayDate)
                } catch (e: Exception) {
                    false
                }
            }

            if (highPriorityTasks.isNotEmpty() || missedDeadlines.isNotEmpty()) {
                val message = buildString {
                    if (highPriorityTasks.isNotEmpty()) {
                        append("You have ${highPriorityTasks.size} high priority task(s) pending.\n")
                    }
                    if (missedDeadlines.isNotEmpty()) {
                        append("You have missed deadline for ${missedDeadlines.size} task(s).")
                    }
                }

                showNotification(message)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun showNotification(message: String) {
        val channelId = Constants.TASK_REMINDER_CHANNEL
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                Constants.TASK_REMINDERS,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0, // Request code (can be unique for different notifications)
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_overlay) // Replace with your own icon
            .setContentTitle(Constants.TASK_REMINDER)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}