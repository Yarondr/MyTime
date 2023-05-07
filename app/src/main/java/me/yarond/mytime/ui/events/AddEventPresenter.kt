package me.yarond.mytime.ui.events

import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Notifications

class AddEventPresenter(private var view: AddEventActivity) {

    fun selectDay() {
        view.showDayDialog()
    }

    fun selectNotificationOption() {
        view.showNotificationDialog()
    }

    fun selectTime(hourPickerType: AddEventActivity.HourPickerType) {
        view.showTimePicker(hourPickerType)
    }

    fun backClicked() {
        view.finish()
    }

    fun saveEvent() {
        view.finish()
    }

    fun setSelectedDay(day: String) {
        view.updateDayText("Day: $day")
    }

    fun setSelectedNotification(notification: String) {
        view.updateNotificationText("Notification: $notification")
    }
}