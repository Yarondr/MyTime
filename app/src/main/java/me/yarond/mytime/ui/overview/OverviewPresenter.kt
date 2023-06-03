package me.yarond.mytime.ui.overview

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import me.yarond.mytime.persistency.Repository
import me.yarond.mytime.Utils
import me.yarond.mytime.models.Event
import me.yarond.mytime.ui.UtilPresenter
import me.yarond.mytime.workers.NotificationWorker
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class OverviewPresenter(private var view: OverviewActivity) : Repository.OverviewEventsListener {

    init {
        val repository = Repository.getInstance()
        repository.setOverviewEventsListener(this)
        repository.readSpecificDayEvents(Utils.getCurrentDay())
        repository.readSpecificDayEvents(Utils.getTomorrowDay())
        UtilPresenter.askForNotificationPermission(view)
    }

    fun getTodayDate(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
    }

    fun getTomorrowDate(): String {
        return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yy"))
    }

    fun sidebarButtonClicked() {
        UtilPresenter.sidebarButtonClicked(view)
    }

    fun onDrawerLayoutSlide(slideOffset: Float) {
        UtilPresenter.onDrawerLayoutSlide(view, slideOffset)
    }

    fun createNewEvent() {
        view.startActivity(view.getEditEventActivityIntent())
    }

    override fun onTodayEventsUpdate(events: ArrayList<Event>) {
        val pendingEvents = ArrayList<Event>()
        val currentDate = LocalDateTime.now()

        events.forEach { event ->
            val eventEndHour = Utils.formatTime(event.endTime.split(":")[0])
            val eventEndMinute = Utils.formatTime(event.endTime.split(":")[1])
            val eventStartHour = Utils.formatTime(event.startTime.split(":")[0])
            val eventStartMinute = Utils.formatTime(event.startTime.split(":")[1])

            // add to pending events
            if (eventEndHour > currentDate.hour || eventEndHour == currentDate.hour && eventEndMinute >= currentDate.minute) {
                pendingEvents.add(event)
            }

            // schedule notification
            val eventTime = LocalDate.now().atTime(eventStartHour, eventStartMinute)
            val notificationTime = eventTime.minusHours(event.notification!!.timeArray[0].toLong())
                .minusMinutes(event.notification!!.timeArray[1].toLong())
            val currentTime = LocalDateTime.now()

            val interval = Duration.between(currentTime, notificationTime).seconds
            unScheduleNotificationWorker(event)
            scheduleNotificationWorker(interval, event)
        }

        view.updateTodayEvents(pendingEvents)
    }

    override fun onTomorrowEventsUpdate(events: ArrayList<Event>) {
        events.forEach { event ->
            val eventStartHour = Utils.formatTime(event.startTime.split(":")[0])
            val eventStartMinute = Utils.formatTime(event.startTime.split(":")[1])

            val eventTime = LocalDate.now().atTime(eventStartHour, eventStartMinute)
            val notificationTime = eventTime.minusHours(event.notification!!.timeArray[0].toLong())
                .minusMinutes(event.notification!!.timeArray[1].toLong()).plusDays(1)
            val currentTime = LocalDateTime.now()

            val interval = Duration.between(currentTime, notificationTime).seconds

            unScheduleNotificationWorker(event)
            scheduleNotificationWorker(interval, event)
        }

        view.updateTomorrowEvents(events)
    }

    private fun scheduleNotificationWorker(interval: Long, event: Event) {
        if (interval <= 0) return

        val data = Data.Builder()
            .putString("id", event.generateId())
            .putInt("dayIndex", event.day!!.ordinal)
            .putString("name", event.name)
            .putString("startTime", event.startTime)
            .putString("endTime", event.endTime)
            .putInt("notificationIndex", event.notification!!.ordinal)
            .putString("location", event.location)
            .putString("notes", event.notes)
            .putBoolean("once", event.once)

        OneTimeWorkRequestBuilder<NotificationWorker>()
            .addTag(event.generateId())
            .setInputData(data.build())
            .setInitialDelay(interval, TimeUnit.SECONDS)
            .build()
            .also {
                WorkManager.getInstance(view).enqueue(it)
            }
    }

    private fun unScheduleNotificationWorker(event: Event) {
        WorkManager.getInstance(view).cancelAllWorkByTag(event.generateId())
    }
}