package com.example.fascom.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.fascom.Address
import com.example.fascom.R
import com.example.fascom.model.MyCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyCartAdap (private val context: Context, val  cartlist: ArrayList<MyCartModel>) : RecyclerView.Adapter<MyCartAdap.MyCartView>() {

 private val auth = FirebaseAuth.getInstance()

    var onDelete: (Double) -> Unit = {}
    var onclearlist: (ArrayList<MyCartModel>) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cartitem, parent, false)
        return MyCartView(itemView)
    }

    override fun onBindViewHolder(holder: MyCartView, position: Int) {
        val currentitem = cartlist[position]
        var totalprice = 0

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 8f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(context).load(currentitem.image).placeholder(circularProgressDrawable).into(holder.mimage)
        holder.mquant.text = currentitem.quant
        holder.mprice.text = currentitem.price
        holder.mproductname.text = currentitem.productname
        holder.mtotalquantity.text = currentitem.totalQuantity.toString()
        holder.mcost.text = currentitem.cost
        holder.msize.text = currentitem.size
        holder.mprogress.visibility = View.GONE

            holder.deleteit.setOnClickListener {
                holder.mprogress.visibility = View.VISIBLE
                FirebaseFirestore.getInstance().collection("CurrentUser").document(auth.currentUser!!.uid)
                    .collection("AddToCart")
                    .document(cartlist[position].documentid.toString())
                    .delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            holder.mprogress.visibility = View.GONE
                            cartlist.remove(cartlist[position])
//                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show()
                            onDelete(calculate())
                        }
                        calculate()
                        notifyDataSetChanged()
                    }
              }
        }

    override fun getItemCount(): Int {
        return cartlist.size
    }

    fun calculate(): Double {
        var totalAmount = 0.0
        for (myCartModel : MyCartModel in cartlist) {
            totalAmount += myCartModel.totalprice
        }
//        Toast.makeText(context, "TOTAL AMOunt = $totalAmount", Toast.LENGTH_SHORT).show()
        return totalAmount
    }

    fun clearlist(): ArrayList<MyCartModel> {
        cartlist.clear()
        return cartlist
        notifyDataSetChanged()
    }

    class MyCartView(itemView: View): RecyclerView.ViewHolder(itemView){
        var mquant: TextView = itemView.findViewById(R.id.quant)
        var mimage: ImageView = itemView.findViewById(R.id.pimage)
        var mprice: TextView = itemView.findViewById(R.id.price)
        var mproductname: TextView = itemView.findViewById(R.id.product_name)
        var mtotalquantity: TextView = itemView.findViewById(R.id.totalQuantity)
        var deleteit : ImageView = itemView.findViewById(R.id.removeitem)
        var mcost: TextView = itemView.findViewById(R.id.cost)
        var msize: TextView = itemView.findViewById(R.id.size)
        var mprogress : ProgressBar = itemView.findViewById(R.id.progress)
    }

}