package com.example.fascom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.fascom.adapter.showToast
import com.example.fascom.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class Login : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        progress = findViewById(R.id.progress)
        progress?.visibility = View.GONE

        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this@Login,Home::class.java))
            finish()
        }

        forgo.setOnClickListener{
            val intent = Intent(this@Login, Forgotpass::class.java)
            startActivity(intent)
        }
        dirsignupp.setOnClickListener{
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
        login()
    }


    private fun login(){

        btnlogin.setOnClickListener{
            progress?.visibility = View.VISIBLE
            if(TextUtils.isEmpty(log_user.text.toString())){
                name.setError("Please Enter Email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(log_pass.text.toString())){
                name.setError("Please Enter Password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(log_user.text.toString(),log_pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Login,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@Login, Home::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(
                            "User_id",
                            FirebaseAuth.getInstance().currentUser!!.uid
                        )
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        progress?.visibility = View.GONE
                        showToast("Login Failed")
                    }
                }
        }
    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}