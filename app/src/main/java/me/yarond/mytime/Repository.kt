package me.yarond.mytime

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
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

        private var database = FirebaseFirestore.getInstance()

        fun getInstance(): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository().also { instance = it }
            }
        }
    }

    fun readTodayEvents(day: Day) {
        database.collection("events").document(day.value).collection("events").get()
        .addOnSuccessListener { it ->
            val events = it.documents.map {
                it.toObject(Event::class.java)!!
            }
            eventsListener.onTodayEventsReceived(ArrayList(events))
        }.addOnFailureListener {
            Log.e("FIREBASE", "Error getting data", it)
        }
    }

    fun write() {
        val events = ArrayList<Event>()
        events.add(Event("Event 1", Day.Sunday, "12:00", "13:00", Notifications.HalfHourBefore, "Location 1", "Notes 1", true))
        events.add(Event("Event 2", Day.Monday, "13:00", "15:00", Notifications.ThreeHoursBefore, "Location 2", "Notes 2", false))
        events.forEach {
            val id = it.generateId()
            database.collection("events").document("Sunday").collection("events").document(id).set(it)
        }
    }
}