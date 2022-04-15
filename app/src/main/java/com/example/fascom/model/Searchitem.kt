package com.example.fascom.model

import android.widget.TextView
import kotlinx.serialization.Serializable


@Serializable
data class Searchitem(
    val photoPath: String,
    val productname: String,

    ){
        constructor() : this("","")

}
