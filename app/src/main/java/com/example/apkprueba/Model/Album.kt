package com.example.apkprueba.Model

import com.google.gson.annotations.SerializedName

data class Device (
    @SerializedName("name")
    val name : String?,
    @SerializedName("installmentsTag")
    val installmentsTag : String?,
    @SerializedName("topTag")
    val topTag : String?,
    @SerializedName("mainImage")
    val mainImage : MainImage?

    ) {
    var id: String? = null
    var urlList : ArrayList<String>? = null
    var legalText: String? = null
}