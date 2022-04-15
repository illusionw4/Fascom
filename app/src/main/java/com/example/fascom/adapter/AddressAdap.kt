package com.example.fascom.adapter

import android.content.Context
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fascom.R
import com.example.fascom.model.AddressModel
import com.example.fascom.model.MyCartModel
import com.google.android.play.core.appupdate.v
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddressAdap(private val context: Context, val addresslist: ArrayList<AddressModel>) : RecyclerView.Adapter<AddressAdap.MyViewHolder>(){

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.addressitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = addresslist[position]
        holder.usercall.text = currentitem.usercall
        holder.usercity.text = currentitem.usercity
        holder.userflat.text = currentitem.userflat
        holder.username.text = currentitem.username
        holder.userstreet.text = currentitem.userstreet
        holder.userzip.text = currentitem.userzip
        holder.mprogress.visibility = View.GONE

        holder.deleteit.setOnClickListener {
            holder.mprogress.visibility = View.VISIBLE
            FirebaseFirestore.getInstance().collection("Ordered").document(auth.currentUser!!.uid)
                .collection("Address")
                .document(addresslist[position].documentid.toString())
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        addresslist.remove(addresslist[position])
//                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show()
                    }
                    notifyDataSetChanged()
                }
        }
    }

      override fun getItemCount(): Int {
        return addresslist.size
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var usercall: TextView = itemView.findViewById(R.id.usercall)
        var usercity: TextView = itemView.findViewById(R.id.usercity)
        var userflat: TextView = itemView.findViewById(R.id.userflat)
        var username: TextView = itemView.findViewById(R.id.username)
        var userstreet: TextView = itemView.findViewById(R.id.userstreet)
        var userzip: TextView = itemView.findViewById(R.id.userzip)
        var deleteit : ImageView = itemView.findViewById(R.id.removeitem)
        var mprogress : ProgressBar = itemView.findViewById(R.id.progress)

    }

    interface SelectedAddress {
        fun setAddress(usercall: String?, usercity: String?, userflat: String?, username: String?, userstreet: String?, userzip: String?)
    }
}