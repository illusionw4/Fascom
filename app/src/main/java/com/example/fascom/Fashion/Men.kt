package com.example.fascom.Fashion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.R
import com.example.fascom.adapter.FastFooAdap
import com.example.fascom.model.Fastfood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class Men : Fragment() {

    private lateinit var Vegrecycle: RecyclerView
    private lateinit var Vegarray: ArrayList<Fastfood>
    private lateinit var myadapter: FastFooAdap
    private lateinit var mfirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cakes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mfirestore = FirebaseFirestore.getInstance()
        Vegrecycle = view.findViewById(R.id.veglist)
        Vegrecycle.layoutManager = LinearLayoutManager(context)
        Vegrecycle.setHasFixedSize(true)
        Vegarray = arrayListOf()
        myadapter = FastFooAdap(
            requireContext(),
            Vegarray,
            FirebaseFirestore.getInstance(),
            FirebaseAuth.getInstance()
        )

        EventChangeListner()
    }

    private fun EventChangeListner() {
        mfirestore = FirebaseFirestore.getInstance()
        mfirestore.collection("Fashion").document("1").collection("Men")
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
                            Vegarray.add(dc.document.toObject(Fastfood::class.java))

                        }
                    }
                    Vegrecycle.adapter = FastFooAdap(
                        requireContext(),
                        Vegarray,
                        FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()
                    )
                    myadapter.notifyDataSetChanged()

                }


            })
    }
}