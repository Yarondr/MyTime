package me.yarond.mytime.ui.events

import me.yarond.mytime.persistency.Repository
import me.yarond.mytime.models.Day
import me.yarond.mytime.models.Event
import me.yarond.mytime.models.HourPickerType
import me.yarond.mytime.models.Notifications
import java.time.Duration
import java.time.LocalTime

class EditEventPresenter(private var view: EditEventActivity) {

    private var event: Event = Event()


    fun setEvent(event: Event) {
        view.setEventName(event.name)
        view.setEventDay("Day: " + event.day!!.value)
        view.setEventStartTime("Start Time: " + event.startTime)
        view.setEventEndTime("End Time: " + event.endTime)
        view.setEventNotification("Notification: " + event.notification!!.value)

        if (event.location.isEmpty()) {
            view.setEventLocation("None")
        } else {
            view.setEventLocation(event.location)
        }

        if (event.notes.isEmpty()) {
            view.setEventNotes("None")
        } else {
            view.setEventNotes(event.notes)
        }

        if (event.once) {
            view.setEventToOneTime()
        } else {
            view.setEventToRecurring()
        }

        this.event = event
    }
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
            HourPickerType.START -> event.startTime = time
            HourPickerType.END -> event.endTime = time
        }
    }

    fun updateName(name: String) {
        this.event.name = name
    }

    fun updateLocation(location: String) {
        this.event.location = location
    }

    fun updateNotes(notes: String) {
        this.event.notes = notes
    }

    fun updateOnce(once: Boolean) {
        this.event.once = once
    }

    fun backClicked() {
        view.finish()
    }

    fun saveEvent(oldEvent: Event?) {
        if (!validateEvent()) return

        val repository = Repository.getInstance()

        if (oldEvent != null)  repository.deleteEvent(oldEvent.day!!.value, oldEvent.generateId())
        repository.saveEvent(event)
        view.finish()
    }

    private fun validateEvent(): Boolean {
        if (event.name.isEmpty()) {
            view.setStatus("Name is empty!")
            return false
        }
        if (event.day == null) {
            view.setStatus("Day is not selected!")
            return false
        }
        if (event.startTime.isEmpty()) {
            view.setStatus("Start time is not selected!")
            return false
        }
        if (event.endTime.isEmpty()) {
            view.setStatus("End time is not selected!")
            return false
        }
        if (differenceBetweenTimes(event.startTime, event.endTime) <= 0) {
            view.setStatus("Start time must be before end time!")
            return false
        }
        return true
    }

    private fun differenceBetweenTimes(time1: String, time2: String): Int {
        return Duration.between(LocalTime.parse(time1), LocalTime.parse(time2)).seconds.toInt()
    }

    fun setSelectedDay(day: String) {
        this.event.day = Day.values().find { it.value == day }!!
        view.updateDayText("Day: $day")
    }

    fun setSelectedNotification(notification: String) {
        this.event.notification = Notifications.values().find { it.value == notification }!!
        view.updateNotificationText("Notification: $notification")
    }
}