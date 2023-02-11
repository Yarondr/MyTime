package me.yarond.mytime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.R

class HourAdapter(private var hours: ArrayList<Hour>) : RecyclerView.Adapter<HourAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleritem_hour, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourAdapter.ViewHolder, position: Int) {
        val hour = hours[position]
        holder.hourTextView.text = hour.hour
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var hourTextView: TextView

        init {
            hourTextView = itemView.findViewById<TextView>(R.id.textview_recyclerview_hour)
        }

    }
}