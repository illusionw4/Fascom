package com.example.fascom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profiletab.*

class Profiletab : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var dbref : DatabaseReference
    lateinit var  database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiletab)

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbref = database.reference.child("Profile")
        loadprofile()
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    startActivity(Intent(applicationContext, Home::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.explore -> {
                    startActivity(Intent(applicationContext, Explore::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    startActivity(Intent(applicationContext, Addtocart::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    fun setHomeItem(activity: Activity) {
        val bottomNavigationView = activity.findViewById(R.id.bottomnav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.profile
    }

    override fun onBackPressed() {
        val bottomNavigationView = findViewById<View>(R.id.bottomnav) as BottomNavigationView
        val seletedItemId = bottomNavigationView.selectedItemId
        if (R.id.profile !== seletedItemId) {
            super.onBackPressed()
        } else {
            bottomNavigationView.selectedItemId = R.id.profile;
            finish()
        }
    }

    private fun loadprofile(){
        val user = auth.currentUser
        val userref =dbref.child(user!!.uid)
        userref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                profilename.text  = snapshot.child("name").value.toString()
                profileemail.text = snapshot.child("email").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        myorders.setOnClickListener {
            startActivity(Intent(this@Profiletab,Myorders::class.java))
        }

        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@Profiletab,Login::class.java))
            finish()
        }
    }
}