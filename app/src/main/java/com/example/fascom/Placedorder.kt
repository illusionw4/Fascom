package com.example.fascom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.example.fascom.model.MyCartModel
import com.example.fascom.utills.prefConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.internal.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firestore.v1.StructuredQuery
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.cartitem.*
import kotlinx.android.synthetic.main.cartitem.cost
import kotlinx.android.synthetic.main.cartitem.size
import com.google.firestore.v1.StructuredQuery.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.orderview.*
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.example.fascom.adapter.MyCartAdap
import com.example.fascom.adapter.MydotdAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_addtocart.*
import kotlinx.android.synthetic.main.activity_placedorder.*


class Placedorder : AppCompatActivity() {
    private lateinit var mfirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var mArraylist: ArrayList<MyCartModel>
    private lateinit var continuesh: Button
     lateinit var subtext: TextView
     lateinit var ordimage: ImageView
     lateinit var orderplace: TextView
    private lateinit var vieword: Button
    var mProgressBar: ProgressBar? = null
    var i: Int = 0
    lateinit var mCountDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placedorder)


        //buttons
        continuesh = findViewById(R.id.cons)
        vieword = findViewById(R.id.vieworders)
        subtext = findViewById(R.id.subtext)
        orderplace = findViewById(R.id.orderplace)
        ordimage = findViewById(R.id.ordimage)

        mProgressBar = findViewById(R.id.progress)
        mProgressBar?.visibility = View.VISIBLE
        subtext.visibility = View.GONE
        continuesh.visibility = View.GONE
        vieword.visibility = View.GONE
        ordimage.visibility = View.GONE
        orderplace.visibility = View.GONE

        mfirestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        mArraylist = arrayListOf<MyCartModel>()
        val uuid: UUID = UUID.randomUUID()
        val randomUUIDString: String = uuid.toString()
        val lUUID = UUID.randomUUID().toString().replace("-","").substring(0,8)

        mProgressBar?.progress = i
        mCountDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tick of Progress$i$millisUntilFinished")
                i++
                mProgressBar?.progress = i as Int * 100 / (3000 / 1000)
            }

            override fun onFinish() {
                //Do what you want
                i++
                mProgressBar?.progress = 100
                mProgressBar?.visibility = View.GONE
                subtext.visibility = View.VISIBLE
                continuesh.visibility = View.VISIBLE
                vieword.visibility = View.VISIBLE
                ordimage.visibility = View.VISIBLE
                orderplace.visibility = View.VISIBLE
            }
        }
        mCountDownTimer.start()
        val list: ArrayList<MyCartModel>? = this.intent.getParcelableArrayListExtra<MyCartModel>("itemlist")

        val saveCurrentDate: String
        val saveCurrentTime: String
        val calForDate = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MM dd yyyy")
        saveCurrentDate = currentDate.format(Date())

        val currentTime = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = currentTime.format(Date())


        continuesh.setOnClickListener {
            val intent = Intent(this@Placedorder, Home::class.java)
            startActivity(intent)
            finish()
        }
        vieword.setOnClickListener {
            val intent = Intent(this@Placedorder, Myorders::class.java)
            startActivity(intent)
            finish()
        }

        if (list != null && list.size > 0) {
            val outerMap = HashMap<String, Any>()
            outerMap["orderId"] = lUUID
            outerMap["timestamp"] = (Date().time / 1000).toString()
            outerMap["order_detail"] = Gson().toJson(com.example.fascom.utills.prefConfig.getFoodOrderList(applicationContext))
            outerMap["totalamount"] = intent.getDoubleExtra("pass", 0.0).toString()
            outerMap["status"] = "ordered"

            mfirestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("orders").document().set(outerMap)
                .addOnCompleteListener(OnCompleteListener<Void>() {

                })
        }


        if (list != null && list.size > 0) {
            for (myCartModel: MyCartModel in list) {
                val cartMap = HashMap<String, Any>()
                cartMap["productname"] = myCartModel.productname
                cartMap["price"] = myCartModel.price
                cartMap["cost"] = myCartModel.cost
                cartMap["size"] = myCartModel.size
                cartMap["quant"] = myCartModel.quant
                cartMap["currentDate"] = saveCurrentDate
                cartMap["currentTime"] = saveCurrentTime
                cartMap["totalQuantity"] = myCartModel.totalQuantity
                cartMap["totalprice"] = myCartModel.totalprice

                Log.i("list.clear", list.clear().toString())
                mfirestore.collection("users").document(auth.currentUser!!.uid)
                    .collection("Orders").document(lUUID).collection("New Orders")
                    .add(cartMap)
                    .addOnCompleteListener(OnCompleteListener<DocumentReference?> {
                        intent.putExtra("documentid",lUUID)
                    })
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@Placedorder, Home::class.java)
        startActivity(intent)
    }

    }
