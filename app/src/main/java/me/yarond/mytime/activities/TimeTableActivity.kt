package me.yarond.mytime.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.adapters.Hour
import me.yarond.mytime.adapters.HourAdapter
import me.yarond.mytime.adapters.PendingEvent
import me.yarond.mytime.adapters.PendingEventAdapter
import me.yarond.mytime.decorators.MarginItemDecoration
import me.yarond.mytime.layouts.WeekLayoutManager

class TimeTableActivity : AppCompatActivity() {

    private lateinit var hoursLayoutManager: RecyclerView.LayoutManager
    private lateinit var hoursAdapter: RecyclerView.Adapter<HourAdapter.ViewHolder>
    private lateinit var hoursRecyclerView: RecyclerView
    private lateinit var hoursRecyclerView2: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        setViews()
        setAdapters()
    }

    private fun setViews() {
        hoursRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_timetable_hours)
        hoursRecyclerView2 = findViewById<RecyclerView>(R.id.recyclerview_timetable_hours2)
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
    }
}