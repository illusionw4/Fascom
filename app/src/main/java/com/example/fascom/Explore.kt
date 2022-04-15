package com.example.fascom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.adapter.ExploreAdap
import com.example.fascom.adapter.MydotdAdapter
import com.example.fascom.model.Dotd
import com.example.fascom.model.Explotab
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import com.site_valley.imagesliderexampleinkotlin.MySliderImageAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.appbar.*

class Explore : AppCompatActivity() {

    private lateinit var mrecycler: RecyclerView
    private lateinit var mArraylist: ArrayList<Explotab>
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var myadapter: ExploreAdap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.selectedItemId = R.id.explore

        //recyclerview

        mrecycler = findViewById(R.id.explo)
        mrecycler.layoutManager = LinearLayoutManager(this)
        mrecycler.setHasFixedSize(true)

        mArraylist = arrayListOf<Explotab>()
        myadapter = ExploreAdap(mArraylist)
        mrecycler.adapter = ExploreAdap(mArraylist)


        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.home -> {
                    startActivity(Intent(applicationContext, Home::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.explore -> {
                    return@OnNavigationItemSelectedListener true
                }

                R.id.search -> {
                    startActivity(Intent(applicationContext, Addtocart::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    startActivity(Intent(applicationContext, Profiletab::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        })


        EventChangeListner()
    }

    fun setHomeItem(activity: Activity) {
        val bottomNavigationView = activity.findViewById(R.id.bottomnav) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.explore
    }

    override fun onBackPressed() {
        val bottomNavigationView = findViewById<View>(R.id.bottomnav) as BottomNavigationView
        val seletedItemId = bottomNavigationView.selectedItemId
        if (R.id.explore !== seletedItemId) {
            super.onBackPressed()
        }
        else {
            bottomNavigationView.selectedItemId = R.id.explore;
            finish()
        }
    }

    private fun EventChangeListner() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("Exploretab")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {

                        if (dc.type == DocumentChange.Type.ADDED) {
                            mArraylist.add(dc.document.toObject(Explotab::class.java))

                        }
                    }
                    mrecycler.adapter = ExploreAdap(mArraylist)
                    myadapter.notifyDataSetChanged()

                }

            })
    }
}