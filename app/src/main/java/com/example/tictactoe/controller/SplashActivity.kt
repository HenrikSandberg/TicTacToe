package com.example.tictactoe.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val DELAY: Long = 3000 //3 seconds

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) { mDelayHandler!!.removeCallbacks(mRunnable) }
        super.onDestroy()
    }

}