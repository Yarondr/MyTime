package me.yarond.mytime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.adapters.PendingEvent
import me.yarond.mytime.adapters.PendingEventAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val DAY = "SUNDAY"

/**
 * A simple [Fragment] subclass.
 * Use the [DayScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayScheduleFragment(private var events: ArrayList<PendingEvent>, private var day: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var eventsLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsAdapter: RecyclerView.Adapter<PendingEventAdapter.ViewHolder>
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_dayschedule) as RecyclerView
        titleTextView = view.findViewById<TextView>(R.id.textview_dayschedule_daytitle) as TextView
        titleTextView.text = day
        setAdapters(view, events)
    }

    private fun setAdapters(view: View, events: ArrayList<PendingEvent>) {
        eventsLayoutManager = LinearLayoutManager(view.context)
        eventsRecyclerView.layoutManager = eventsLayoutManager

        eventsAdapter = PendingEventAdapter(events)
        eventsRecyclerView.adapter = eventsAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_schedule, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param day the day of the week.
         * @return A new instance of fragment DayScheduleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(events: ArrayList<PendingEvent>, day: String) =
            DayScheduleFragment(events, day).apply {
                arguments = Bundle().apply {}
            }
    }
}