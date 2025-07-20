package com.sakhura.disqueramp.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GooglesSingHelper {

    private val = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("ID_TOKEN")
        .requestEmail()
        .build()

    private val googlesSignInClient = GoogleSignIn.getClient(context, gso)

    fun getSignInIntent(): Intent {
        return googlesSignInClient.signInIntent
    }

    fun handleSignInResult(data: Intent?, onSucess: (GoogleSignInAccount) -> Unit, onError: (Exception) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if ( account != null){
                onSucess(account)
            }
        } catch (e: ApiException) {
            onError(e)
            }
        }
     fun signOut(onComplete: () -> Unit) {
         googlesSignInClient.signOut().addOnCompleteListener {
             onComplete()
         }
      {
    }



}