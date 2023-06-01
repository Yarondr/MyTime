package me.yarond.mytime.persistency

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Auth {

    companion object {
        private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        private var googleSignInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("214344413709-jmupuov1malpm6dhgcm6ipc9b3kh83nl.apps.googleusercontent.com")
            .requestEmail()
            .build()

        fun getFirebaseAuth(): FirebaseAuth {
            return firebaseAuth
        }

        fun getGoogleSignInOptions(): GoogleSignInOptions {
            return googleSignInOptions
        }
    }
}