package com.example.newz.Util

sealed class Resource<T>(
    var data:T?=null,
    var message:String?=null) {

    class Success<T>(data :T):Resource<T>(data)
    class Error<T>(data: T?,message: String?):Resource<T>(data,message)
    class Loading<T>():Resource<T>()
}