package com.example.apkprueba.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.apkprueba.Model.Album
import com.example.apkprueba.databinding.CellAlbumBinding


class MainRecyclerViewAdapter(private var deviceInteraction: DeviceInteraction) :
    Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    private var mainList: List<Album>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {
        val binding = CellAlbumBinding.inflate(LayoutInflater.from(viewGroup.context))
        return MainViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = position


    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) {
        mainList?.let {
            mainViewHolder.bindView(it[position], deviceInteraction)
        }
    }

    override fun getItemId(position: Int) = position.toLong()


    override fun getItemCount(): Int {
        return mainList?.size ?: 0
    }

    fun update(albums: List<Album>) {
        mainList = albums
        notifyDataSetChanged()
    }

    class MainViewHolder(private val binding: CellAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(album: Album, deviceInteraction: DeviceInteraction) {
            with(binding){
                textViewId.text = album.id.toString()
                textViewTopTag.text = album.title
                root.setOnClickListener {
                  deviceInteraction.onAlbumSelected(album.id.toString())
                }
            }

        }


    }

    fun getList() = mainList


    interface DeviceInteraction {
        fun onAlbumSelected(id: String)
    }
}

