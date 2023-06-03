package me.yarond.mytime.ui

import android.app.Activity
import androidx.core.app.ActivityCompat

class UtilPresenter {

    companion object {
        fun sidebarButtonClicked(view: Sidebar) {
            if (view.isDrawerLayoutOpen()) {
                view.closeDrawerLayout()
            } else {
                view.openDrawerLayout()
            }
        }

        fun onDrawerLayoutSlide(view: Sidebar, slideOffset: Float) {
            if (slideOffset > 0.3) {
                view.closeSidebarArrowImage()
            } else {
                view.openSidebarMenuImage()
            }
        }

        fun askForNotificationPermission(view: Activity) {
            if (ActivityCompat.checkSelfPermission(
                    view.applicationContext,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    view,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}