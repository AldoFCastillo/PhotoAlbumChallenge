package com.example.apkprueba.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.apkprueba.Model.Device
import com.example.apkprueba.databinding.CellDeviceBinding


class MainRecyclerViewAdapter(private var deviceInteraction: DeviceInteraction) :
    Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    private var mainList: List<Device>? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {
        val binding = CellDeviceBinding.inflate(LayoutInflater.from(viewGroup.context))
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

    fun update(devices: List<Device>) {
        mainList = devices
        notifyDataSetChanged()
    }

    class MainViewHolder(private val binding: CellDeviceBinding) : RecyclerView.ViewHolder(binding.root) {

        private var detailsAdapter  = DetailsRecyclerViewAdapter()

        fun bindView(device: Device, deviceInteraction: DeviceInteraction) {
            with(binding){
                textViewName.text = device.name
                textViewTopTag.text = device.topTag
                textViewInstallmentTag.text = device.installmentsTag
                Glide.with(itemView.context)
                    .load(device.mainImage?.url)
                    .into(image)
                root.setOnClickListener {
                    initDetailsViews(device.id, deviceInteraction)
                }
                setChildRecyclerUpdate(device)
            }

        }

        fun setChildRecyclerUpdate(device: Device){
            if(!device.urlList.isNullOrEmpty()){
                detailsAdapter.update(device.urlList!!)
                var legalText = device.legalText?.replace("<p>", "")
                legalText = legalText?.replace("</p>", "")
                binding.textViewLegal.text = legalText
            }
        }

        private fun initDetailsViews(id: String?, deviceInteraction: DeviceInteraction){
            binding.horizontalRecyclerView.apply {
                if(visibility == VISIBLE) {
                    visibility = GONE
                    binding.line.visibility = GONE
                    binding.textViewLegal.visibility = GONE
                } else {
                    adapter = detailsAdapter
                    setHasFixedSize(true)
                    id?.let { id -> deviceInteraction.deviceSelected(id) }
                    visibility = VISIBLE
                    binding.line.visibility = VISIBLE
                    binding.textViewLegal.visibility = VISIBLE
                }

            }
        }


    }

    fun getList() = mainList


    interface DeviceInteraction {
        fun deviceSelected(id: String)
    }
}

