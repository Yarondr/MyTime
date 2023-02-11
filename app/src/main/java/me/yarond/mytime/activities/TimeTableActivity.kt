package me.yarond.mytime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.adapters.Event
import me.yarond.mytime.adapters.EventsAdapter
import me.yarond.mytime.adapters.Hour
import me.yarond.mytime.adapters.HourAdapter
import me.yarond.mytime.decorators.MarginItemDecoration

class TimeTableActivity : AppCompatActivity() {

    private lateinit var hoursLayoutManager: RecyclerView.LayoutManager
    private lateinit var hoursAdapter: RecyclerView.Adapter<HourAdapter.ViewHolder>
    private lateinit var hoursRecyclerView: RecyclerView

    private lateinit var sundayLayoutManager: RecyclerView.LayoutManager
    private lateinit var sundayAdapter: RecyclerView.Adapter<EventsAdapter.ViewHolder>
    private lateinit var sundayRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        setViews()
        setAdapters()
    }

    private fun setViews() {
        hoursRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_timetable_hours)
        sundayRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_timetable_sunday)
    }

    private fun setAdapters() {
        hoursLayoutManager = LinearLayoutManager(this)
        hoursRecyclerView.layoutManager = hoursLayoutManager

        val hours = ArrayList<Hour>()
        for (i in 0..23) {
            hours.add(Hour(i.toString()))
        }

        hoursAdapter = HourAdapter(hours)
        hoursRecyclerView.adapter = hoursAdapter
        hoursRecyclerView.addItemDecoration(MarginItemDecoration(0,30, 20, 0))


        sundayLayoutManager = LinearLayoutManager(this)
        sundayRecyclerView.layoutManager = sundayLayoutManager

        val events = ArrayList<Event>()
        for (i in 0..23) {
            events.add(Event("Event $i", "10:00", "10:30", R.color.secondary))
        }

        sundayAdapter = EventsAdapter(events)
        sundayRecyclerView.adapter = sundayAdapter
//        sundayRecyclerView.addItemDecoration(MarginItemDecoration(0,30, 20, 0))
    }
}