package com.mubarak.insight.activitys


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.mubarak.insight.R

class SplashActivity : AppCompatActivity() {

    companion object {
        const val ANIMATION_TIME: Long = 1400
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(this.mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, ANIMATION_TIME)
    }
}