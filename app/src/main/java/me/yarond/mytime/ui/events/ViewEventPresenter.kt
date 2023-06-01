package me.yarond.mytime.ui.events

import me.yarond.mytime.persistency.Repository
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
    }

    fun onDeleteConfirm(day: String, id: String) {
        val repository = Repository.getInstance()
        repository.deleteEvent(day, id)
        view.finish()
    }

    fun editClicked() {
        view.showEditEventActivity()
    }
}