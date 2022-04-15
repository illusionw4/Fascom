package com.example.fascom.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fascom.Productactivity
import com.example.fascom.R
import com.example.fascom.model.popchoi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class PopulAdap(private val context: Context, val poplist: ArrayList<popchoi>, private var firestore: FirebaseFirestore, private var auth: FirebaseAuth) : RecyclerView.Adapter<PopulAdap.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemlistdotd, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = poplist[position]
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        holder.mmrp.text = currentitem.mrp
        holder.mourpri.text = currentitem.ourpri
        holder.mproductname.text = currentitem.productname
        holder.mquantity.text = currentitem.quantity
        holder.mcost.text = currentitem.cost.toString()
        holder.msize.text = currentitem.size.toString()
        Glide.with(context).load(currentitem.image).into(holder.mimage)

        holder.addbtn.setOnClickListener {
            val intent : Intent? = null
            var totalquantity : String= "1"
//            var totalprice = intent?.getIntExtra("cost",0)!! * 1
            val cartMap = HashMap<String, Any>()
            val image: String = Glide.with(context).load(currentitem.image).into(holder.mimage).toString()
            cartMap["productname"] = currentitem.productname.toString()
            cartMap["price"] = currentitem.ourpri.toString()
            cartMap["cost"] = currentitem.cost.toString()
            cartMap["size"] = currentitem.size.toString()
            cartMap["image"] = currentitem.image.toString()
            cartMap["quant"] = currentitem.quantity.toString()
            cartMap["totalQuantity"] = totalquantity
            cartMap["totalprice"] = currentitem.cost

            firestore.collection("CurrentUser").document(auth.currentUser!!.uid)
                .collection("AddToCart").add(cartMap)
                .addOnCompleteListener(OnCompleteListener<DocumentReference?> {
                    Toast.makeText(context,
                        "Added", Toast.LENGTH_SHORT
                    ).show()
                })
        }


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Productactivity::class.java)
            intent.putExtra("productname",currentitem.productname)
            intent.putExtra("ourpri",currentitem.ourpri)
            intent.putExtra("cost",currentitem.cost)
            intent.putExtra("size",currentitem.size)
            intent.putExtra("images",currentitem.image)
            intent.putExtra("mrp",currentitem.mrp)
            intent.putExtra("description",currentitem.descr)
            intent.putExtra("stock",currentitem.stock)
            intent.putExtra("quantity",currentitem.quantity)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return poplist.size
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var mmrp: TextView = itemView.findViewById(R.id.mrp)
        var mourpri: TextView = itemView.findViewById(R.id.ourpri)
        var mcost: TextView = itemView.findViewById(R.id.cost)
        var msize: TextView = itemView.findViewById(R.id.size)
        var mproductname: TextView = itemView.findViewById(R.id.productname)
        var mquantity: TextView = itemView.findViewById(R.id.quantity)
        var mimage: ImageView = itemView.findViewById(R.id.image)
        var addbtn: TextView = itemView.findViewById(R.id.addu)
    }
}