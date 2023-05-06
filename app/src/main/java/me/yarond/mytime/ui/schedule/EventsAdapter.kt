package me.yarond.mytime.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R
import me.yarond.mytime.model.Event

class EventsAdapter(private var events: ArrayList<Event>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleritem_event, parent, false)
//        view.layoutParams.height = height
//        view.layoutParams.width = width
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.nameTextView.text = event.name
        Toast.makeText(holder.itemView.context, event.name, Toast.LENGTH_SHORT).show()
        holder.backgroundView.setBackgroundColor(event.color)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView
        var backgroundView: View

        init {
            nameTextView = itemView.findViewById<TextView>(R.id.textview_event_name)
            backgroundView = itemView.findViewById<View>(R.id.view_event_background)
        }
    }
}