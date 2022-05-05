package com.example.apkprueba.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.apkprueba.Model.Photo
import com.example.apkprueba.databinding.CellPhotoBinding

class PhotosRecyclerViewAdapter:
    RecyclerView.Adapter<PhotosRecyclerViewAdapter.DetailsViewHolder>() {

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

            val url = GlideUrl(
                photo.url, LazyHeaders.Builder()
                    .addHeader("User-Agent", WebSettings.getDefaultUserAgent(itemView.context))
                    .build()
            )

            with(binding){
                cellTextViewName.text = photo.id.toString()
                cellTextViewDescription.text = photo.title?.uppercase()
                Glide.with(itemView.context)
                    .load(url)
                    .into(imageViewThumbnail)
            }

        }
    }
}