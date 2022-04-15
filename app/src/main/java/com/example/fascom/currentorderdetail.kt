package com.example.fascom

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.adapter.Ordersinsideview
import com.example.fascom.adapter.Ordersview
import com.example.fascom.model.MyCartModel
import com.example.fascom.model.Myordersview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.orderview.*
import java.util.*
import kotlin.collections.ArrayList
import android.R.string
import android.widget.ProgressBar
import com.google.firestore.v1.StructuredQuery


class currentorderdetail : AppCompatActivity() {

    private lateinit var itemrecycle: RecyclerView
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var myadapter: Ordersinsideview
    private lateinit var msadapter: Ordersview
    private lateinit var mArraylist: ArrayList<MyCartModel>
    private lateinit var msArraylist: ArrayList<Myordersview>
    var progress: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currentorderdetail)

        val lUUID = UUID.randomUUID().toString().replace("-","").substring(0,8)

        progress = findViewById(R.id.progress)
        progress?.visibility = View.VISIBLE

        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        //recyclerview
        itemrecycle = findViewById(R.id.currentorder)
        itemrecycle.visibility = View.GONE
        itemrecycle.setHasFixedSize(true)
        itemrecycle.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        //myadapter
        mArraylist = arrayListOf<MyCartModel>()
        myadapter = Ordersinsideview(this, mArraylist)
        itemrecycle.adapter = Ordersinsideview(this, mArraylist)

        //
            var s: String? = intent.getStringExtra("documentid")

              Log.i("documentid", s.toString())
        //Firestore
            mfirestore.collection("users").document(auth.currentUser!!.uid)
                .collection("Orders").document(s!!).collection("New Orders")
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        progress?.visibility = View.GONE
                        itemrecycle.visibility = View.VISIBLE
                        for (documentSnapshot in task.result!!.documents) {

                            var documentid = documentSnapshot.id
                            val addlist = documentSnapshot.toObject(MyCartModel::class.java)
                            mArraylist.add(addlist!!)
                        }
                        itemrecycle.adapter = Ordersinsideview(applicationContext, mArraylist)
                        myadapter.notifyDataSetChanged()
                    }
                })
        }

    }
