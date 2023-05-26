package me.yarond.mytime.ui.schedule

import me.yarond.mytime.Repository
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.ui.UtilPresenter

class WeeklySchedulePresenter(private var view: WeeklyScheduleActivity) : Repository.WeeklyEventsListener {

    fun start() {
        val repository = Repository.getInstance()
        repository.setWeeklyEventsListener(this)
        repository.readWeeklyEvents()
    }

    fun getDayScheduleFragment(day: Day): DayScheduleFragment {
        return DayScheduleFragment(arrayListOf(), day.value)
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

    override fun onSundayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Sunday.ordinal, events)
    }

    override fun onMondayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Monday.ordinal, events)
    }

    override fun onTuesdayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Tuesday.ordinal, events)
    }

    override fun onWednesdayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Wednesday.ordinal, events)
    }

    override fun onThursdayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Thursday.ordinal, events)
    }

    override fun onFridayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Friday.ordinal, events)
    }

    override fun onSaturdayEventsUpdate(events: ArrayList<Event>) {
        view.updateEvents(Day.Saturday.ordinal, events)
    }

}