package com.example.apkprueba.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.apkprueba.Model.Album
import com.example.apkprueba.databinding.CellAlbumBinding
import java.util.*
import kotlin.collections.ArrayList


class MainRecyclerViewAdapter(private var deviceInteraction: DeviceInteraction) :
    Adapter<MainRecyclerViewAdapter.MainViewHolder>(), Filterable {

    private var mainList: List<Album>? = null
    private var mainListFiltered: List<Album>? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {
        val binding = CellAlbumBinding.inflate(LayoutInflater.from(viewGroup.context))
        return MainViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = position


    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) {
        mainListFiltered?.let {
            mainViewHolder.bindView(it[position], deviceInteraction)
        }
    }

    override fun getItemId(position: Int) = position.toLong()


    override fun getItemCount()= mainListFiltered?.size ?: 0

    fun update(albums: List<Album>) {
        mainList = albums
        mainListFiltered = mainList
        notifyDataSetChanged()
    }

    class MainViewHolder(private val binding: CellAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(album: Album, deviceInteraction: DeviceInteraction) {
            with(binding){
                textViewId.text = album.id.toString()
                textViewTopTag.text = album.title?.uppercase()
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) mainListFiltered = mainList else {
                    val filteredList = ArrayList<Album>()
                    mainList?.filter {
                            (it.title!!.contains(constraint!!))
                        }?.forEach { filteredList.add(it) }
                    mainListFiltered = filteredList

                }
                return FilterResults().apply { values = mainListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                mainListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Album>
                notifyDataSetChanged()
            }
        }
    }
}

