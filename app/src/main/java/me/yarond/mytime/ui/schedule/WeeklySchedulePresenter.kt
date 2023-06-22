package me.yarond.mytime.ui.schedule

import me.yarond.mytime.models.Day
import me.yarond.mytime.models.Event
import me.yarond.mytime.persistency.Repository
import me.yarond.mytime.ui.UtilPresenter

class WeeklySchedulePresenter(private var view: WeeklyScheduleActivity) : Repository.WeeklyEventsListener {

    private var events: ArrayList<ArrayList<Event>> = arrayListOf(
        ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList()
    )
    private var hideNonImportant = false

    fun init() {
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

    fun updateImportant(important: Boolean) {
        hideNonImportant = important
        Day.values().forEach { day ->
            updateEventsOnScreen(day.ordinal, events[day.ordinal])
        }
    }

    fun updateEventsOnScreen(dayIndex: Int, events: ArrayList<Event>) {
        view.updateEvents(dayIndex, events.filter { event: Event ->
            if (hideNonImportant) event.important
            else true
        } as ArrayList<Event>)
    }

    fun createNewEvent() {
        view.startActivity(view.getEditEventActivityIntent())
    }

    override fun onSundayEventsUpdate(events: ArrayList<Event>) {
        this.events[0] = events
        updateEventsOnScreen(Day.Sunday.ordinal, events)
    }

    override fun onMondayEventsUpdate(events: ArrayList<Event>) {
        this.events[1] = events
        updateEventsOnScreen(Day.Monday.ordinal, events)
    }

    override fun onTuesdayEventsUpdate(events: ArrayList<Event>) {
        this.events[2] = events
        updateEventsOnScreen(Day.Tuesday.ordinal, events)
    }

    override fun onWednesdayEventsUpdate(events: ArrayList<Event>) {
        this.events[3] = events
        updateEventsOnScreen(Day.Wednesday.ordinal, events)
    }

    override fun onThursdayEventsUpdate(events: ArrayList<Event>) {
        this.events[4] = events
        updateEventsOnScreen(Day.Thursday.ordinal, events)
    }

    override fun onFridayEventsUpdate(events: ArrayList<Event>) {
        this.events[5] = events
        updateEventsOnScreen(Day.Friday.ordinal, events)
    }

    override fun onSaturdayEventsUpdate(events: ArrayList<Event>) {
        this.events[6] = events
        updateEventsOnScreen(Day.Saturday.ordinal, events)
    }

}