package com.example.newz.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.Adapter.NewsRVadapter
import com.example.newz.MainActivity
import com.example.newz.R
import com.example.newz.ViewModel.NewsViewModelClass
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:NewsRVadapter
    private lateinit var viewmodel:NewsViewModelClass
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as MainActivity).viewmodel
        recyclerView=view.findViewById(R.id.recyclerview_saved_fragment)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(this.requireContext())
        adapter= NewsRVadapter(this.requireContext())
        recyclerView.adapter=adapter

        adapter.setOnItemClickListner {
            val action=SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleViewFragments(it.url.toString())
            findNavController().navigate(action)

        }

        val itemtouchhelpercallback=object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=adapter.differ.currentList.get(position)
                viewmodel.delarticle(article)
                Snackbar.make(view,"Successfully item deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewmodel.savearticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemtouchhelpercallback).apply {
            attachToRecyclerView(recyclerView)
        }

        viewmodel.getsavednews().observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })
    }

}