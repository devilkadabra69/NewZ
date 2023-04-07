package com.example.newz.Repository

import com.example.newz.ModelClass.Article
import com.example.newz.ModelClass.Root_Model_Class_Response
import com.example.newz.Retrofit.Retrofitinstance
import com.example.newz.RoomDB.NewsDatabse
import retrofit2.Response

class NewsRepository(val db:NewsDatabse) {

    suspend fun getallnews(apiKey:String,country:String,page:Int): Response<Root_Model_Class_Response> {
        return Retrofitinstance.api.getallbreakingnews(apiKey, country, page)
    }

    suspend fun deletenewsArticle(article: Article){
        db.getdao().delete(article)
    }

    suspend fun upsertnewsArticle(article: Article){
        db.getdao().upsert(article)
    }
    fun getallsavednews()=db.getdao().fetchAllBreakingNews()

    suspend fun searchArticle(searchquery:String,pageNumber:Int,apiKey: String,)=
        Retrofitinstance.api.searchfornews(apiKey,searchquery,"publishedAt",pageNumber)
}