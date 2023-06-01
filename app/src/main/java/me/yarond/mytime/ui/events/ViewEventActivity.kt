package me.yarond.mytime.ui.events

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import me.yarond.mytime.R
import me.yarond.mytime.models.Event
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
    private lateinit var editImageView: ImageView
    private lateinit var backImageView: ImageView

    private lateinit var id: String
    private lateinit var presenter: ViewEventPresenter

    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        presenter = ViewEventPresenter(this)
        setViews()
        setListeners()
        event = intent.getSerializableExtra("event") as Event
        presenter.setEvent(event)
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
        editImageView = findViewById(R.id.imagebutton_viewevent_edit)
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
        editImageView.setOnClickListener { presenter.editClicked() }
    }

    fun showEditEventActivity() {
        val intent = Intent(this, EditEventActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
        finish()
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