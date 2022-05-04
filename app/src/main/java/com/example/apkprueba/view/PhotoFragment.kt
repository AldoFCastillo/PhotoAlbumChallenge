package com.example.apkprueba.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.apkprueba.R
import com.example.apkprueba.databinding.FragmentMainBinding
import com.example.apkprueba.services.Resource
import com.example.apkprueba.utils.RecyclerItemDecorator
import com.example.apkprueba.view.recyclerview.PhotosRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PhotoFragment: Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by sharedViewModel()
    private val binding get() = mainView!!
    private lateinit var photoAdapter: PhotosRecyclerViewAdapter
    private var mainView: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        photoAdapter = PhotosRecyclerViewAdapter()
        mainView = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.apply {
                adapter = photoAdapter
                layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                addItemDecoration(RecyclerItemDecorator(20, 20, 20, 20))
                setHasFixedSize(true)
                scheduleLayoutAnimation()
                (layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.9F
                requestLayout()
            }
        }
        setListener()
        viewModel.getSelectedAlbumId()?.let { viewModel.requestAlbumPhotos(it) }
    }

    private fun setListener() {
        viewModel.photoRequestResponse.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.STARTED -> {
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    it.data?.let { data ->
                        photoAdapter.update(data)
                    }

                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setTitle("Error")
                        .setPositiveButton("Aceptar") { _, _ -> }
                        .setOnDismissListener {
                            viewModel.photoRequestResponse.value =
                                Resource.Started()
                        }
                        .show()
                }
            }
        })
    }

}