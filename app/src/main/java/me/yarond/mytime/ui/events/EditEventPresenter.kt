package me.yarond.mytime.ui.events

import me.yarond.mytime.Repository
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications

class EditEventPresenter(private var view: EditEventActivity) {

    private var name: String = ""
    private var day: Day? = null
    private var startTime: String = ""
    private var endTime: String = ""
    private var notification: Notifications? = null
    private var location: String = ""
    private var notes: String = ""
    private var once: Boolean = false


    fun setEvent(event: Event) {
        view.setEventName(event.name)
        view.setEventDay(event.day.value)
        view.setEventStartTime("Start Time: " + event.startTime)
        view.setEventEndTime("End Time: " + event.endTime)
        view.setEventNotification(event.notification.value)

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

        name = event.name
        day = event.day
        startTime = event.startTime
        endTime = event.endTime
        notification = event.notification
        location = event.location
        notes = event.notes
        once = event.once
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
        if (!validateEvent()) return

        val event = createEvent()
        val repository = Repository.getInstance()
        repository.saveEvent(event)
        view.finish()
    }

    private fun validateEvent(): Boolean {
        if (name.isEmpty()) {
            view.setStatus("Name is empty!")
            return false
        }
        if (day == null) {
            view.setStatus("Day is not selected!")
            return false
        }
        if (startTime.isEmpty()) {
            view.setStatus("Start time is not selected!")
            return false
        }
        if (endTime.isEmpty()) {
            view.setStatus("End time is not selected!")
            return false
        }
        return true
    }

    private fun createEvent(): Event {
        return Event(name, day!!, startTime, endTime, notification!!, location, notes, once)
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