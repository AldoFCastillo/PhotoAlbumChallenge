package com.example.apkprueba.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apkprueba.Model.Device
import com.example.apkprueba.Model.DeviceDetails
import com.example.apkprueba.services.Repository
import com.example.apkprueba.services.Resource
import com.example.apkprueba.services.WebService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository ): ViewModel() {

    var devicesRequestResponse: MutableLiveData<Resource<ArrayList<Device>>> = MutableLiveData()
    var deviceDetailsRequestResponse: MutableLiveData<Resource<DeviceDetails>> = MutableLiveData()


    fun requestDevices() {
        devicesRequestResponse.postValue(Resource.Loading())
        repository.requestDevices(object: Callback<ArrayList<Device>>{
            override fun onResponse(
                call: Call<ArrayList<Device>>,
                response: Response<ArrayList<Device>>
            ) {
                devicesRequestResponse.postValue(Resource.Success(response.body()!!))
            }

            override fun onFailure(call: Call<ArrayList<Device>>, t: Throwable) {
                devicesRequestResponse.postValue(t.message?.let { Resource.Error(it) })
            }

        })
    }

    fun requestDeviceDetails(id: String) {
        deviceDetailsRequestResponse.postValue(Resource.Loading())
        repository.requestDeviceDetails(id.toInt(), object : Callback<DeviceDetails> {
            override fun onResponse(
                call: Call<DeviceDetails>,
                response: Response<DeviceDetails>
            ) {
                deviceDetailsRequestResponse.postValue(Resource.Success(response.body()!!))
            }

            override fun onFailure(call: Call<DeviceDetails>, t: Throwable) {
                deviceDetailsRequestResponse.postValue(t.message?.let { Resource.Error(it) })
            }

        })
    }
}