package com.example.newz.Fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.newz.ModelClass.Article
import com.example.newz.R
import kotlin.properties.ReadWriteProperty


class ArticleViewFragments : Fragment() {


    private val args:ArticleViewFragmentsArgs by navArgs()
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_view_fragments, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar=view.findViewById(R.id.progress_article)
        progressBar.visibility=View.VISIBLE


        var url:String?=null
       try { url =args.ArticleUrlByNvaargs.toString()
           if(url.isNullOrEmpty()){
               Log.d("ARTICLEVIEW", "onViewCreated: null url found ${args.ArticleUrlByNvaargs.toString()}")
               return
           }
           webView=view.findViewById(R.id.webview_article)
           webView.apply {
               webViewClient= WebViewClient()
               settings.javaScriptEnabled=true
               settings.supportZoom()
               settings.allowFileAccess=true
               settings.builtInZoomControls=true
               settings.displayZoomControls=false
               settings.allowContentAccess=true
               settings.javaScriptCanOpenWindowsAutomatically=true
               loadUrl(url)
           }}
       catch (E:Exception){
           Log.d("EXCEPTION", "onViewCreated: ${url.toString()}")
       }

    }
    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }
    }

}


