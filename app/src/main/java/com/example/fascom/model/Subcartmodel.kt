package com.example.fascom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Parcelize
data class Subcartmodel(
    val image: String,
    val quant: String,
    val price: String,
    val cost: String,
    var size: String,
    val productname: String,
    var plantype: String,
    var totalprice: Int,
    var documentid: String
) : Parcelable
{
    constructor() : this("","","","","","","",0,"")
}

