package com.example.fascom

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fascom.adapter.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
     var dbref : DatabaseReference?= null
     var  database : FirebaseDatabase?= null
    var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        progress = findViewById(R.id.progress)
        progress?.visibility = View.GONE

        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database?.reference!!.child("Profile")
        register()

    skip.setOnClickListener{
        val intent = Intent(this@Signup, Home::class.java)
        startActivity(intent)
    }
    }

    private fun register() {
        btngo.setOnClickListener{

            progress?.visibility = View.VISIBLE
            if(TextUtils.isEmpty(name.text.toString())){
                name.setError("Please Enter Name")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(email.text.toString())){
                name.setError("Please Enter Name")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(mobile.text.toString())){
                name.setError("Please Enter Name")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(pass.text.toString())){
                name.setError("Please Enter Name")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString())
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){

                        val currentUser = auth.currentUser
                        val currentUserdb = dbref?.child((currentUser?.uid!!))
                        currentUserdb?.child("name")?.setValue(name.text.toString())
                        currentUserdb?.child("email")?.setValue(email.text.toString())
                        currentUserdb?.child("mobile")?.setValue(mobile.text.toString())
                        currentUserdb?.child("pass")?.setValue(pass.text.toString())

                        progress?.visibility = View.GONE
                        showToast("Registration success")
                        finish()
                        startActivity(Intent(this@Signup,Home::class.java))

                    }
                    else if (task.exception is FirebaseAuthUserCollisionException) {
                        progress?.visibility = View.GONE
                        Toast.makeText(
                            this@Signup,
                            "User with this email already exist.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        progress?.visibility = View.GONE
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        showToast("Signup Failed")
                    }
                }
        }
    }
}