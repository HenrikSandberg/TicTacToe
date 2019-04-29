package com.example.tictactoe.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class SplashActivity : AppCompatActivity() {
    private var handleDelay: Handler? = null
    private val delay: Long = 3000 // 3 seconds

    private val runner: Runnable = Runnable {
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
        handleDelay = Handler()
        handleDelay!!.postDelayed(runner, delay)
    }

    public override fun onDestroy() {
        if (handleDelay != null) {
            handleDelay!!.removeCallbacks(runner)
        }
        super.onDestroy()
    }

}