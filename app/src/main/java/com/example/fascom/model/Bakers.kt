package com.example.fascom.model

data class Bakers  (
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