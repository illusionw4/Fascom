package com.example.fascom.model

import kotlinx.serialization.Serializable

@Serializable
data class Dotd(
   val image: String?="",
   val mrp: String?="",
   val ourpri: String?="",
   var cost: Int = 0,
   var size: Int = 0,
   val descr: String?="",
   val stock: String?="",
   val productname: String?="",
   val quantity: String?=""
)