package me.yarond.mytime.ui.events

import me.yarond.mytime.Repository
import me.yarond.mytime.model.Event

class ViewEventPresenter(private var view: ViewEventActivity) {

    fun backClicked() {
        view.finish()
    }

    fun setEvent(event: Event) {
        view.setEventId(event.generateId())
        view.setEventName(event.name)
        view.setEventDay(event.day.value)
        view.setEventStartTime("Start Time: " + event.startTime)
        view.setEventEndTime("End Time: " + event.endTime)
        view.setEventNotification(event.notification.value)
        view.setEventLocation(event.location)
        view.setEventNotes(event.notes)
        if (event.once) {
            view.setEventToOneTime()
        } else {
            view.setEventToRecurring()
        }
    }

    fun onDeleteConfirm(day: String, id: String) {
        var repository = Repository.getInstance()
        repository.deleteEvent(day, id)
        view.finish()
    }
}