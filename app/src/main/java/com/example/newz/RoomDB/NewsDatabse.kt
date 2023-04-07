package com.example.newz.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newz.ModelClass.Article

@Database(entities = [Article::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class NewsDatabse():RoomDatabase() {

    abstract fun getdao():NewsDao

    companion object{
        @Volatile
        private var INSTANCE:NewsDatabse?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= INSTANCE?: synchronized(LOCK){
            INSTANCE?: getInstance(context).also {
                INSTANCE=it
            }
        }

        private fun getInstance(context: Context):NewsDatabse=
            Room.databaseBuilder(
                context,
                NewsDatabse::class.java,
                "article_DB.db"
            ).build()
    }
}