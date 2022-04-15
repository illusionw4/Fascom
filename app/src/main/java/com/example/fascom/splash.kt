package com.example.fascom

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.WindowCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.logging.Handler

class splash : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        android.os.Handler().postDelayed({
            if(currentuser != null){
                startActivity(Intent(this@splash,Home::class.java))
                finish()
            }
            else {
                val intent = Intent(this@splash, Starter::class.java)
                startActivity(intent)
                finish()
            }
        }, 2400)
    }
}