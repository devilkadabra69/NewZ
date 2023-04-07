package com.example.newz.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newz.ModelClass.Article
import com.example.newz.ModelClass.Root_Model_Class_Response
import com.example.newz.Repository.NewsRepository
import com.example.newz.Retrofit.Retrofitinstance
import com.example.newz.RoomDB.NewsDatabse
import com.example.newz.Util.Constants
import com.example.newz.Util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModelClass(application: Application):AndroidViewModel(application) {
    private var repo:NewsRepository= NewsRepository(NewsDatabse(application))
    var breakingnews:MutableLiveData<Resource<Root_Model_Class_Response>> = MutableLiveData()
    val pageSize=1
    var searchnews:MutableLiveData<Resource<Root_Model_Class_Response>> = MutableLiveData()
    val searchnewspage=1

    init {
        fetchnewsallfrombyretrofit(Constants.API_KEY,Constants.COUNTRY)
    }


    var response:Response<Root_Model_Class_Response>?=null
    fun fetchnewsallfrombyretrofit(apiKey:String,country:String){
        viewModelScope.launch (Dispatchers.IO){
         breakingnews.postValue(Resource.Loading())
            val response=Retrofitinstance.api.getallbreakingnews(Constants.API_KEY,Constants.COUNTRY,pageSize)
            breakingnews.postValue(handleResponse(response))
        }
    }


    fun searchnews(apiKey: String,query:String,sortBy:String)=viewModelScope.launch(Dispatchers.IO) {
        searchnews.postValue(Resource.Loading())
        val response=repo.searchArticle(query,searchnewspage,apiKey)
        searchnews.postValue(handleSearchNewsResponse(response))
    }



    fun handleResponse(response: Response<Root_Model_Class_Response>): Resource<Root_Model_Class_Response>{
        if(response.isSuccessful){
            Log.d("RESPONSE", "handlebreakingnews: response is successful")
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(data = null,response.message())
    }
    fun handleSearchNewsResponse(response: Response<Root_Model_Class_Response>): Resource<Root_Model_Class_Response>{
        if(response.isSuccessful){
            Log.d("RESPONSE", "handlebreakingnews: response is successful")
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(data = null,response.message())
    }


    fun savearticle(article: Article){
        viewModelScope.launch {
            repo.upsertnewsArticle(article)
        }
    }

    fun getsavednews()=
        repo.getallsavednews()

    fun delarticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deletenewsArticle(article)
        }
    }
}