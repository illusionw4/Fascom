package com.example.fascom

import android.content.Intent
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.example.fascom.model.Dotd
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_productactivity.*
import kotlinx.android.synthetic.main.cartitem.*
import kotlinx.android.synthetic.main.itemintabs.view.*
import kotlinx.android.synthetic.main.itemintabs.view.ourpri
import kotlinx.android.synthetic.main.itemlistdotd.*
import kotlinx.android.synthetic.main.itemlistdotd.image
import kotlinx.android.synthetic.main.itemlistdotd.view.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull
import android.widget.ArrayAdapter

import android.widget.Spinner
import kotlinx.android.synthetic.main.defappbar.*

class Productactivity : AppCompatActivity() {

    var totalquantity = 1
    var totalprice = 0
    private lateinit var detailimg: ImageView
    private lateinit var addto: Button
    private lateinit var prodname: TextView
    private lateinit var pcost: TextView
    private lateinit var psize: TextView
    private lateinit var quanum: TextView
    private lateinit var quanti: TextView
    private lateinit var addin: ImageView
    private lateinit var removi: ImageView
    private lateinit var stomks: TextView
    private lateinit var descri: TextView
    private lateinit var mrpp: TextView
    private lateinit var price: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productactivity)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        detailimg = findViewById(R.id.proimage)
        descri = findViewById(R.id.productdesc)
        addin = findViewById(R.id.adding)
        quanum = findViewById(R.id.quanumber)
        removi = findViewById(R.id.removing)
        addto = findViewById(R.id.procart)
        prodname = findViewById(R.id.proname)
        quanti = findViewById(R.id.proquantity)
        stomks = findViewById(R.id.availability)
        price = findViewById(R.id.proprice)
        pcost = findViewById(R.id.procost)
        psize = findViewById(R.id.prosize)
        mrpp = findViewById(R.id.mrpp)

        var link = intent.getStringExtra("images")

        Glide.with(applicationContext).load(link).into(detailimg)
        //detailimg.setImageResource(intent.getStringExtra("images"))
        descri.text = intent.getStringExtra("description")
        prodname.text = intent.getStringExtra("productname")
        quanti.text = intent.getStringExtra("quantity")
        stomks.text = intent.getStringExtra("stock")
        mrpp.text = intent.getStringExtra("mrp")
        price.text = intent.getStringExtra("ourpri")
        pcost.text = intent.getIntExtra("cost",0).toString()
        psize.text = intent.getIntExtra("size",0).toString()

        totalprice = intent.getIntExtra("cost",0)*totalquantity


        backbut.setOnClickListener {
            finish()
        }

        bag.setOnClickListener {
            val intent = Intent(this@Productactivity, Addtocart::class.java)
            startActivity(intent)
        }

        //add to cart
        addin.setOnClickListener {
            if (totalquantity < 4) {
                totalquantity++
                quanumber.text = totalquantity.toString()
                totalprice = intent.getIntExtra("cost",0)*totalquantity

            }
        }
        removi.setOnClickListener {
            if (totalquantity > 1) {
                totalquantity--
                quanumber.text = totalquantity.toString()
                totalprice = intent.getIntExtra("cost",0)*totalquantity
            }
        }
        addto.setOnClickListener {
            addtocart()
//            totalprice = intent.getIntExtra("cost",0)
        }
    }
    private fun addtocart() {
            val cartMap = HashMap<String, Any>()
        val image : String
        var link = intent.getStringExtra("images")
    image = Glide.with(applicationContext).load(link).into(detailimg).toString()
            cartMap["productname"] = prodname.text.toString()
            cartMap["price"] = price.text.toString()
            cartMap["cost"] = pcost.text.toString()
            cartMap["size"] = psize.text.toString()
            cartMap["image"] = link.toString()
            cartMap["quant"] = quanti.text.toString()
            cartMap["totalQuantity"] = quanum.text.toString()
            cartMap["totalprice"] = totalprice

        firestore.collection("CurrentUser").document(auth.currentUser!!.uid)
            .collection("AddToCart").add(cartMap)
            .addOnCompleteListener(OnCompleteListener<DocumentReference?> {
                Toast.makeText(this@Productactivity,
                    "Added To Cart", Toast.LENGTH_SHORT
                ).show()
                finish()
            })
    }
}