package me.yarond.mytime.ui.settings

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import me.yarond.mytime.persistency.Auth
import me.yarond.mytime.ui.UtilPresenter

class SettingsPresenter(private var view: SettingsActivity) {

    private var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(view, Auth.getGoogleSignInOptions())

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

    fun logoutButtonClicked() {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Auth.getFirebaseAuth().signOut()
                    view.goToLogin()
                } else {
                    view.displayToast("Logout failed")
                }
            }
    }

}