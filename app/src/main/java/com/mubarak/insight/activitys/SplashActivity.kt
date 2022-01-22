package com.mubarak.insight.activitys


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.mubarak.insight.R

class SplashActivity : AppCompatActivity() {
    // in animation time her write the time in millisecond
    // 1400 millisecond means 1.4 sec
    companion object {
        const val ANIMATION_TIME: Long = 1400
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // here we start the splash activity, after the splash ended MainActivity start
        setContentView(R.layout.activity_splash)
        Handler(this.mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, ANIMATION_TIME)
    }
}