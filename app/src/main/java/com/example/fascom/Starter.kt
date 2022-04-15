package com.example.fascom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.fascom.adapter.showToast
import com.example.fascom.databinding.ActivityStarterBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progress
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_starter.*

class Starter : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    private val binding: ActivityStarterBinding by lazy {
        ActivityStarterBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        getstart.setOnClickListener{
            val intent = Intent(this@Starter, Signup::class.java)
            startActivity(intent)
        }
        loginn.setOnClickListener{
            val intent = Intent(this@Starter, Login::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
    }
