package me.yarond.mytime.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import me.yarond.mytime.persistency.Auth
import me.yarond.mytime.persistency.Repository

class LoginPresenter(private var view: LoginActivity) {

    private var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(view, Auth.getGoogleSignInOptions())

    init {
        val firebaseUser = Auth.getFirebaseAuth().currentUser
        if (firebaseUser != null) {
            view.goToOverview()
        }
    }

    fun loginClicked() {
        val signInIntent = googleSignInClient.signInIntent
        view.startActivityForResult(signInIntent, 100)
    }

    fun handleLoginResult(signInAccountTask: Task<GoogleSignInAccount>) {
        if (signInAccountTask.isSuccessful) {
            try {
                val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                if (googleSignInAccount != null) {
                    view.setStatus("Logging in...")
                    val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

                    Auth.getFirebaseAuth().signInWithCredential(authCredential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Repository.getInstance().setEmail(Auth.getFirebaseAuth().currentUser!!.email!!)
                                view.goToOverview()
                            } else {
                                view.setStatus("Login failed")
                            }
                        }
                }
            } catch (e: ApiException) {
                view.setStatus("Login failed")
            }
        }
    }
}