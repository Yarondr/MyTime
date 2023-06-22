package me.yarond.mytime.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import me.yarond.mytime.R
import me.yarond.mytime.models.Day
import me.yarond.mytime.models.Event
import me.yarond.mytime.models.Notifications
import me.yarond.mytime.ui.events.ViewEventActivity

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val channelId = "MyTime"
    private val channelName = "Event Notifications"

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val event = buildEvent()

        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, ViewEventActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("event", event)
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.book_icon)
            .setContentTitle("Event is coming up!")
            .setContentText("Event ${event.name} is starting ${event.notification!!.message}!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(generateNotificationId(), builder.build())
    }

    private fun generateNotificationId(): Int {
        return (0..Int.MAX_VALUE).random()
    }

    private fun buildEvent(): Event {
        val event = Event(
            inputData.getString("name")!!,
            Day.values()[inputData.getInt("dayIndex", 0)],
            inputData.getString("startTime")!!,
            inputData.getString("endTime")!!,
            Notifications.values()[inputData.getInt("notificationIndex", 0)],
            inputData.getString("location")!!,
            inputData.getString("notes")!!,
            inputData.getBoolean("once", false),
            inputData.getBoolean("important", false)
        )
        event.id = inputData.getString("id")!!
        return event
    }

}