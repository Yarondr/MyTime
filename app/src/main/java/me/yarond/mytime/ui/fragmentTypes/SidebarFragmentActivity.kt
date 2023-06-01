package me.yarond.mytime.ui.fragmentTypes

import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.ui.Sidebar

open class SidebarFragmentActivity(): FragmentActivity(), Sidebar {
    override lateinit var drawerLayout: DrawerLayout
    override lateinit var navigationView: NavigationView
    override lateinit var sidebarButton: ImageButton
}