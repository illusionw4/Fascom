package com.example.fascom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.Fashion.Fashiontab
import com.example.fascom.adapter.MydotdAdapter
import com.example.fascom.adapter.PopulAdap
import com.example.fascom.model.Dotd
import com.example.fascom.model.popchoi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.site_valley.imagesliderexampleinkotlin.MySliderImageAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_home.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.fascom.activities.Homeappliances

import com.example.fascom.activities.Kitchen

class Home : AppCompatActivity() {

    private lateinit var mytoolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mrecycler: RecyclerView
    private lateinit var msrecycler: RecyclerView
    private lateinit var myadapter: MydotdAdapter
    private lateinit var mysadapter: PopulAdap
    private lateinit var mArraylist: ArrayList<Dotd>
    private lateinit var msArraylist: ArrayList<popchoi>
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var txtMarquee: TextView


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //BottomSheet
//        var bottomFragment = BottomFragment()
//        bottomFragment.show(supportFragmentManager,"TAG")

        txtMarquee = findViewById(R.id.marquee);
        txtMarquee.isSelected = true;


        //recyclerviewcode
        mrecycler = findViewById(R.id.listvieww)
        mrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mrecycler.setHasFixedSize(true)


        //recyclerviewcode
        msrecycler = findViewById(R.id.popcho)
        msrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        msrecycler.setHasFixedSize(true)

        //Arraylist
        mArraylist = arrayListOf<Dotd>()
        myadapter = MydotdAdapter(
            this,
            mArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )
        mrecycler.adapter = MydotdAdapter(
            this,
            mArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )

        //Arraylist
        msArraylist = arrayListOf<popchoi>()
        mysadapter = PopulAdap(
            this,
            msArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )
        msrecycler.adapter = PopulAdap(
            this,
            msArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )

        //imageslider
        val imageSlider = findViewById<SliderView>(R.id.slide)
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://firebasestorage.googleapis.com/v0/b/dorus-2d869.appspot.com/o/3609477.jpg?alt=media&token=e84f0a9a-2fcf-4682-a3ee-d92a1580e49e")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/dorus-2d869.appspot.com/o/33152.jpg?alt=media&token=35aab293-3826-4cda-806f-be3f859b33e0")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/dorus-2d869.appspot.com/o/household.jpg?alt=media&token=480a6935-39c6-4860-a579-48b9235da660")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/dorus-2d869.appspot.com/o/furniture.jpg?alt=media&token=85095499-7f1e-4024-9a9d-2de8c1b70f63")
        setImageInSlider(imageList, imageSlider)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.pinkti)
        }

        bottomNavigationView = findViewById(R.id.bottomnav)
        //toolbar
        mytoolbar = findViewById(R.id.newtoolbar)
        mytoolbar.title = ""
        setSupportActionBar(mytoolbar)

        kitch.setOnClickListener {
            val intent = Intent(this@Home, Kitchen::class.java)
            startActivity(intent) }

        homeappliance.setOnClickListener {
            val intent = Intent(this@Home, Homeappliances::class.java)
            startActivity(intent) }

        appraels.setOnClickListener {
            val intent = Intent(this@Home, Fashiontab::class.java)
            startActivity(intent) }

//        furnitures.setOnClickListener {
//            val intent = Intent(this@Home, Vegetab::class.java)
//            startActivity(intent) }

        EventChangeListner()
        Popularchoices()
//        getdata()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.explore -> {
                    startActivity(Intent(applicationContext, Explore::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.search -> {
                    showCustomAlert()
                }
                R.id.profile -> {
                    startActivity(Intent(applicationContext, Profiletab::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
//TextView retrieve from firebase
        val userref = FirebaseDatabase.getInstance().reference.child("Marquee")
        userref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                txtMarquee.text  = snapshot.child("text").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
//Shop closed text
        val shoptxt = FirebaseDatabase.getInstance().reference.child("Close")
        shoptxt.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                closing.text  = snapshot.child("text").value.toString()
                if(closing.text == "ha") {
                    closing.visibility = View.GONE
                }
                else{
                    closing.visibility = View.VISIBLE
            }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//subheading under shope close
        val subhead = FirebaseDatabase.getInstance().reference.child("Close")
        subhead.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                subclose.text  = snapshot.child("subclose").value.toString()
                if(subclose.text == "ha") {
                    divider.visibility = View.GONE
                    subclose.visibility = View.GONE
                }
                else{
                    divider.visibility = View.VISIBLE
                    subclose.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun Popularchoices() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("popular")
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
                            msArraylist.add(dc.document.toObject(popchoi::class.java))

                        }
                    }
                    msrecycler.adapter = PopulAdap(
                        this@Home,
                        msArraylist,
                        FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()
                    )
                    myadapter.notifyDataSetChanged()

                }


            })
    }
    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.cartdialog, null)
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        val grocart = dialogView.findViewById<Button>(R.id.grocart)
        val btDismiss = dialogView.findViewById<Button>(R.id.backdis)
        grocart.setOnClickListener {
            val intent = Intent(this@Home, Addtocart::class.java)
            startActivity(intent)
        }
        btDismiss.setOnClickListener {
           customDialog.dismiss()
        }
    }

    private fun setImageInSlider(images: ArrayList<String>, slide: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        slide.setSliderAdapter(adapter)
        slide.isAutoCycle = true
        slide.startAutoCycle()
    }

    private fun EventChangeListner() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("Dealsofday")
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
                            mArraylist.add(dc.document.toObject(Dotd::class.java))
                        }
                    }
                    mrecycler.adapter = MydotdAdapter(
                        this@Home,
                        mArraylist,
                        FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()
                    )
                    myadapter.notifyDataSetChanged()

                }


            })
    }
}

//    private fun getdata() {
//
//        FirebaseDatabase.getInstance().getReference("pincode")
//            .addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    for (deotSnapshot in snapshot.children) {
//
//                        val prods = deotSnapshot.getValue(Dotd::class.java)
//                        mArraylist.add(prods!!)
//                    }
//                    mrecycler.adapter = MydotdAdapter(mArraylist)
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//    }
