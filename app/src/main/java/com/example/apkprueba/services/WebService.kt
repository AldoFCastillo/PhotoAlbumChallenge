package com.example.apkprueba.services

import com.example.apkprueba.Model.DeviceDetails
import com.example.apkprueba.Model.Device
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface WebService {

    @GET("/devices")
    fun getDevices(): Call<ArrayList<Device>>

    @GET("/devices/{id}")
    fun getDeviceDetails(@Path("id") id: Int): Call<DeviceDetails>
}