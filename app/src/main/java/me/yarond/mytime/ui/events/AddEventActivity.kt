package me.yarond.mytime.ui.events

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import me.yarond.mytime.R
import me.yarond.mytime.model.Day
import me.yarond.mytime.model.Notifications
import me.yarond.mytime.ui.activityTypes.DefaultActivity

class AddEventActivity : DefaultActivity() {
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView
    private lateinit var daySelectorTextView: TextView
    private lateinit var notificationSelectorTextView: TextView
    private lateinit var backImageView: ImageView
    private lateinit var saveImageButton: ImageButton
    private lateinit var dayDialog: Dialog
    private lateinit var notificationDialog: Dialog
    private lateinit var presenter: AddEventPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        presenter = AddEventPresenter(this)
        setViews()
        setListeners()
        createDialogs()
    }

    private fun setViews() {
        startTimeTextView = findViewById(R.id.textview_addevent_start)
        endTimeTextView = findViewById(R.id.textview_addevent_end)
        backImageView = findViewById(R.id.imageview_addevent_back)
        saveImageButton = findViewById(R.id.imageButton_addevent_save)
        daySelectorTextView = findViewById(R.id.textview_addevent_day)
        notificationSelectorTextView = findViewById(R.id.textview_addevent_notif)
    }

    private fun setListeners() {
        backImageView.setOnClickListener { presenter.backClicked() }
        saveImageButton.setOnClickListener { presenter.saveEvent() }
        daySelectorTextView.setOnClickListener { presenter.selectDay() }
        notificationSelectorTextView.setOnClickListener { presenter.selectNotificationOption() }
        startTimeTextView.setOnClickListener { presenter.selectTime(HourPickerType.START) }
        endTimeTextView.setOnClickListener { presenter.selectTime(HourPickerType.END) }

    }

    private fun createDialogs() {
        createDayDialog()
        createNotificationDialog()
    }

    private fun createDayDialog() {
        dayDialog = Dialog(this)
        dayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dayDialog.setCancelable(true)
        dayDialog.setContentView(R.layout.dialog_day_picker)
        dayDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val radioGroup: RadioGroup = dayDialog.findViewById(R.id.radiogroup_daypicker)
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val index = group.indexOfChild(radioButton)
            val day = resources.getString(Integer.parseInt(Day.values()[index].value))
            presenter.setSelectedDay(day)
            dayDialog.dismiss()
        })
    }

    private fun createNotificationDialog() {
        notificationDialog = Dialog(this)
        notificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        notificationDialog.setCancelable(true)
        notificationDialog.setContentView(R.layout.dialog_notification_picker)

        val window = notificationDialog.window
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        window?.setLayout(width, 1000)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val radioGroup: RadioGroup = notificationDialog.findViewById(R.id.radiogroup_notifpicker)
        Notifications.values().iterator().forEach { notification ->
            val radioButton = RadioButton(this)
            radioButton.text = notification.value
            radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
            radioButton.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            radioButton.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            radioButton.textSize = 18f
            radioButton.typeface = resources.getFont(R.font.quicksand_medium)
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.secondary))
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val index = group.indexOfChild(radioButton)
            val notification = Notifications.values()[index].value
            presenter.setSelectedNotification(notification)
            notificationDialog.dismiss()
        })

        radioGroup.check(radioGroup.getChildAt(0).id)
    }

    fun updateDayText(day: String) {
        daySelectorTextView.text = day
    }

    fun updateNotificationText(notification: String) {
        notificationSelectorTextView.text = notification
    }

    fun showTimePicker(hourPickerType: HourPickerType) {
        // choose the right textview to update
        val textView = when (hourPickerType) {
            HourPickerType.START -> startTimeTextView
            HourPickerType.END -> endTimeTextView
        }

        // show the time picker dialog and update the textview
        TimePickerDialog(this, { _, hourOfDay, minute ->
            textView.text = String.format(hourPickerType.text + ": %02d:%02d", hourOfDay, minute)
        }, 12, 0, true).show()
    }

    fun showDayDialog() {
        dayDialog.show()
    }

    fun showNotificationDialog() {
        notificationDialog.show()
    }

    enum class HourPickerType(val text: String) {
        START("Start time"),
        END("End time")
    }
}