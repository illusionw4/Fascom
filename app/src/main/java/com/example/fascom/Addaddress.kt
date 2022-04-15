package com.example.fascom

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fascom.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_bottom.*
import java.util.HashMap

class Addaddress : AppCompatActivity() {

    private lateinit var fullname: EditText
    private lateinit var call: EditText
    private lateinit var flatno: EditText
    private lateinit var street: EditText
    private lateinit var city: EditText
    private lateinit var zipcode: EditText
    private lateinit var addaddress: Button

    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addaddress)


        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        fullname = findViewById(R.id.fullname)
        call = findViewById(R.id.call)
        flatno = findViewById(R.id.flatno)
        street = findViewById(R.id.street)
        city = findViewById(R.id.city)
        zipcode = findViewById(R.id.zipcode)
        addaddress = findViewById(R.id.addaddress)



        addaddress.setOnClickListener {

            val username = fullname.text.toString()
            val usercall = call.text.toString()
            val userflat = flatno.text.toString()
            val userstreet = street.text.toString()
            val usercity = city.text.toString()
            val userzip = zipcode.text.toString()
            var finaladdress = ""

            if (!username.isEmpty()) {
                finaladdress += username
            }
            if (!usercall.isEmpty()) {
                finaladdress += usercall
            }
            if (!userflat.isEmpty()) {
                finaladdress += userflat
            }
            if (!userstreet.isEmpty()) {
                finaladdress += userstreet
            }
            if (!usercity.isEmpty()) {
                finaladdress += usercity
            }
            if (!userzip.isEmpty()) {
                finaladdress += userzip

            }
//            if (!username.isEmpty() && !usercall.isEmpty() && !userflat.isEmpty() && !userstreet.isEmpty() && !usercity.isEmpty()
//                && !userzip.isEmpty())
//            {
//                val map = HashMap<String, String>()
//                map["username"] = fullname.text.toString()
//                map["usercall"] = call.text.toString()
//                map["userflat"] = flatno.text.toString()
//                map["userstreet"] = street.text.toString()
//                map["usercity"] = city.text.toString()
//                map["userzip"] = zipcode.text.toString()
//
//                mfirestore.collection("Ordered").document(auth.currentUser!!.uid)
//                    .collection("Address").add(map)
//                    .addOnCompleteListener(OnCompleteListener<DocumentReference?> { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this@Addaddress, "Address Added", Toast.LENGTH_SHORT).show()
//                            startActivity(intent)
//                            finish()
//                        }
//                    })
//            }
//            else {
//                Toast.makeText(this@Addaddress, "Kindly Fill All Fields",Toast.LENGTH_SHORT).show()
//            }
            //pincode
            val pincoder = zipcode.text.toString().trim()
            FirebaseFirestore.getInstance()
                .collection("Pincode")
                .document("zipcollect")
                .collection("zipcodes")
                .document("Am0U7MSpQWRBdFzyujpA").get()
                .addOnCompleteListener {
                    it.let {
                        if (it.isSuccessful && it.result != null && !username.isEmpty() && !usercall.isEmpty() && !userflat.isEmpty() && !userstreet.isEmpty() && !usercity.isEmpty()
                            && !userzip.isEmpty()) {
                            val pinCodeList = it.result!!["pin_code"] as List<String>
                            if(pinCodeList.contains(pincoder)){

                                val map = HashMap<String, String>()
                                map["username"] = fullname.text.toString()
                                map["usercall"] = call.text.toString()
                                map["userflat"] = flatno.text.toString()
                                map["userstreet"] = street.text.toString()
                                map["usercity"] = city.text.toString()
                                map["userzip"] = zipcode.text.toString()

                                mfirestore.collection("Ordered").document(auth.currentUser!!.uid)
                                    .collection("Address").add(map)
                                    .addOnCompleteListener(OnCompleteListener<DocumentReference?> { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this@Addaddress, "Address Added", Toast.LENGTH_SHORT).show()
                                            val s = intent.getDoubleExtra("pass", 0.0)
                                            val h = intent.getParcelableArrayListExtra<Parcelable>("itemlist")
                                            val i = Intent(applicationContext, com.example.fascom.Address::class.java)
                                            i.putExtra("pass", s)
                                            i.putParcelableArrayListExtra("itemlist", h)
                                            startActivity(i)
                                            finish()
                                        }
                                    })
                                Toast.makeText(this,"We Deliver Here!", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(this,"We Don't Deliver Here!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@Addaddress, "Kindly Fill All Fields",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {

                }
        }

    }

//    fun pincoder() {
//        //pincode checker
//        val pincoder = zipcode.text.toString().trim()
//        val userref =  FirebaseDatabase.getInstance().reference.child("Pincode")
//        userref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val pins : String = snapshot.child("pincode").value.toString()
//                if (pincoder.compareTo(pins) == 0){
//                    Toast.makeText(this@Addaddress, "We deliver here", Toast.LENGTH_LONG).show()
//
//                }
//                else
//                {
//                    Toast.makeText(this@Addaddress, "We dont deliver here", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }


}
