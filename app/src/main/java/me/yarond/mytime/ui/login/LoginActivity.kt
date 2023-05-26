package me.yarond.mytime.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import me.yarond.mytime.R
import me.yarond.mytime.ui.overview.OverviewActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var presenter: LoginPresenter
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        setViews()
        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            var signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.handleLoginResult(signInAccountTask)
        }
    }

    fun setViews() {
        loginButton = findViewById(R.id.button_login_google_signin)
    }

    fun setListeners() {
        loginButton.setOnClickListener {
            presenter.loginClicked()
        }
    }

    fun goToOverview() {
        var intent = Intent(this, OverviewActivity::class.java)
        startActivity(intent)
    }

    fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}