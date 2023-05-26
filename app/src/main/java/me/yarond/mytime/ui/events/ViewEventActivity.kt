package me.yarond.mytime.ui.events

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import me.yarond.mytime.R
import me.yarond.mytime.Repository
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Event
import me.yarond.mytime.ui.activityTypes.DefaultActivity

class ViewEventActivity : DefaultActivity() {

    private lateinit var eventNameTextView: TextView
    private lateinit var eventDayTextView: TextView
    private lateinit var eventStartTextView: TextView
    private lateinit var eventEndTextView: TextView
    private lateinit var eventNotificationTextView: TextView
    private lateinit var eventLocationTextView: TextView
    private lateinit var eventNotesTextView: TextView
    private lateinit var eventOneTimeImageView: ImageView
    private lateinit var deleteImageView: ImageView
    private lateinit var backImageView: ImageView

    private lateinit var id: String
    private lateinit var presenter: ViewEventPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        presenter = ViewEventPresenter(this)
        setViews()
        setListeners()
        presenter.setEvent(intent.getSerializableExtra("event") as Event)
    }

    private fun setViews() {
        eventNameTextView = findViewById(R.id.textview_viewevent_name)
        eventDayTextView = findViewById(R.id.textview_viewevent_day)
        eventStartTextView = findViewById(R.id.textview_viewevent_start)
        eventEndTextView = findViewById(R.id.textview_viewevent_end)
        eventNotificationTextView = findViewById(R.id.textview_viewevent_notif)
        eventLocationTextView = findViewById(R.id.textview_viewevent_location)
        eventNotesTextView = findViewById(R.id.textview_viewevent_notes)
        eventOneTimeImageView = findViewById(R.id.imageview_viewevent_onetime)
        backImageView = findViewById(R.id.imageview_viewevent_back)
        deleteImageView = findViewById(R.id.imagebutton_viewevent_delete)
    }

    private fun setListeners() {
        backImageView.setOnClickListener { presenter.backClicked() }
        deleteImageView.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Event?")
                .setMessage("Are you sure you want to delete this event?")
                .setIcon(R.drawable.warning_icon)
                .setPositiveButton(R.string.yes) { dialog, whichButton ->
                    presenter.onDeleteConfirm(eventDayTextView.text.toString(), id)
                }
                .setNegativeButton(R.string.no) { dialog, whichButton -> }
                .show()
        }
    }

    fun setEventName(name: String) {
        eventNameTextView.text = name
    }

    fun setEventDay(day: String) {
        eventDayTextView.text = day
    }

    fun setEventStartTime(start: String) {
        eventStartTextView.text = start
    }

    fun setEventEndTime(end: String) {
        eventEndTextView.text = end
    }

    fun setEventNotification(notification: String) {
        eventNotificationTextView.text = notification
    }

    fun setEventLocation(location: String) {
        eventLocationTextView.text = location
    }

    fun setEventNotes(notes: String) {
        eventNotesTextView.text = notes
    }

    fun setEventToOneTime() {
        eventOneTimeImageView.setImageResource(R.drawable.check_icon)
    }

    fun setEventToRecurring() {
        eventOneTimeImageView.setImageResource(R.drawable.deny_icon)
    }

    fun setEventId(id: String) {
        this.id = id
    }

}