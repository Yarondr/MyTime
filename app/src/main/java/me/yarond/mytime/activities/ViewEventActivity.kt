package me.yarond.mytime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import me.yarond.mytime.R

class ViewEventActivity : AppCompatActivity() {

    private lateinit var backImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        setViews()
        setListeners()
    }

    private fun setViews() {
        backImageView = findViewById<ImageView>(R.id.imageview_viewevent_back)
    }

    private fun setListeners() {
        backImageView.setOnClickListener {
            finish()
        }
    }

}