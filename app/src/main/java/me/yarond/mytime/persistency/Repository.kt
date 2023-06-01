package me.yarond.mytime.persistency

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import me.yarond.mytime.Utils
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.model.Notifications

class Repository {

    private var email: String = Auth.getFirebaseAuth().currentUser?.email ?: ""

    interface OverviewEventsListener {
        fun onTodayEventsUpdate(events: ArrayList<Event>)
        fun onTomorrowEventsUpdate(events: ArrayList<Event>)
    }

    interface WeeklyEventsListener {
        fun onSundayEventsUpdate(events: ArrayList<Event>)
        fun onMondayEventsUpdate(events: ArrayList<Event>)
        fun onTuesdayEventsUpdate(events: ArrayList<Event>)
        fun onWednesdayEventsUpdate(events: ArrayList<Event>)
        fun onThursdayEventsUpdate(events: ArrayList<Event>)
        fun onFridayEventsUpdate(events: ArrayList<Event>)
        fun onSaturdayEventsUpdate(events: ArrayList<Event>)

    }

    private lateinit var overviewEventsListener: OverviewEventsListener
    private lateinit var weeklyEventsListener: WeeklyEventsListener

    fun setOverviewEventsListener(overviewEventsListener: OverviewEventsListener) {
        this.overviewEventsListener = overviewEventsListener
    }

    fun setWeeklyEventsListener(weeklyEventsListener: WeeklyEventsListener) {
        this.weeklyEventsListener = weeklyEventsListener
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

    fun setEmail(email: String) {
        this.email = email
    }

    fun readSpecificDayEvents(day: Day) {
        if (email == "") return

        database.collection("events").document(day.value).collection(email)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("MyTime Firebase", e)
                    return@addSnapshotListener
                }

                val events = ArrayList(snapshots!!.documents.map {
                    it.toObject(Event::class.java)!!
                })

                when (day) {
                    Utils.getCurrentDay() -> overviewEventsListener.onTodayEventsUpdate(events)
                    Utils.getTomorrowDay() -> overviewEventsListener.onTomorrowEventsUpdate(events)
                    else -> {}
                }
            }
    }

    fun readWeeklyEvents() {
        if (email == "") return

        Day.values().forEach { day ->
            database.collection("events").document(day.value).collection(email)
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w("MyTime Firebase", e)
                        return@addSnapshotListener
                    }

                    val events = ArrayList(snapshots!!.documents.map {
                        it.toObject(Event::class.java)!!
                    })

                    when (day) {
                        Day.Sunday -> weeklyEventsListener.onSundayEventsUpdate(events)
                        Day.Monday -> weeklyEventsListener.onMondayEventsUpdate(events)
                        Day.Tuesday -> weeklyEventsListener.onTuesdayEventsUpdate(events)
                        Day.Wednesday -> weeklyEventsListener.onWednesdayEventsUpdate(events)
                        Day.Thursday -> weeklyEventsListener.onThursdayEventsUpdate(events)
                        Day.Friday -> weeklyEventsListener.onFridayEventsUpdate(events)
                        Day.Saturday -> weeklyEventsListener.onSaturdayEventsUpdate(events)
                    }
                }
        }
    }

    fun write()
    {
        if (email == "") return

        Day.values().forEachIndexed { index, day ->
            val events = ArrayList<Event>()
            events.add(Event("Event $index", day, "12:00", "13:00", Notifications.HalfHourBefore, "Home", "Hello", true))
            events.add(Event("Event " + (index + 1), day, "13:00", "15:00", Notifications.ThreeHoursBefore, "School", "Hi", false))
            events.forEach {
                val id = it.generateId()
                database.collection("events").document(day.value)
                    .collection(email).document(id).set(it)
            }
        }
    }

    fun saveEvent(event: Event) {
        if (email == "") return

        val id = event.generateId()
        database.collection("events").document(event.day.value).
            collection(email).document(id).set(event)
            .addOnFailureListener { e ->
                Log.w("FIREBASE", "Error writing document", e)
            }
    }

    fun deleteEvent(day: String, id: String) {
        if (email == "") return
        database.collection("events").document(day).
            collection(email).document(id).delete()
    }
}