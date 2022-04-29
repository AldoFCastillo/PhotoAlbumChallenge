package com.example.apkprueba.Model

import com.google.gson.annotations.SerializedName

data class DeviceDetails(
    @SerializedName("id") val id : String?,
    @SerializedName("brand") val brand : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("legal") val legal : String?,
    @SerializedName("images") val images : List<Images>?,
    @SerializedName("mainImage") val mainImage : MainImage?
) {
}