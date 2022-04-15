package com.example.fascom.model

import kotlinx.serialization.Serializable

@Serializable
data class Grocers(
    val image: String,
    val mrp: String,
    val ourpri: String,
    var cost: Int,
    var size: Int,
    val descr: String,
    val stock: String,
    val productname: String,
    val quantity: String
){
    constructor() : this("","","",0,0,"","","","")

}
