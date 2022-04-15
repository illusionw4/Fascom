package com.example.fascom.model

data class Myordersview (
    var orderId: String?="",
    val totalamount: String?="",
    val status: String?="",
    var order_detail: String?="",
    var timestamp: String?=""
) {
    constructor() : this("","","","","")
}