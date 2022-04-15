package com.example.fascom.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.R
import com.example.fascom.adapter.FastFooAdap
import com.example.fascom.model.Fastfood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class Homeappliances : AppCompatActivity() {
    private lateinit var mrecycler: RecyclerView
    private lateinit var madapter: FastFooAdap
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mArraylist: ArrayList<Fastfood>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeappliances)

        //recyclerviewcode
        mrecycler = findViewById(R.id.kitchlist)
        mrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mrecycler.setHasFixedSize(true)

        //Arraylist
        mArraylist = arrayListOf<Fastfood>()
        madapter = FastFooAdap(
            this,
            mArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )
        mrecycler.adapter = FastFooAdap(
            this,
            mArraylist,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )
        EventChangeListner()
    }

    private fun EventChangeListner() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("Homeappliances")
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
                            mArraylist.add(dc.document.toObject(Fastfood::class.java))
                        }
                    }

                    mrecycler.adapter = FastFooAdap(
                        this@Homeappliances,
                        mArraylist,
                        FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()
                    )
                    madapter.notifyDataSetChanged()

                }


            })
    }
}