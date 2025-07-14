package com.sakhura.disqueramp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONObject

objet GitHubAuthHelper {
    private const val CLIENT_ID = "TU_CLIENT_ID"
    private const val REDIRECT_URI = "yourapp://callback"
    private const val AUTH_URL = "https://github.com/login/oauth/authorize"
    const val AUTH_URL = "https://github.com/login/oauth/authorize"
    const val TOKEN_URL = "https://github.com/login/oauth/access_token"
    const val USER_API_URL = "https://api.github.com/user"

    fun launchGitHubLogin() {
        val authUri = Uri.parse(AUTH_URL)
            .buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", "user") // Add required scopes
            .build()

        val intent = Intent(Intent.ACTION_VIEW, authUri)
        context.startActivity(intent)
    }

    fun exchangeCodeForToken(code: String, callback: (String?) -> Unit) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET)
            .add("code", code)
            .add("redirect_uri", REDIRECT_URI)
            .build()

        val request = Request.Builder()
            .url(TOKEN_URL)
            .post(requestBody)
            .header("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val json = responseBody?.let { JSONObject(it) }
                val accessToken = json?.optString("access_token")
                callback(accessToken)
            }
        })
    }

    fun getUserInfo(accessToken: String, callback: (JSONObject?) -> Unit) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(USER_API_URL)
            .header("Accept", "application/json")
            .header("Authorization", "Bearer $accessToken")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val userInfo = responseBody?.let { JSONObject(it) }
                callback(userInfo)
            }
        })
    }
}