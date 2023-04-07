package com.example.newz.ModelClass

data class Root_Model_Class_Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)