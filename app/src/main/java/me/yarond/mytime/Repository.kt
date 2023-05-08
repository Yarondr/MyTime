package me.yarond.mytime

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications

class Repository {

    interface EventsListener {
        fun onTodayEventsReceived(events: ArrayList<Event>)
    }

    private lateinit var eventsListener: EventsListener

    fun setEventsListener(eventsListener: EventsListener) {
        this.eventsListener = eventsListener
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        private val database = FirebaseDatabase.getInstance("https://mytime-31dce-default-rtdb.firebaseio.com/")

        fun getInstance(): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository().also { instance = it }
            }
        }
    }

    fun readTodayEvents() {
        val myRef = database.getReference("events/8\\5\\23")
        myRef.get().addOnSuccessListener {
            val events = (it.value as ArrayList<*>).map { event ->
                val eventMap = event as HashMap<*, *>
                Event(eventMap["name"] as String,
                    Day.valueOf(eventMap["day"] as String),
                    eventMap["startTime"] as String,
                    eventMap["endTime"] as String,
                    Notifications.valueOf(eventMap["notification"] as String),
                    eventMap["location"] as String,
                    eventMap["notes"] as String,
                    eventMap["once"] as Boolean)
            }
            eventsListener.onTodayEventsReceived(events as ArrayList<Event>)
        }.addOnFailureListener {
            Log.e("FIREBASE", "Error getting data", it)
        }
    }

    fun write() {
        val events = ArrayList<Event>()
        events.add(Event("Event 1", Day.Sunday, "12:00", "13:00", Notifications.HalfHourBefore, "Location 1", "Notes 1", true))
        events.add(Event("Event 2", Day.Monday, "12:00", "13:00", Notifications.ThreeHoursBefore, "Location 2", "Notes 2", false))

        val myRef = database.getReference("events/8\\5\\23")
        myRef.setValue(events)
    }
}