package com.example.fascom.model

data class AddressModel (
    val usercall: String? = "",
    val usercity: String?="",
    val userflat: String?="",
    val username: String?="",
    val userstreet: String?="",
    val userzip: String?="",
    var documentid: String
        )
{
    constructor() : this("","","","","","","")
}