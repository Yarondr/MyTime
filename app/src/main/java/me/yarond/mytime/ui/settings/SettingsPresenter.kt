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

    fun themeButtonClicked() {
        if (view.isDarkThemeEnabled()) {
            view.setDarkThemeEnabled(false)
            view.setLightTheme()
        } else {
            view.setDarkThemeEnabled(true)
            view.setDarkTheme()
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