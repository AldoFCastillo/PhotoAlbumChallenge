package com.example.apkprueba.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.apkprueba.R
import com.example.apkprueba.databinding.FragmentMainBinding
import com.example.apkprueba.services.Resource
import com.example.apkprueba.utils.RecyclerItemDecorator
import com.example.apkprueba.view.recyclerview.MainRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment(R.layout.fragment_main), MainRecyclerViewAdapter.DeviceInteraction {
    private val viewModel: MainViewModel by sharedViewModel()
    private val binding get() = mainView!!
    private lateinit var mainAdapter: MainRecyclerViewAdapter
    private var mainView: FragmentMainBinding? = null

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
                layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                addItemDecoration(RecyclerItemDecorator(20, 20, 20, 20))
                setHasFixedSize(true)
                scheduleLayoutAnimation()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (mainAdapter.getList()?.any{it.title!!.contains(query!!)} == true) {
                        mainAdapter.filter.filter(query)
                    } else {
                        Toast.makeText(context, "No Match found", Toast.LENGTH_LONG).show()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    mainAdapter.filter.filter(newText)
                    return false
                }

            })

        }
        setListener()
        viewModel.requestAlbums()
    }



    private fun setListener() {
        viewModel.albumsRequestResponse.observe(viewLifecycleOwner, {
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
                            viewModel.albumsRequestResponse.value =
                                Resource.Started()
                        }
                        .show()
                }
            }
        })
    }

    override fun onAlbumSelected(id: String) {

        viewModel.setSelectedAlbum(id)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, PhotoFragment::class.java, null)
            ?.commit()
    }


}