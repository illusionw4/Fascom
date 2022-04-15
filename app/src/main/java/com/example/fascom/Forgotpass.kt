package com.example.fascom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fascom.adapter.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgotpass.*

class Forgotpass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)

        submit.setOnClickListener {
            val email : String =editText.text.toString().trim(){ it <= ' '}
            if(email.isEmpty()) {
                showToast("Enter email")
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {  task ->
                        if(task.isSuccessful){
                            showToast("Email sent successfully ")
                            finish()
                        }
                        else{
                            showToast(task.exception!!.message.toString())

                        }
                    }
            }

        }
    }
}
