package com.example.fascom

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fascom.adapter.MyCartAdap
import com.example.fascom.model.MyCartModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.dialogcustom.*
import kotlinx.serialization.KSerializer
import java.io.Serializable
import java.util.HashMap

class Payment : AppCompatActivity() {

    private lateinit var mArraylist: ArrayList<MyCartModel>
    private lateinit var overtotalamount: TextView
    private lateinit var shipping: TextView
    private lateinit var subtotaltext: TextView
    private lateinit var myadapter: MyCartAdap
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var totalAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


        overtotalamount = findViewById(R.id.carttotal)
        shipping = findViewById(R.id.shipping)
        subtotaltext = findViewById(R.id.subtotal)

//       Arraylist
        mArraylist = arrayListOf<MyCartModel>()
        myadapter = MyCartAdap(this, mArraylist)

//        double s= getIntent().getDoubleExtra("pass");
//        Intent i = new Intent(getApplicationContext(), Payment.class);
//        i.putExtra("pass", s);
//        startActivity(i);

        payment_btn.setOnClickListener {
         showCustomAlert()
        }

        val inn = intent
        val passing: Double = inn.getDoubleExtra("pass",0.0)
        overtotalamount.text = inn.getDoubleExtra("pass",0.0).toString()

        if (intent.getDoubleExtra("pass", 0.0) > 59.0) {
                var ship = 0.0
                shipping.text = ship.toString()
               var subto = ship + intent.getDoubleExtra("pass", 0.0)
                subtotaltext.text = subto.toString()
            }
        else if (intent.getDoubleExtra("pass", 0.0) < 59.0) {
                var ship = 9.0
                shipping.text = ship.toString()
               var subto = ship + intent.getDoubleExtra("pass", 0.0)
              subtotaltext.text = subto.toString()
            }
        }

    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.dialogcustom, null)
        val currentuser = auth.currentUser
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        val cod = dialogView.findViewById<Button>(R.id.cod)
        val btDismiss = dialogView.findViewById<Button>(R.id.btDismissCustomDialog)

        cod.setOnClickListener {
            val s = intent.getDoubleExtra("pass",0.0)
            val h = intent.getParcelableArrayListExtra<Parcelable>("itemlist")
                val i = Intent(applicationContext, Placedorder::class.java)
                i.putParcelableArrayListExtra("itemlist", h)
                i.putExtra("pass", s)
                mArraylist.clear()
                startActivity(i)
            }

        btDismiss.setOnClickListener {
            customDialog.dismiss()
            val intent = Intent(this@Payment, Home::class.java)
            startActivity(intent)
        }
    }
    }