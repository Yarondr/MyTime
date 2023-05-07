package me.yarond.mytime.ui.schedule

import me.yarond.mytime.model.Day
import me.yarond.mytime.model.PendingEvent
import me.yarond.mytime.ui.UtilPresenter

class WeeklySchedulePresenter(private var view: WeeklyScheduleActivity) {

    fun getDayScheduleFragment(day: Day): DayScheduleFragment {
        return DayScheduleFragment(arrayListOf(
            PendingEvent("Event 1", "10:00", "1"),
            PendingEvent("Event 2", "11:00", "2")
        ), view.getStringFromResourceId(day.value))
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
}