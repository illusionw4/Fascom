package com.example.fascom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.adapter.Ordersinsideview
import com.example.fascom.model.Myordersview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyOrdersinside : AppCompatActivity() {

    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mrecycler: RecyclerView
    private lateinit var myadapter: Ordersinsideview
    private lateinit var mArraylist: ArrayList<Myordersview>
    private lateinit  var mtext: TextView
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ordersinside)
        val uuid: UUID = UUID.randomUUID()
        val randomUUIDString: String = uuid.toString()
        auth= FirebaseAuth.getInstance()
        mfirestore = FirebaseFirestore.getInstance()


        Log.i("s", MyOrdersinside().toString())
        mtext =  findViewById(R.id.docuid)
        var s = intent.getStringExtra(intent.getStringExtra("documentid"))
        mtext.text = s

//        val someDate: String
//        val saveCurrentDate: String
//        val calForDate = Calendar.getInstance()
//
//        val currentDate = SimpleDateFormat("MM dd yyyy")
//        saveCurrentDate = currentDate.format(calForDate.time)
//
//        //recyclerviewcode
//        mrecycler = findViewById(R.id.orderslist)
//        mrecycler.layoutManager = LinearLayoutManager(this)
//        mrecycler.setHasFixedSize(true)

//        //Arraylist
//        mArraylist = arrayListOf<Myordersview>()
//        myadapter = Ordersinsideview(this, mArraylist)
//        mrecycler.adapter = Ordersinsideview(this, mArraylist)
//
//        Log.i("document(order+${saveCurrentDate.format(Date())})", MyOrdersinside().toString())
//
//        //Testing Firestore document Id retrieve
//        mfirestore.collection("Testing").document(auth.currentUser!!.uid)
//            .collection("Orders")
//            .document("order+${saveCurrentDate.format(Date())}").collection("Userorder")
//            .get()
//            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
//                if (task.isSuccessful) {
//                    for (documentSnapshot in task.result!!.documents) {
//
//                        var documentid = documentSnapshot.id
//                        val addlist = documentSnapshot.toObject(Myordersview::class.java)
//
//                        mArraylist.add(addlist!!)
//                    }
//                    mrecycler.adapter = Ordersinsideview(applicationContext, mArraylist)
//                    myadapter.notifyDataSetChanged()
//                }
//            })

    }
}