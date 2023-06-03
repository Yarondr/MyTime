package me.yarond.mytime.ui.events

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.models.Event

class PendingEventAdapter(private var events: ArrayList<Event>) : RecyclerView.Adapter<PendingEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleritem_pending_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        val eventOrder = position + 1
        holder.titleTextView.text = event.name
        holder.timeTextView.text = event.startTime
        holder.numberTextView.text = String.format("%d.", eventOrder)
        holder.event = event
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var timeTextView: TextView
        var numberTextView: TextView
        var event: Event? = null

        init {
            titleTextView = itemView.findViewById(R.id.textview_pending_event_title)
            timeTextView = itemView.findViewById(R.id.textview_pending_event_time)
            numberTextView = itemView.findViewById(R.id.textview_pending_event_number)

            itemView.setOnClickListener {
                val intent = Intent(timeTextView.context, ViewEventActivity::class.java)
                intent.putExtra("event", event)
                timeTextView.context.startActivity(intent)
            }
        }

    }

}