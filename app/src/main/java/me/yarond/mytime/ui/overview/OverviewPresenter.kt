package me.yarond.mytime.ui.overview

import me.yarond.mytime.Repository
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
    }

    fun getTodayDate(): String {
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

    private fun formatTime(time: String): Int {
        if (time[0] == '0') {
            return time[1].digitToInt()
        }
        return time.toInt()
    }

    override fun onTodayEventsUpdate(events: ArrayList<Event>) {
        val pendingEvents = ArrayList<Event>()
        val currentDate = LocalDateTime.now()

        events.forEach { event ->
            val eventEndHour = formatTime(event.endTime.split(":")[0])
            val eventEndMinute = formatTime(event.endTime.split(":")[1])
            if (eventEndHour > currentDate.hour || eventEndHour == currentDate.hour && eventEndMinute >= currentDate.minute) {
                pendingEvents.add(event)
            }
        }
        view.updateTodayEvents(pendingEvents)
    }

    override fun onTomorrowEventsUpdate(events: ArrayList<Event>) { }
}