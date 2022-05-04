package com.example.apkprueba.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apkprueba.Model.Album
import com.example.apkprueba.Model.Photo
import com.example.apkprueba.services.Repository
import com.example.apkprueba.services.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository ): ViewModel() {

    var albumsRequestResponse: MutableLiveData<Resource<ArrayList<Album>>> = MutableLiveData()
    var photoRequestResponse: MutableLiveData<Resource<ArrayList<Photo>?>> = MutableLiveData()

    private var selectedAlbumId: String?= null


    fun requestAlbums() {
        albumsRequestResponse.postValue(Resource.Loading())
        repository.requestAlbums(object: Callback<ArrayList<Album>>{
            override fun onResponse(
                call: Call<ArrayList<Album>>,
                response: Response<ArrayList<Album>>
            ) {
                albumsRequestResponse.postValue(Resource.Success(response.body()!!))
            }

            override fun onFailure(call: Call<ArrayList<Album>>, t: Throwable) {
                albumsRequestResponse.postValue(t.message?.let { Resource.Error(it) })
            }

        })
    }

    fun requestAlbumPhotos(id: String) {
        photoRequestResponse.postValue(Resource.Loading())
        repository.requestPhotos(id.toInt(), object : Callback<ArrayList<Photo>> {
            override fun onResponse(
                call: Call<ArrayList<Photo>>,
                response: Response<ArrayList<Photo>>
            ) {
                photoRequestResponse.postValue(Resource.Success(response.body()))
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                photoRequestResponse.postValue(t.message?.let { Resource.Error(it) })
            }

        })
    }

    fun setSelectedAlbum(id: String){
        this.selectedAlbumId = id
    }

    fun getSelectedAlbumId(): String? = this.selectedAlbumId
}