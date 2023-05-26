package me.yarond.mytime

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications
import java.time.DayOfWeek
import java.time.LocalDate

class Repository {

    interface EventsListener {
        fun onTodayEventsUpdate(events: ArrayList<Event>)
        fun onTomorrowEventsUpdate(events: ArrayList<Event>)
        fun onWeeklyEventsUpdate(events: ArrayList<ArrayList<Event>>)
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

    fun readSpecificDayEvents(day: Day) {
        database.collection("events").document(day.value).collection("events")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("MyTime Firebase", e)
                    return@addSnapshotListener
                }

                val events = ArrayList(snapshots!!.documents.map {
                    it.toObject(Event::class.java)!!
                })

                when (day) {
                    Utils.getCurrentDay() -> eventsListener.onTodayEventsUpdate(events)
                    Utils.getTomorrowDay() -> eventsListener.onTomorrowEventsUpdate(events)
                    else -> {}
                }
            }
    }

    fun readWeeklyEvents() {
        Day.values().forEach { day ->
            database.collection("events")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w("MyTime Firebase", e)
                        return@addSnapshotListener
                    }

                    val weeklyEvents = ArrayList<ArrayList<Event>>()

                    for (document in snapshots!!.documents.sortedBy { day ->
                        Day.values().find { it.value == day.id}
                    }) {
                        (document.get("events") as List<DocumentSnapshot>).map {  }
                        weeklyEvents.add(
                            ArrayList((document.get("events") as List<DocumentSnapshot>).map {
                                it.toObject(Event::class.java)!!
                            })
                        )
                    }

                    eventsListener.onWeeklyEventsUpdate(weeklyEvents)
                }
        }
    }

    fun write() {
        Day.values().forEachIndexed { index, day ->
            val events = ArrayList<Event>()
            events.add(Event("Event $index", day, "12:00", "13:00", Notifications.HalfHourBefore, "Home", "Hello", true))
            events.add(Event("Event " + (index + 1), day, "13:00", "15:00", Notifications.ThreeHoursBefore, "School", "Hi", false))
            events.forEach {
                val id = it.generateId()
                database.collection("events").document(day.value).collection("events").document(id).set(it)
            }
        }
    }

    fun saveEvent(event: Event) {
        val id = event.generateId()
        database.collection("events").document(event.day.value).collection("events").document(id).set(event)
            .addOnSuccessListener {
                Log.d("FIREBASE", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("FIREBASE", "Error writing document", e)
            }
    }
}