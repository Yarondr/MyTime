package me.yarond.mytime.ui.schedule

import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications
import me.yarond.mytime.ui.UtilPresenter

class WeeklySchedulePresenter(private var view: WeeklyScheduleActivity) {

    fun getDayScheduleFragment(day: Day): DayScheduleFragment {
        return DayScheduleFragment(arrayListOf(
            Event("Event 1",  Day.Sunday , "10:00", "11:00", Notifications.None, "Home", "Notes", false),
            Event("Event 1",  Day.Monday, "10:00", "11:00", Notifications.None, "Home", "Notes", false),
        ), day.value)
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