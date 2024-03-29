package me.yarond.mytime.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import me.yarond.mytime.R
import me.yarond.mytime.ui.overview.OverviewActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: LoginPresenter
    private lateinit var loginButton: Button
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        setViews()
        setListeners()
        loadThemeFromSharedPreferences()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.handleLoginResult(signInAccountTask)
        }
    }

    private fun setViews() {
        loginButton = findViewById(R.id.button_login_google_signin)
        statusTextView = findViewById(R.id.textview_login_status)
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            presenter.loginClicked()
        }
    }

    fun goToOverview() {
        val intent = Intent(this, OverviewActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setStatus(message: String) {
        statusTextView.text = message
    }

    private fun loadThemeFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyTime", MODE_PRIVATE)
        val isDarkThemeEnabled = sharedPreferences.getBoolean("darkTheme", true)
        if (isDarkThemeEnabled) {
            setDarkTheme()
        } else {
            setLightTheme()
        }
    }

    private fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}