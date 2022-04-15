package com.example.fascom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.R
import com.example.fascom.adapter.AddressAdap
import com.example.fascom.model.AddressModel
import com.example.fascom.model.MyCartModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_addtocart.*
import kotlinx.android.synthetic.main.addressitem.*

class Address : AppCompatActivity(), AddressAdap.SelectedAddress {

    private lateinit var mrecycler: RecyclerView
    private lateinit var myadapter: AddressAdap
    private lateinit var auth: FirebaseAuth
    private lateinit var mArraylistt: ArrayList<MyCartModel>
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var mArraylist: ArrayList<AddressModel>
    var progress: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        progress = findViewById(R.id.progress)
        progress?.visibility = View.VISIBLE

        //recyclerviewcode
        mrecycler = findViewById(R.id.addresslist)
        mrecycler.layoutManager = LinearLayoutManager(this)
        mrecycler.setHasFixedSize(true)

        //Arraylist
        mArraylist = arrayListOf<AddressModel>()
        mArraylistt = arrayListOf<MyCartModel>()
        myadapter = AddressAdap(applicationContext, mArraylist)
        mrecycler.adapter = AddressAdap(applicationContext, mArraylist)

        add_address_btn.setOnClickListener {
            val s = intent.getDoubleExtra("pass", 0.0)
            val h = intent.getParcelableArrayListExtra<Parcelable>("itemlist")
            val i = Intent(applicationContext, com.example.fascom.Addaddress::class.java)
            i.putExtra("pass", s)
            i.putParcelableArrayListExtra("itemlist", h)
            startActivity(i)
            finish()
        }


        mfirestore.collection("Ordered").document(auth.currentUser!!.uid)
            .collection("Address").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    progress?.visibility = View.GONE
                    for (documentSnapshot in task.result!!.documents) {
                        val documentid = documentSnapshot.id
                        val addlist = documentSnapshot.toObject(AddressModel::class.java)
                        addlist?.documentid = documentid
                        mArraylist.add(addlist!!)
                    }

                    mrecycler.adapter = AddressAdap(applicationContext, mArraylist)
                    myadapter.notifyDataSetChanged()
                }
            })

        payment_btn.setOnClickListener {
            if (myadapter.itemCount != 0) {
                val s = intent.getDoubleExtra("pass", 0.0)
                val h = intent.getParcelableArrayListExtra<Parcelable>("itemlist")
                val i = Intent(applicationContext, Payment::class.java)
                i.putExtra("pass", s)
                i.putParcelableArrayListExtra("itemlist", h)
                startActivity(i)
            }
            else{
                Toast.makeText(this, "Add Address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setAddress(
        usercall: String?,
        usercity: String?,
        userflat: String?,
        username: String?,
        userstreet: String?,
        userzip: String?
    ) {

    }
}