package me.yarond.mytime.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import me.yarond.mytime.ui.activity_types.SidebarActivity
import me.yarond.mytime.R
import me.yarond.mytime.ui.login.LoginActivity
import me.yarond.mytime.ui.overview.OverviewActivity
import me.yarond.mytime.ui.schedule.WeeklyScheduleActivity

class SettingsActivity : SidebarActivity() {

    private lateinit var themeButton: Button
    private lateinit var logoutButton: Button
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        presenter = SettingsPresenter(this)
        setViews()
        setSideBar()
        setListeners()
    }

    private fun setViews() {
        themeButton = findViewById(R.id.button_settings_theme)
        logoutButton = findViewById(R.id.button_settings_logout)
        drawerLayout = findViewById(R.id.drawerlayout_settings)
        navigationView = findViewById(R.id.navigationview_settings)
        sidebarButton = findViewById(R.id.imagebutton_settings_sidebar)
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
                    val intent = Intent(this, OverviewActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.menu_weekly_schedule -> {
                    val intent = Intent(this, WeeklyScheduleActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.menu_settings -> {
                    drawerLayout.closeDrawer(navigationView)
                }
            }
            true
        }

        onCreateOptionsMenu(navigationView.menu)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setListeners() {
        themeButton.setOnClickListener { presenter.themeButtonClicked() }
        logoutButton.setOnClickListener { presenter.logoutButtonClicked() }
    }

    fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        val sharedPreferences = getSharedPreferences("MyTime", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("darkTheme", enabled)
        editor.apply()
    }

    fun isDarkThemeEnabled(): Boolean {
        val sharedPreferences = getSharedPreferences("MyTime", MODE_PRIVATE)
        return sharedPreferences.getBoolean("darkTheme", true)
    }
}