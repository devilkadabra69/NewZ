package com.example.newz

import android.content.res.Resources.Theme
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newz.Repository.NewsRepository
import com.example.newz.RoomDB.NewsDatabse
import com.example.newz.ViewModel.NewsViewModelClass
import com.example.newz.ViewModel.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewmodel:NewsViewModelClass
    lateinit var factory: NewsViewModelProviderFactory
    lateinit var repository: NewsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NewZ)
        Thread.sleep(500)
        setContentView(R.layout.activity_main)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navhostcontroller=navHostFragment.findNavController()
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navhostcontroller)
        repository=NewsRepository(NewsDatabse(this.applicationContext))
        factory=NewsViewModelProviderFactory(repository)
        viewmodel=ViewModelProvider.AndroidViewModelFactory(application).create(NewsViewModelClass::class.java)
        var appBarConfiguration= AppBarConfiguration(setOf(R.id.breakingNewsFragment,R.id.searchNewsFragment,R.id.savedFragment,R.id.articleViewFragments))
        setupActionBarWithNavController(navhostcontroller,appBarConfiguration)

    }
}