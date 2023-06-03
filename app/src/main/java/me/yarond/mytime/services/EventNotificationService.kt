package me.yarond.mytime.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import me.yarond.mytime.R
import me.yarond.mytime.Utils
import me.yarond.mytime.models.Event
import me.yarond.mytime.ui.events.ViewEventActivity
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EventNotificationService : Service() {
    private var running = false

    companion object {
        @Volatile
        private var instance: EventNotificationService? = null

        private var todayEvents: ArrayList<Event> = ArrayList()
        private var tomorrowEvents: ArrayList<Event> = ArrayList()

        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MyTime"
        const val CHANNEL_NAME = "Event Notifications"

        fun getInstance(): EventNotificationService {
            return instance ?: synchronized(this) {
                instance ?: EventNotificationService().also { instance = it }
            }
        }
    }

    fun setTodayEvents(events: ArrayList<Event>) {
        todayEvents = events
    }

    fun setTomorrowEvents(events: ArrayList<Event>) {
        tomorrowEvents = events
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (running) return START_STICKY
        else running = true

        Thread {
            while (true) {
                getEvents(todayEvents).forEach { event -> sendNotification(event) }
                getEvents(tomorrowEvents).forEach { event -> sendNotification(event) }

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun getEvents(events: ArrayList<Event>): ArrayList<Event> {
        val currentTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
        val eventsToNotify: ArrayList<Event> = ArrayList()
        events.forEach { event ->
            if (event.day == Utils.getCurrentDay()) {
                val eventTime = LocalTime.parse(event.startTime)

                val timeArray = event.notification!!.timeArray
                val differenceTime = Duration.between(currentTime, eventTime)

                if (differenceTime.toHours().toInt() == timeArray[0] && differenceTime.toMinutes().toInt() == timeArray[1]) {
                    if (!event.notified) {
                        eventsToNotify.add(event)
                        event.notified = true
                    }
                }
            }
        }
        return eventsToNotify
    }

    private fun sendNotification(event: Event) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, ViewEventActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("event", event)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.book_icon)
            .setContentTitle("Event is coming up!")
            .setContentText("Event ${event.name} is starting ${event.notification!!.message}!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}