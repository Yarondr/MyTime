package me.yarond.mytime.activities

import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import me.yarond.mytime.R

class AddEventActivity : AppCompatActivity() {
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView
    private lateinit var backImageView: ImageView
    private lateinit var saveImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setViews()
        setListeners()
    }

    private fun setViews() {
        startTimeTextView = findViewById(R.id.textview_addevent_start)
        endTimeTextView = findViewById(R.id.textview_addevent_end)
        backImageView = findViewById(R.id.imageview_addevent_back)
        saveImageButton = findViewById(R.id.imageButton_addevent_save)
    }

    private fun setListeners() {
        val daySelector = findViewById<TextView>(R.id.textview_addevent_day)
        daySelector.setOnClickListener {
            showDayDialog()
        }

        val notificationSelector = findViewById<TextView>(R.id.textview_addevent_notif)
        notificationSelector.setOnClickListener {
            showNotificationDialog()
        }

        startTimeTextView.setOnClickListener {
            timePicker(HourPickerType.START)
        }

        endTimeTextView.setOnClickListener {
            timePicker(HourPickerType.END)
        }

        backImageView.setOnClickListener {
            finish()
        }

        saveImageButton.setOnClickListener {
            finish()
        }

    }

    private fun timePicker(hourPickerType: HourPickerType) {
        val textView = when (hourPickerType) {
            HourPickerType.START -> startTimeTextView
            HourPickerType.END -> endTimeTextView
        }

        TimePickerDialog(this, { _, hourOfDay, minute ->
            textView.text = String.format(hourPickerType.text + ": %02d:%02d", hourOfDay, minute)
        }, 12, 0, true).show()
    }

    private fun showDayDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_day_picker)

        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun showNotificationDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_notification_picker)

        val window = dialog.window
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        window?.setLayout(width, 1000)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.show()
    }

    private enum class HourPickerType(val text: String) {
        START("Start time"),
        END("End time")
    }
}