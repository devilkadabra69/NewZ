package com.example.newz.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newz.ModelClass.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article_Table")
    fun fetchAllBreakingNews(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(article: Article):Long

    @Delete
    suspend fun delete(article: Article)
}