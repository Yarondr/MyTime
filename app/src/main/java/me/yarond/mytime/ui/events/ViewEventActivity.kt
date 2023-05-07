package me.yarond.mytime.ui.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import me.yarond.mytime.R
import me.yarond.mytime.ui.activityTypes.DefaultActivity

class ViewEventActivity : DefaultActivity() {

    private lateinit var backImageView: ImageView
    private lateinit var presenter: ViewEventPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        presenter = ViewEventPresenter(this)
        setViews()
        setListeners()
    }

    private fun setViews() {
        backImageView = findViewById(R.id.imageview_viewevent_back)
    }

    private fun setListeners() {
        backImageView.setOnClickListener { presenter.backClicked() }
    }

}