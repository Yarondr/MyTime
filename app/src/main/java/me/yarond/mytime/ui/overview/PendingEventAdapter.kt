package me.yarond.mytime.ui.overview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.model.PendingEvent
import me.yarond.mytime.ui.events.ViewEventActivity

class PendingEventAdapter(private var events: ArrayList<PendingEvent>) : RecyclerView.Adapter<PendingEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleritem_pending_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.titleTextView.text = event.title
        holder.timeTextView.text = event.time
        holder.numberTextView.text = event.number
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var timeTextView: TextView
        var numberTextView: TextView

        init {
            titleTextView = itemView.findViewById<TextView>(R.id.textview_pending_event_title)
            timeTextView = itemView.findViewById<TextView>(R.id.textview_pending_event_time)
            numberTextView = itemView.findViewById<TextView>(R.id.textview_pending_event_number)

            itemView.setOnClickListener {
                val intent = Intent(timeTextView.context, ViewEventActivity::class.java)
                timeTextView.context.startActivity(intent)
            }
        }

    }

}