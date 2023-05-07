package me.yarond.mytime.ui.overview

import me.yarond.mytime.model.PendingEvent
import me.yarond.mytime.ui.UtilPresenter

class OverviewPresenter(private var view: OverviewActivity) {

    fun sidebarButtonClicked() {
        UtilPresenter.sidebarButtonClicked(view)
    }

    fun onDrawerLayoutSlide(slideOffset: Float) {
        UtilPresenter.onDrawerLayoutSlide(view, slideOffset)
    }

    fun getTodayEvents(): ArrayList<PendingEvent> {
        val event1 = PendingEvent("Event 1", "10:00", "1.")
        val event2 = PendingEvent("Event 2", "11:00", "2.")

        return arrayListOf(event1, event2)
    }

    fun createNewEvent() {
        view.startActivity(view.getAddEventActivityIntent())
    }
}