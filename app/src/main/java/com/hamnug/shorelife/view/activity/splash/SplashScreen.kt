package com.hamnug.shorelife.view.activity.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.hamnug.shorelife.R
import com.hamnug.shorelife.data.local.preferences.TokenPreferences
import com.hamnug.shorelife.data.local.preferences.dataStore
import com.hamnug.shorelife.view.activity.welcome.WelcomeActivity
import com.hamnug.shorelife.view.activity.main.MainActivity
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            lifecycleScope.launch {
                val token = getTokenDataStore()
                if (token.isNotEmpty()) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(
                            this@SplashScreen,
                            WelcomeActivity::class.java
                        )
                    )
                }
                finish()
            }
        }, SPLASH_TIME_OUT)

        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private suspend fun getTokenDataStore():String {
        val dataStore = TokenPreferences.getInstance(this.dataStore)
        return dataStore.getToken()
    }
}