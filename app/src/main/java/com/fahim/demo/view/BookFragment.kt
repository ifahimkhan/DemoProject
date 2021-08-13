package com.fahim.demo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.demo.R
import com.fahim.demo.databinding.FragmentMainBinding
import com.fahim.demo.view.adapter.BookRecyclerAdapter
import com.fahim.demo.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookFragment @Inject constructor(
    private val mAdapter: BookRecyclerAdapter
) : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: BookViewModel

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val layoutPosition = viewHolder.layoutPosition
                val selected = mAdapter.currentList[layoutPosition]
                viewModel.deleteBook(selected)
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view);
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)

        binding.floatingActionButton.setOnClickListener {
            Log.e("TAG", "floatingActionButton click: ")
//            viewModel.resetInsertBookMessage()
            findNavController().navigate(BookFragmentDirections.actionBookFragmentToBookDetailsFragment())
        }
        binding.recyclerViewItems.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
            ItemTouchHelper(swipeCallBack).attachToRecyclerView(this)
        }

        viewModel.bookList.observe(viewLifecycleOwner, {
            Log.e("TAG", "onViewCreated: " + it.size)
            mAdapter.submitList(it)
        })


    }


}