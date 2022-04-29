package com.example.apkprueba.view.recyclerview

import android.telecom.Call
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apkprueba.R
import com.example.apkprueba.databinding.CellDetailsBinding
import com.example.apkprueba.databinding.CellDeviceBinding

class DetailsRecyclerViewAdapter:
    RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsViewHolder>() {

    private var urlList: List<String>? = null


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): DetailsViewHolder {
        val binding = CellDetailsBinding.inflate(LayoutInflater.from(viewGroup.context))
        return DetailsViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() = urlList?.size ?: 0

    override fun onBindViewHolder(
        detailsViewHolder: DetailsViewHolder,
        position: Int
    ) {
        urlList?.let {
            detailsViewHolder.bindView(it[position])
        }
    }

    fun update(urls: List<String>) {
        urlList = urls
        notifyDataSetChanged()
    }

    class DetailsViewHolder(private val binding: CellDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(url: String) {
            with(binding){
                Glide.with(itemView.context)
                    .load(url)
                    .into(image)
            }

        }
    }
}