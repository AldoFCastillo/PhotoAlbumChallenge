package com.example.apkprueba.services

import com.example.apkprueba.Model.Album
import com.example.apkprueba.Model.Photo
import retrofit2.Callback

class Repository(private val webService: WebService) {

    fun requestAlbums(callback: Callback<ArrayList<Album>>) {
        webService.getAlbums().enqueue(callback)
    }

    fun requestAlbum(id: Int, callback: Callback<Album>) {
        webService.getAlbum(id).enqueue(callback)
    }

    fun requestPhotos(id: Int, callback: Callback<ArrayList<Photo>>) {
        webService.getPhotos(id).enqueue(callback)
    }

}
