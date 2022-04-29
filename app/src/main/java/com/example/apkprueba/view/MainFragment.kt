package com.example.apkprueba.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apkprueba.R
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apkprueba.Model.Device
import com.example.apkprueba.databinding.FragmentMainBinding
import com.example.apkprueba.services.Resource
import com.example.apkprueba.utils.RecyclerItemDecorator
import com.example.apkprueba.view.recyclerview.DetailsRecyclerViewAdapter
import com.example.apkprueba.view.recyclerview.MainRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment(R.layout.fragment_main), MainRecyclerViewAdapter.DeviceInteraction {
    private val viewModel: MainViewModel by sharedViewModel()
    private val binding get() = mainView!!
    private lateinit var mainAdapter: MainRecyclerViewAdapter
    private var mainView: FragmentMainBinding? = null
    private lateinit var selectedItemId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainAdapter = MainRecyclerViewAdapter(this)
        mainView = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.apply {
                adapter = mainAdapter
                layoutManager = GridLayoutManager(context, 1)
                addItemDecoration(RecyclerItemDecorator(20, 20, 20, 20))
                setHasFixedSize(true)
            }
        }
        setListener()
        viewModel.requestDevices()
    }

    private fun setListener() {
        viewModel.devicesRequestResponse.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.STARTED -> {
                    binding.progressBar.visibility = GONE
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = GONE
                    it.data?.let { data ->
                            data.forEach {
                                it.id = (data.indexOf(it) + 1).toString()
                            }
                            mainAdapter.update(data)

                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setTitle("Error")
                        .setPositiveButton("Aceptar") { _, _ -> }
                        .setOnDismissListener {
                            viewModel.devicesRequestResponse.value =
                                Resource.Started()
                        }
                        .show()
                }
            }
        })
    }

    private fun setDetailsListeners() {
        viewModel.deviceDetailsRequestResponse.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.STARTED -> {
                    binding.progressBar.visibility = GONE
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = GONE

                    it.data?.let { data ->
                        var urlList = arrayListOf<String>()
                        data.images?.forEach {image ->
                            urlList.add(image.url)
                        }
                        val pos = selectedItemId.toInt()-1
                        mainAdapter.getList()?.let { list ->
                            list[pos].let {
                                it.urlList = urlList
                                it.legalText = data.legal
                            }
                        }
                        mainAdapter.notifyDataSetChanged()
                    }

                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setTitle("Error")
                        .setPositiveButton("Aceptar") { _, _ -> }
                        .setOnDismissListener {
                            viewModel.devicesRequestResponse.value =
                                Resource.Started()
                        }
                        .show()
                }
            }
        })
    }

    override fun deviceSelected(id: String) {
        selectedItemId = id
        setDetailsListeners()
        viewModel.requestDeviceDetails(id)
    }


}