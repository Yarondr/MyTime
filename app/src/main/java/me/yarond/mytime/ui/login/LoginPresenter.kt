package me.yarond.mytime.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.yarond.mytime.Auth
import me.yarond.mytime.Repository

class LoginPresenter(private var view: LoginActivity) {

    private var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(view, Auth.getGoogleSignInOptions())

    init {

        var firebaseUser = Auth.getFirebaseAuth().currentUser
        if (firebaseUser != null) {
            view.goToOverview()
        }
    }

    fun loginClicked() {
        var signInIntent = googleSignInClient.signInIntent
        view.startActivityForResult(signInIntent, 100)
    }

    fun handleLoginResult(signInAccountTask: Task<GoogleSignInAccount>) {
        if (signInAccountTask.isSuccessful) {
            try {
                var googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                if (googleSignInAccount != null) {
                    var authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

                    Auth.getFirebaseAuth().signInWithCredential(authCredential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Repository.getInstance().setEmail(Auth.getFirebaseAuth().currentUser!!.email!!)
                                view.goToOverview()
                            } else {
                                view.displayToast("Login failed")
                            }
                        }
                }
            } catch (e: ApiException) {
                view.displayToast("Login failed")
            }
        }
    }
}