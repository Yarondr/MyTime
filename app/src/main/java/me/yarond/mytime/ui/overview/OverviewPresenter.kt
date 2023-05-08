package me.yarond.mytime.ui.overview

import me.yarond.mytime.Repository
import me.yarond.mytime.model.Event
import me.yarond.mytime.ui.UtilPresenter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OverviewPresenter(private var view: OverviewActivity) : Repository.EventsListener {

    init {
        val repository = Repository.getInstance()
        repository.setEventsListener(this)
        repository.readTodayEvents()
    }

    fun getTodayDate(): String {
        // in format: dd/mm/yy
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
    }

    fun sidebarButtonClicked() {
        UtilPresenter.sidebarButtonClicked(view)
    }

    fun onDrawerLayoutSlide(slideOffset: Float) {
        UtilPresenter.onDrawerLayoutSlide(view, slideOffset)
    }

    fun createNewEvent() {
        view.startActivity(view.getAddEventActivityIntent())
    }

    override fun onTodayEventsReceived(events: ArrayList<Event>) {
        view.updateTodayEvents(events)
    }
}