package me.yarond.mytime.ui.overview

import me.yarond.mytime.services.EventNotificationService
import me.yarond.mytime.persistency.Repository
import me.yarond.mytime.Utils
import me.yarond.mytime.model.Event
import me.yarond.mytime.ui.UtilPresenter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OverviewPresenter(private var view: OverviewActivity) : Repository.OverviewEventsListener {

    init {
        val repository = Repository.getInstance()
        repository.setOverviewEventsListener(this)
        repository.readSpecificDayEvents(Utils.getCurrentDay())
        repository.readSpecificDayEvents(Utils.getTomorrowDay())
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
        EventNotificationService.getInstance().setTodayEvents(events)

        val pendingEvents = ArrayList<Event>()
        val currentDate = LocalDateTime.now()

        events.forEach { event ->
            val eventEndHour = Utils.formatTime(event.endTime.split(":")[0])
            val eventEndMinute = Utils.formatTime(event.endTime.split(":")[1])
            if (eventEndHour > currentDate.hour || eventEndHour == currentDate.hour && eventEndMinute >= currentDate.minute) {
                pendingEvents.add(event)
            }
        }
        view.updateTodayEvents(pendingEvents)
    }

    override fun onTomorrowEventsUpdate(events: ArrayList<Event>) {
        EventNotificationService.getInstance().setTomorrowEvents(events)

        view.updateTomorrowEvents(events)
    }
}