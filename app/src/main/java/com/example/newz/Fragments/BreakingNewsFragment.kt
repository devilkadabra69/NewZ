package com.example.newz.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.Adapter.NewsRVadapter
import com.example.newz.MainActivity
import com.example.newz.R
import com.example.newz.Util.Resource
import com.example.newz.ViewModel.NewsViewModelClass
import com.google.android.material.snackbar.Snackbar

class BreakingNewsFragment : Fragment() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var adapter:NewsRVadapter
    private lateinit var viewmodel:NewsViewModelClass
    private lateinit var progress:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_breaking_news, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewmodelclass
        viewmodel=(activity as MainActivity).viewmodel


        //set up recyclerview
        recyclerView=view.findViewById(R.id.recyclerview_breaking_news_fragment)
        setuprecyclerview()

        //adapter.item.click
        adapter.setOnItemClickListner {
            val action=BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleViewFragments(it.url.toString())
            findNavController().navigate(action)

        }
        adapter.setOnItemLongClickListner {
            viewmodel.savearticle(it)
            Snackbar.make(view,"Item Saved Successfully",Snackbar.LENGTH_SHORT).show()
            true
        }



        //progressbar
        progress=view.findViewById(R.id.progress)


        //observer
        viewmodel.breakingnews.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    hideprogressbar()
                    it.data?.let {newResponse->//RootmodelClass
                        run {
                            adapter.differ.submitList(newResponse.articles)
                            Log.d(
                                "RESPONSE_SUCCESS",
                                "onViewCreated: ${newResponse.articles}"
                            )
                            Log.d("RESPONSE_SUCCESS", "onViewCreated: ${newResponse.articles.size}")
                        }
                    }
                }

                is Resource.Error->{
                    hideprogressbar()
                    Log.d("ERROR", "onViewCreated: ${Resource.Error(data = null,it.message)}   ${it.message }")
                }

                is Resource.Loading->{
                    showprogressbar()
                }

            }
        })

    }
    fun hideprogressbar(){
        progress?.visibility=View.INVISIBLE
    }
    fun showprogressbar(){
        progress?.visibility=View.VISIBLE
    }
    fun setuprecyclerview(){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this.requireContext())
        adapter=NewsRVadapter(this.requireContext())
        recyclerView.adapter=adapter
    }

}