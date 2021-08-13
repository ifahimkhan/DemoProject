package com.fahim.demo.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.fahim.demo.R
import com.fahim.demo.database.Book
import com.fahim.demo.databinding.FragmentBookDetailsBinding
import com.fahim.demo.util.Status
import com.fahim.demo.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailsFragment @Inject constructor(private val glide: RequestManager) :
    Fragment(R.layout.fragment_book_details) {

    lateinit var binding: FragmentBookDetailsBinding
    lateinit var viewModel: BookViewModel
    private lateinit var callBack: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach: ")
        callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImageUrl("")
                findNavController().navigateUp()
                viewModel.resetInsertBookMessage()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: ")

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)

        binding = FragmentBookDetailsBinding.bind(view)

        subscribe()

        binding.mainImageView.setOnClickListener {
            findNavController().navigate(BookDetailsFragmentDirections.actionBookDetailsFragmentToImageApiFragment())
        }
        binding.btnSave.setOnClickListener {
            String
            val book = Book(
                title = binding.etTitle.editText?.text.toString(),
                author = binding.etName.editText?.text.toString(),
                year = binding.etYear.editText?.text.toString(),
                imageUrl = ""
            )
            viewModel.validateBook(book.title, book.author, book.year)
            Log.e("TAG", "btnSave: ")
        }


    }

    fun subscribe() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            binding.let {
                glide.load(url).into(it.mainImageView)
            }
        })
        viewModel.inserBookMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertBookMessage()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //unregister listener here
        callBack.isEnabled = false
        callBack.remove()
    }

}