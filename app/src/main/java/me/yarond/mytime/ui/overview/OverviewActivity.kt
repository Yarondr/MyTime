package me.yarond.mytime.ui.overview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.yarond.mytime.EventNotificationService
import me.yarond.mytime.R
import me.yarond.mytime.model.Event
import me.yarond.mytime.ui.activityTypes.SidebarActivity
import me.yarond.mytime.ui.events.EditEventActivity
import me.yarond.mytime.ui.schedule.WeeklyScheduleActivity
import me.yarond.mytime.ui.settings.SettingsActivity

class OverviewActivity : SidebarActivity() {

    private lateinit var todayEventsLayoutManager: RecyclerView.LayoutManager
    private lateinit var todayEventsAdapter: RecyclerView.Adapter<PendingEventAdapter.ViewHolder>
    private lateinit var todayEventsRecyclerView: RecyclerView
    private lateinit var todayDateTextView: TextView
    private lateinit var addImageButton: ImageButton
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var presenter: OverviewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        presenter = OverviewPresenter(this)
        // TODO: remove this
        // Repository.getInstance().write()
        setViews()
        setSideBar()
        setAdapters()
        setListeners()

        val serviceIntent = Intent(this, EventNotificationService.getInstance().javaClass)
        startService(serviceIntent)
    }

    private fun setViews() {
        todayEventsRecyclerView = findViewById(R.id.recyclerview_overview_today)
        addImageButton = findViewById(R.id.imagebutton_overview_add)
        drawerLayout = findViewById(R.id.drawer_layout_overview)
        navigationView = findViewById(R.id.navigationview_overview)
        sidebarButton = findViewById(R.id.imagebutton_overview_sidebar)
        todayDateTextView = findViewById(R.id.textview_overview_today_date)
        todayDateTextView.text = presenter.getTodayDate()
    }

    private fun setSideBar() {
        toggleSidebar = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggleSidebar)
        toggleSidebar.syncState()

        sidebarButton.setOnClickListener { presenter.sidebarButtonClicked() }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                presenter.onDrawerLayoutSlide(slideOffset)
            }

            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerOpened(drawerView: View) {}
        })

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_overview -> {
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.menu_weekly_schedule -> {
                    val intent = Intent(this, WeeklyScheduleActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        onCreateOptionsMenu(navigationView.menu)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setAdapters() {
        todayEventsLayoutManager = LinearLayoutManager(this)
        todayEventsRecyclerView.layoutManager = todayEventsLayoutManager
        todayEventsAdapter = PendingEventAdapter(arrayListOf())
        todayEventsRecyclerView.adapter = todayEventsAdapter
    }

    fun updateTodayEvents(events: ArrayList<Event>) {
        todayEventsAdapter = PendingEventAdapter(events)
        todayEventsRecyclerView.adapter = todayEventsAdapter
    }

    private fun setListeners() {
        addImageButton.setOnClickListener { presenter.createNewEvent() }
    }

    fun getEditEventActivityIntent(): Intent {
        return Intent(this, EditEventActivity::class.java)
    }
}