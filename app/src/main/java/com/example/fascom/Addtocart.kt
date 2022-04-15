package com.example.fascom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.adapter.MyCartAdap
import com.example.fascom.model.MyCartModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_addtocart.*

class Addtocart : AppCompatActivity(){

    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var check: Button
    private lateinit var mrecycler: RecyclerView
    private lateinit var myadapter: MyCartAdap
    private lateinit var mArraylist: ArrayList<MyCartModel>
    private lateinit var overtotalamount: TextView
    var progress: ProgressBar? = null
    var totalAmount = 0.0


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtocart)

        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        progress = findViewById(R.id.progress)
        progress?.visibility = View.VISIBLE

        overtotalamount = findViewById(R.id.overallcost)

        check = findViewById(R.id.checkout)

//      recyclerviewcode
        mrecycler = findViewById(R.id.cartitems)
        mrecycler.layoutManager = LinearLayoutManager(this)
        mrecycler.setHasFixedSize(true)

//        backbut.setOnClickListener {
//          finish()
//        }
//       Arraylist
        mArraylist = arrayListOf<MyCartModel>()
        myadapter = MyCartAdap(this, mArraylist)

        myadapter.onDelete = {
            overtotalamount.text = it.toString()
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        }
        mrecycler.adapter = myadapter

        //FirestoreData
        mfirestore.collection("CurrentUser").document(auth.currentUser!!.uid)
            .collection("AddToCart").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    progress?.visibility = View.GONE
                    for (documentSnapshot in task.result!!.documents) {

                        val documentid = documentSnapshot.id
                        val cartModel = documentSnapshot.toObject(MyCartModel::class.java)
                        cartModel?.documentid = documentid
                        mArraylist.add(cartModel!!)
                    }
                    myadapter = MyCartAdap(this, mArraylist)

                    myadapter.onDelete = {
                        totalAmount = it
                        overtotalamount.text = it.toString()
//                        Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                    }
                    mrecycler.adapter = myadapter
                    myadapter.notifyDataSetChanged()
                    calculate(mArraylist)

                }
            })
//firebase
        //For disabling Checkout
        val subhead = FirebaseDatabase.getInstance().reference.child("Close")
        subhead.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                subclos.text  = snapshot.child("text").value.toString()
                check.isEnabled = subclos.text == "ha"
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    //

        checkout.setOnClickListener {
            if (myadapter.itemCount == 0) {
                Toast.makeText(
                    this@Addtocart,
                    "Shop To Checkout",
                    Toast.LENGTH_LONG
                ).show()
                check.isEnabled = false
            }
            else {
                emptycode.visibility = View.GONE
                 val intent = Intent(this@Addtocart, Address::class.java)
                intent.putExtra("pass", totalAmount)
                val list: ArrayList<MyCartModel> = ArrayList<MyCartModel>()
                intent.putParcelableArrayListExtra("itemlist", mArraylist)
                startActivity(intent)
        }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.selectedItemId = R.id.search


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

    }

    override fun onBackPressed() {
            finish()
    }

    fun calculate(mArrayList: ArrayList<MyCartModel>) {
        for (myCartModel : MyCartModel in mArraylist) {
            totalAmount += myCartModel.totalprice
        }
        overtotalamount.text = totalAmount.toString()
    }
}
