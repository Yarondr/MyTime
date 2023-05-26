package me.yarond.mytime.ui.events

import android.util.Log
import me.yarond.mytime.Repository
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications

class AddEventPresenter(private var view: AddEventActivity) {

    private lateinit var name: String
    private lateinit var day: Day
    private lateinit var startTime: String
    private lateinit var endTime: String
    private lateinit var notification: Notifications
    private lateinit var location: String
    private lateinit var notes: String
    private var once: Boolean = false


    fun selectDay() {
        view.showDayDialog()
    }

    fun selectNotificationOption() {
        view.showNotificationDialog()
    }

    fun selectTime(hourPickerType: HourPickerType) {
        view.showTimePicker(hourPickerType)
    }

    fun updateTime(hourPickerType: HourPickerType, time: String) {
        when (hourPickerType) {
            HourPickerType.START -> startTime = time
            HourPickerType.END -> endTime = time
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updateLocation(location: String) {
        this.location = location
    }

    fun updateNotes(notes: String) {
        this.notes = notes
    }

    fun updateOnce(once: Boolean) {
        this.once = once
    }

    fun backClicked() {
        view.finish()
    }

    fun saveEvent() {
        val event = createEvent()
        val repository = Repository.getInstance()
        repository.saveEvent(event)
        view.finish()
    }

    private fun createEvent(): Event {
        return Event(name, day, startTime, endTime, notification, location, notes, once)
    }

    fun setSelectedDay(day: String) {
        this.day = Day.values().find { it.value == day }!!
        view.updateDayText("Day: $day")
    }

    fun setSelectedNotification(notification: String) {
        this.notification = Notifications.values().find { it.value == notification }!!
        view.updateNotificationText("Notification: $notification")
    }
}