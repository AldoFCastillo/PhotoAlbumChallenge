package com.example.apkprueba.Model

import com.google.gson.annotations.SerializedName

data class Album (
    @SerializedName("userId")
    val userId : Int?,
    @SerializedName("id")
    val id : Int?,
    @SerializedName("title")
    val title : String?,
    ) {

    var photos= arrayListOf<Photo>()
}