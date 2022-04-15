package com.example.fascom.model

data class Myitemlist (
    val productname: String?="",
    val price: String?="",
    val cost: String?="",
    var timestamp: String?="",
    ) {
        constructor() : this("","","","")
    }
