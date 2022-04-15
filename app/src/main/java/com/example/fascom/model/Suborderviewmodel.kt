package com.example.fascom.model

data class Suborderviewmodel (
    var orderId: String?="",
    val totalamount: String?="",
    val status: String?="",
    val plantype: String?="",
    var order_detail: String?="",
    var timestamp: String?=""
) {
    constructor() : this("","","","","")
}