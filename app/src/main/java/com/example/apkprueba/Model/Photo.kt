package com.example.apkprueba.Model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") val id : Int?,
    @SerializedName("albumId") val albumId : Int?,
    @SerializedName("title") val title : String?,
    @SerializedName("url") val url : String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl : String?
    ) {
}