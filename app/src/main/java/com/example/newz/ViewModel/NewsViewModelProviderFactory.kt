package com.example.newz.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newz.Repository.NewsRepository

class NewsViewModelProviderFactory(private val newsRepo: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModelProviderFactory(newsRepo = newsRepo) as T
    }
}