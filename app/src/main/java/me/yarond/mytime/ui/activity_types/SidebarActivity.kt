package me.yarond.mytime.ui.activity_types

import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.ui.Sidebar

open class SidebarActivity(): AppCompatActivity(), Sidebar {
    override lateinit var drawerLayout: DrawerLayout
    override lateinit var navigationView: NavigationView
    override lateinit var sidebarButton: ImageButton
}