package com.example.apkprueba.Model

import com.google.gson.annotations.SerializedName

data class Images (

    @SerializedName("alternativeText") val alternativeText : String,
    @SerializedName("caption") val caption : String,
    @SerializedName("width") val width : Int,
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("thumbnailUrl") val thumbnailUrl : String
)
