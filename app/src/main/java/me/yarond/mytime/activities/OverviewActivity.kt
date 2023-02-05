package me.yarond.mytime.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.adapters.PendingEvent
import me.yarond.mytime.adapters.PendingEventAdapter

class OverviewActivity : AppCompatActivity() {

    private lateinit var todayEventsLayoutManager: RecyclerView.LayoutManager
    private lateinit var todayEventsAdapter: RecyclerView.Adapter<PendingEventAdapter.ViewHolder>
    private lateinit var todayEventsRecyclerView: RecyclerView
    private lateinit var addImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        setViews()
        setAdapters()
        setListeners()
    }

    private fun setViews() {
        todayEventsRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_overview_today)
        addImageButton = findViewById<ImageButton>(R.id.imagebutton_overview_add)
    }

    private fun setAdapters() {
        todayEventsLayoutManager = LinearLayoutManager(this)
        todayEventsRecyclerView.layoutManager = todayEventsLayoutManager

        val event1 = PendingEvent("Event 1", "10:00", "1.")
        val event2 = PendingEvent("Event 2", "11:00", "2.")

        todayEventsAdapter = PendingEventAdapter(arrayListOf(event1, event2))
        todayEventsRecyclerView.adapter = todayEventsAdapter
    }

    private fun setListeners() {
        addImageButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }
}