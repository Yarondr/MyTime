package me.yarond.mytime.ui.settings

import me.yarond.mytime.ui.UtilPresenter

class SettingsPresenter(private var view: SettingsActivity) {

    fun sidebarButtonClicked() {
        UtilPresenter.sidebarButtonClicked(view)
    }

    fun onDrawerLayoutSlide(slideOffset: Float) {
        UtilPresenter.onDrawerLayoutSlide(view, slideOffset)
    }

    fun themeSwitchToggle(isChecked: Boolean) {
        if (isChecked) {
            view.setDarkTheme()
        } else {
            view.setLightTheme()
        }
    }

    fun setInitialThemeSwitchStatus() {
        if (view.isDarkThemeOnStart()) {
            view.setThemeSwitchStatus(true)
        } else {
            view.setThemeSwitchStatus(false)
        }
    }

}