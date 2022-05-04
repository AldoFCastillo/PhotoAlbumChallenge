package com.example.apkprueba.view.recyclerview

import android.telecom.Call
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apkprueba.Model.Photo
import com.example.apkprueba.R
import com.example.apkprueba.databinding.CellPhotoBinding

class DetailsRecyclerViewAdapter:
    RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsViewHolder>() {

    private var photoList: List<Photo>? = null


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): DetailsViewHolder {
        val binding = CellPhotoBinding.inflate(LayoutInflater.from(viewGroup.context))
        return DetailsViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() = photoList?.size ?: 0

    override fun onBindViewHolder(
        detailsViewHolder: DetailsViewHolder,
        position: Int
    ) {
        photoList?.let {
            detailsViewHolder.bindView(it[position])
        }
    }

    fun update(photos: List<Photo>) {
        photoList = photos
        notifyDataSetChanged()
    }

    class DetailsViewHolder(private val binding: CellPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(photo: Photo) {

            with(binding){
                Glide.with(itemView.context)
                    .load(photo.url)
                    .into(imageViewThumbnail)
            }

        }
    }
}