package com.example.apkprueba.services

import com.example.apkprueba.Model.Photo
import com.example.apkprueba.Model.Album
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface WebService {

    @GET("/albums")
    fun getAlbums(): Call<ArrayList<Album>>

    @GET("/albums/{id}")
    fun getAlbum(@Path("id") id: Int): Call<Album>

    @GET("/albums/{id}/photos")
    fun getPhotos(@Path("id") id: Int): Call<ArrayList<Photo>>
}