package com.example.fascom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.adapter.*
import com.example.fascom.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldPath.documentId
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profiletab.*
import kotlinx.android.synthetic.main.orderview.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Myorders : AppCompatActivity() {

    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mrecycler: RecyclerView
    private lateinit var myadapter: Ordersview
    private lateinit var mArraylist: ArrayList<Myordersview>
    lateinit var noordertext: TextView
    lateinit var myordtxt: TextView
    lateinit var auth : FirebaseAuth
    var myOrdersList: List<Myordersview> = ArrayList<Myordersview>()
    var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myorders)
        val uuid: UUID = UUID.randomUUID()
        val randomUUIDString: String = uuid.toString()

        progress = findViewById(R.id.progress)
        progress?.visibility = View.VISIBLE

        auth= FirebaseAuth.getInstance()
        mfirestore = FirebaseFirestore.getInstance()

        noordertext = findViewById(R.id.noorder)
        myordtxt = findViewById(R.id.mytext)
        myordtxt.visibility = View.GONE

        //recyclerviewcode
        mrecycler = findViewById(R.id.orderslist)
        mrecycler.visibility = View.GONE
        mrecycler.layoutManager = LinearLayoutManager(this)
        mrecycler.setHasFixedSize(true)

        //Arraylist
        mArraylist = arrayListOf<Myordersview>()
        myadapter = Ordersview(this, mArraylist)
        mrecycler.adapter = Ordersview(this, mArraylist)
        myOrdersList = arrayListOf<Myordersview>()

        val s = intent.getStringExtra("documentid").toString()
        intent.putExtra("documentid",s)

        //Testing Firestore document Id retrieve
        mfirestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("orders").orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    progress?.visibility = View.GONE
                    for (documentSnapshot in task.result!!.documents) {
                        val addlist = documentSnapshot.toObject(Myordersview::class.java)
                        mArraylist.add(addlist!!)
                    }
                    if (myadapter.itemCount != 0) {
                        myordtxt.visibility = View.VISIBLE
                        mrecycler.visibility = View.VISIBLE
                        mrecycler.adapter = Ordersview(applicationContext, mArraylist)
                        myadapter.notifyDataSetChanged()
                    }
                    else {
                        myordtxt.visibility = View.GONE
                        noordertext.visibility = View.VISIBLE
                        progress?.visibility = View.GONE
                        mrecycler.visibility = View.GONE
                    }
                }
            })

    }
    }