package com.fahim.demo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fahim.demo.R
import com.fahim.demo.databinding.FragmentImageApiBinding
import com.fahim.demo.util.Status
import com.fahim.demo.view.adapter.ImageRecyclerAdapter
import com.fahim.demo.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    val mAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: BookViewModel
    lateinit var binding: FragmentImageApiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentImageApiBinding.bind(view);
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        var job: Job? = null

        binding.etSearch.editText?.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotBlank()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        mAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImageUrl(it)
            Log.e("TAG", "Frag: $it")

        }
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)

        }


        viewModel.imageList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }


                    binding.progressBar.visibility = View.GONE
                    urls?.let {
                        mAdapter.submitList(urls)
                    } ?: mAdapter.submitList(listOf())

                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error Occured..", Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}