package com.halfinfinity.flashdownloader.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.halfinfinity.flashdownloader.ui.pinboard.PinboardActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Simulating initial configs loading
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, PinboardActivity::class.java))
            finish()
        }, 3000)
    }
}