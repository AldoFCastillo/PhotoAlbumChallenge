package com.example.apkprueba.services

import com.example.apkprueba.Model.Device
import com.example.apkprueba.Model.DeviceDetails
import retrofit2.Callback

class Repository(private val webService: WebService) {

    fun requestDevices(callback: Callback<ArrayList<Device>>) {
        webService.getDevices().enqueue(callback)
    }

    fun requestDeviceDetails(id: Int, callback: Callback<DeviceDetails>) {
        webService.getDeviceDetails(id).enqueue(callback)
    }

}
